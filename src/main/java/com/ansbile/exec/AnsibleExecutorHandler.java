package com.ansbile.exec;

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

    public void execute(TaskMember member, CommandLine commandLine, Long timeout) {
        if (timeout == 0)
            timeout = MAX_TIMEOUT;

        try {
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
                    log.info("任务启动成功! id = {} ; isWatching = {} ; taskStatus = {}", member.getId(), executorEngine.isWatching(), member.getTaskStatus());
                    member.setTaskStatus(TaskStatus.EXECUTING.getStatus());
                    taskMemberService.updateTaskMember(member);
                } else {
                    log.info("任务启动失败! id = {} ; isWatching = {} ; taskStatus = {}", member.getId(), executorEngine.isWatching(), member.getTaskStatus());
                    member.setExitValue(1);
                    member.setFinalized(1);
                    member.setTaskStatus(TaskStatus.FINALIZED.getStatus());
                    taskMemberService.updateTaskMember(member);
                    return;
                }
            }

            while (true) {
                resultHandler.waitFor(1000);

                // 执行日志写入redis
//                taskLogRecorder.recorderLog(member.getId(), executorEngine);

//                log.info("output msg:" + executorEngine.getOutputMsg());
//                log.error("error msg:" + executorEngine.getErrorMsg());
                // 任务结束
                if (resultHandler.hasResult()) {
                    TaskStatusBO taskStatus = TaskStatusBO.builder()
                            .exitValue(resultHandler.getExitValue())
                            .taskResult(AnsibleResult.getName(resultHandler.getExitValue()))
                            .stopType(resultHandler.getExitValue() == 0 ? TaskStopType.COMPLETE_STOP.getType() : -1)
                            .build();
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
        // 写入并清空日志
        MemberExecutorLogBO memberExecutorLogBO = taskLogRecorder.getLog(member.getId());
        if (memberExecutorLogBO != null) {
            try {
                if (!StringUtils.isEmpty(memberExecutorLogBO.getOutputMsg())) {
                    String outputLogPath = taskLogRecorder.getOutputLogPath(member);   //Joiner.on("/").join(playbookLogPath,member.getId() + "_output.log" );
                    member.setOutputMsg(outputLogPath);
                }
            } catch (Exception e) {
                log.error("记录执行日志OutputMsg错误, memberId = {}", member.getId());
            }
            try {
                if (!StringUtils.isEmpty(memberExecutorLogBO.getErrorMsg())) {
                    String errorLogPath = taskLogRecorder.getErrorLogPath(member); //Joiner.on("/").join(playbookLogPath,member.getId() + "_error.log" );
                    member.setErrorMsg(errorLogPath);
                }
            } catch (Exception e) {
                log.error("记录执行日志ErrorMsg错误, memberId = {}", member.getId());
            }
            taskLogRecorder.clearLog(member.getId());
        }

        if (!StringUtils.isEmpty(taskStatus.getTaskResult()))
            member.setTaskResult(taskStatus.getTaskResult());

        taskMemberService.updateTaskMember(member);
    }

}
