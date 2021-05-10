package com.ansbile.exec;

import com.ansbile.config.AnsiblePlaybookArgs;
import com.ansbile.model.*;
import com.ansbile.service.TaskMemberService;
import com.ansbile.service.TaskService;
import com.ansbile.service.impl.DeploySchemaRegister;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by cuikai on 2021/4/26.
 */
@Component
@Slf4j
public class AnsibleTaskHandler {

    @Resource
    private TaskService taskService;

    @Resource
    private TaskMemberService taskMemberService;

    @Resource
    private DeploySchemaRegister deploySchemaRegister;

    @Resource
    private AnsibleExecutorHandler ansibleExecutorHandler;

    /**
     * taskType: 安装类型
     * hostsMap: Map<group,IP1;IP2;IP3>
     * taskParam: 参数，Map形式传递和存储
     * @param task
     */
    public void call(Task task) {
        // 1. 生成子任务，不同的部署类型生成的子任务不一样
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> hostPatternMap = new GsonBuilder().create().fromJson(task.getHostsMap(), type);

        List<TaskMember> taskMembers = deploySchemaRegister.getDeploySchemaService(task.getTaskType()).buildTaskMembers(task, hostPatternMap);
        // 更新子任务数量
        task.setTaskSize(taskMembers.size());
        taskService.updateTask(task);
        //2. 子任务写入数据库中
        for (TaskMember taskMember : taskMembers) {
            taskMemberService.addTaskMember(taskMember);
        }

        //3. 调度子任务并更新主任务状态
        boolean exit = false;
        while (!exit) {
            // 执行
            executorPlaybook(taskMemberService.queryTaskMemberByTaskStatus(task.getId(), TaskStatus.QUEUE.getStatus(), 1));
            int finalizedSize = taskMemberService.countTaskMemberByTaskStatus(task.getId(), TaskStatus.FINALIZED.getStatus(), task.getTaskSize());
            // 判断任务是否结束
            if (finalizedSize == task.getTaskSize()) {
                task.setFinalized(1);
                taskService.updateTask(task);
                exit = true;
            }
            // 判断主任务是否结束
            task = taskService.queryTaskById(task.getId());
            if (task.getStopType() == TaskStopType.TASK_STOP.getType()) {
                exit = true;
            }

            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
        }
    }


    private void executorPlaybook(List<TaskMember> memberList) {
        for (TaskMember member : memberList) {
            member.setTaskStatus(TaskStatus.EXECUTING.getStatus());
            taskMemberService.updateTaskMember(member);

            AnsibleInventory inventory = new AnsibleInventory();
            AnsibleGroup group = new AnsibleGroup(member.getGroup());
            inventory.addGroup(group);
            String[] hosts = StringUtils.split(member.getHosts(), ";");
            for (String host : hosts) {
                group.addHost(new AnsibleHost(host));
            }

            AnsiblePlaybookArgs ansibleArgs = AnsiblePlaybookArgs.builder()
                    .playbookName(member.getPlayBookName())
                    .inventory(inventory)
                    .build();

            ansibleExecutorHandler.execute(member, ansibleArgs, (long) (1000 * 60 * 30));
        }
    }
}
