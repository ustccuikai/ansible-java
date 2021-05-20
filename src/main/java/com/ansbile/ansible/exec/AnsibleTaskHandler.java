package com.ansbile.ansible.exec;

import com.ansbile.ansible.config.AnsiblePlaybookArgs;
import com.ansbile.dao.entity.Task;
import com.ansbile.dao.entity.TaskMember;
import com.ansbile.ansible.model.*;
import com.ansbile.service.DeploySchemaService;
import com.ansbile.service.TaskMemberService;
import com.ansbile.service.TaskService;
import com.ansbile.service.impl.DeploySchemaRegister;
import com.google.common.collect.Maps;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
     * inventoryJson: inventory的JSON
     * taskParam: 参数，Map形式传递和存储
     *
     * 安装成功后，后置任务: 写入mysql集群表....
     * @param task
     */
    public void call(Task task) {
        //0. 生成mysql集群id
        task.setMysqlGroupId(UUID.randomUUID().toString().replace("-", "").toLowerCase());

        DeploySchemaService deployService = deploySchemaRegister.getDeploySchemaService(task.getTaskType());
        //1. 生成子任务，不同的部署类型生成的子任务不一样
        List<TaskMember> taskMembers = deployService.buildTaskMembers(task);
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

            //任务完成后执行后置处理
            if (task.getStopType() == TaskStopType.COMPLETE_STOP.getType()) {
                deployService.postProcessor(task);
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

            AnsibleInventory inventory = new GsonBuilder().create().fromJson(
                    member.getInventoryJson(), AnsibleInventory.class);

            Map<String, String> paramMap = Maps.newHashMap();
            if (StringUtils.isNotBlank(member.getExecutorParam())) {
                Type type = new TypeToken<Map<String, Map<String, String>>>() {
                }.getType();
                paramMap = new GsonBuilder().create().fromJson(member.getExecutorParam(), type);
            }

            AnsiblePlaybookArgs ansibleArgs = AnsiblePlaybookArgs.builder()
                    .playbookName(member.getPlaybookName())
                    .tags(member.getPlaybookTags())
                    .inventory(inventory)
                    .becomePassword(member.getBecomePassword())
                    .extraVars(paramMap)
                    .build();

            ansibleExecutorHandler.execute(member, ansibleArgs, (long) (1000 * 60 * 30));
        }
    }
}
