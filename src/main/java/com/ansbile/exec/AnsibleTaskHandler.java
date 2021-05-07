package com.ansbile.exec;

import com.ansbile.config.AnsibleConfig;
import com.ansbile.config.AnsiblePlaybookArgs;
import com.ansbile.config.AnsiblePlaybookArgsBuilder;
import com.ansbile.model.*;
import com.ansbile.service.TaskMemberService;
import com.ansbile.service.TaskService;
import com.ansbile.service.impl.DeploySchemaRegister;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cuikai on 2021/4/26.
 */
@Component
public class AnsibleTaskHandler {

    @Resource
    private TaskService taskService;

    @Resource
    private TaskMemberService taskMemberService;

    @Resource
    private DeploySchemaRegister deploySchemaRegister;

    @Resource
    private AnsibleConfig ansibleConfig;

    @Resource
    private AnsibleExecutorHandler ansibleExecutorHandler;

    public void call(Task task) {
        // 1. 生成子任务，不同的部署类型生成的子任务不一样
        List<TaskMember> taskMembers = deploySchemaRegister.getDeploySchemaService(task.getTaskType()).buildTaskMembers(task);
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
            if (task.getStopType() == TaskStopType.TASK_STOP.getType())
                exit = true;
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
        }
    }


    private void executorPlaybook(List<TaskMember> memberList) throws Exception {
        for (TaskMember member : memberList) {
            member.setTaskStatus(TaskStatus.EXECUTING.getStatus());
            taskMemberService.updateTaskMember(member);

            AnsibleInventory inventory = new AnsibleInventory();
            AnsibleGroup group = new AnsibleGroup(member.getHostPattern());
            inventory.addGroup(group);
            String[] hosts = StringUtils.split(member.getHosts(), ";");
            for (String host : hosts) {
                group.addHost(new AnsibleHost(host));
            }

            AnsiblePlaybookArgs ansibleArgs = AnsiblePlaybookArgs.builder()
                    .playbookName(member.getPlayBookName())
                    .inventory(inventory)
                    .build();

            CommandLine commandLine = AnsiblePlaybookArgsBuilder.build(ansibleConfig, ansibleArgs);
            System.out.println(commandLine);
            ansibleExecutorHandler.execute(member, commandLine, (long) (1000 * 60 * 30));
        }
    }
}
