package com.ansbile.exec;

import com.ansbile.config.AnsiblePlaybookArgs;
import com.ansbile.config.AnsiblePlaybookArgsBuilder;
import com.ansbile.dao.entity.TaskMember;
import com.ansbile.model.*;
import com.ansbile.service.TaskMemberService;
import com.ansbile.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Component
public class AnsibleExecutorHandler {

    // 100 分钟
    public static final Long MAX_TIMEOUT = 6000000L;

    @Resource
    private TaskMemberService taskMemberService;

    //子任务状态修改
    //子任务执行失败时需要修改主任务结束状态为强制停止TASK_STOP
    public void execute(TaskMember member, AnsiblePlaybookArgs ansibleArgs, Long timeout) {
        if (timeout == 0)
            timeout = MAX_TIMEOUT;

        try {
            CommandLine commandLine = AnsiblePlaybookArgsBuilder.build(ansibleArgs);
            System.out.println(commandLine);

            final DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
            ExecutorEngine executorEngine = ExecutorEngineBuilder.build(timeout);
            executorEngine.execute(commandLine, resultHandler);
            resultHandler.waitFor(1000);
            // 启动时间
            Long startTaskTime = new Date().getTime();

            // 判断任务是否执行
            if (member.getTaskStatus().equals(TaskStatus.QUEUE.getStatus())) {
                resultHandler.waitFor(500);
                if (executorEngine.isWatching()) {
                    member.setTaskStatus(TaskStatus.EXECUTING.getStatus());
                    taskMemberService.updateTaskMember(member);
                    log.info("任务启动成功! id = {} ; isWatching = {} ; taskStatus = {}", member.getId(), executorEngine.isWatching(), member.getTaskStatus());
                } else {
                    member.setExitValue(1);
                    member.setFinalized(1);
                    member.setTaskStatus(TaskStatus.FINALIZED.getStatus());
                    taskMemberService.updateTaskMember(member);
                    log.info("任务启动失败! id = {} ; isWatching = {} ; taskStatus = {}", member.getId(), executorEngine.isWatching(), member.getTaskStatus());
                    return;
                }
            }

            while (true) {
                resultHandler.waitFor(1000);

                //每隔1s更新一次log
                taskMemberService.updateTaskMemberLog(member.getId(), executorEngine.getOutputMsg(),
                        executorEngine.getErrorMsg());

                // 任务结束
                if (resultHandler.hasResult()) {
                    TaskStatusBO taskStatus = TaskStatusBO.builder()
                            .exitValue(resultHandler.getExitValue())
                            .taskResult(AnsibleResult.getName(resultHandler.getExitValue()))
                            .stopType(resultHandler.getExitValue() == 0 ? TaskStopType.COMPLETE_STOP.getType() : -1)
                            .build();
                    System.out.println(taskStatus);
                    saveServerTaskMember(member, taskStatus);
                    log.info("ExitValue is " + resultHandler.getExitValue());
                    return;
                } else {
                    // 判断任务是否需要终止或超时
                    if (TimeUtils.checkTimeout(startTaskTime, timeout)) {
                        executorEngine.killedProcess();
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void saveServerTaskMember(TaskMember member, TaskStatusBO taskStatus) {
        member.setFinalized(taskStatus.getFinalized());
        member.setExitValue(taskStatus.getExitValue());
        member.setStopType(taskStatus.getStopType());
        member.setTaskStatus(taskStatus.getTaskStatus());

        if (!StringUtils.isEmpty(taskStatus.getTaskResult()))
            member.setTaskResult(taskStatus.getTaskResult());

        taskMemberService.updateTaskMember(member);
    }
}
