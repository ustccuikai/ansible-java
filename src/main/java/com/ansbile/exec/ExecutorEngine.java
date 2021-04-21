package com.ansbile.exec;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.exec.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Data
@Builder
public class ExecutorEngine {

    @Builder.Default
    private DefaultExecutor executor = new DefaultExecutor();
    // 缓冲区1024字节
    @Builder.Default
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
    @Builder.Default
    private ByteArrayOutputStream errorStream = new ByteArrayOutputStream(1024);
    private final ExecuteWatchdog watchdog;

    public void execute(CommandLine commandLine, DefaultExecuteResultHandler resultHandler) throws IOException {
        executor.execute(commandLine,resultHandler);
    }

    public boolean isWatching(){
        return watchdog.isWatching();
    }

    public boolean killedProcess() {
        return watchdog.killedProcess();
    }

    public String getOutputMsg() {
        try {
            return outputStream.toString("utf8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public String getErrorMsg() {
        try {
            return errorStream.toString("utf8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

}
