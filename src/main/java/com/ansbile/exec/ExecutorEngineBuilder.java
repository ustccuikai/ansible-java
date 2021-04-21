package com.ansbile.exec;

import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;

public class ExecutorEngineBuilder {

    public static ExecutorEngine build(long timeout) {
        final ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout);
        // 缓冲区1024字节
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream(1024);
        PumpStreamHandler streamHandler = new PumpStreamHandler(new ExecLogHandler(outputStream), new ExecLogHandler(errorStream));
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(streamHandler);
        executor.setWatchdog(watchdog);

        return ExecutorEngine.builder()
                .executor(executor)
                .watchdog(watchdog)
                .outputStream(outputStream)
                .errorStream(errorStream)
                .build();
    }
}
