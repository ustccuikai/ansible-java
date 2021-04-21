package com.ansbile.exec;

import com.ansbile.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class AnsibleExecutorHandler {

    public static void execute(CommandLine commandLine, Long timeout) {
        try {
            final DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
            ExecutorEngine executorEngine = ExecutorEngineBuilder.build(timeout);
            executorEngine.execute(commandLine, resultHandler);
            resultHandler.waitFor(1000);
            // 启动时间
            Long startTaskTime = new Date().getTime();

            // 判断任务是否执行
            if (executorEngine.isWatching()) {
                log.info("任务启动成功! isWatching = {}", executorEngine.isWatching());
            } else {
                log.info("任务启动失败! isWatching = {} ", executorEngine.isWatching());
                return;
            }

            while (true) {
                resultHandler.waitFor(1000);

//                log.info("output msg:" + executorEngine.getOutputMsg());
//                log.error("error msg:" + executorEngine.getErrorMsg());
                // 任务结束
                if (resultHandler.hasResult()) {
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

}
