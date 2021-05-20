package com.ansbile.exec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.LogOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by cuikai on 2021/4/20.
 */
@Slf4j
public class ExecLogHandler extends LogOutputStream {

    ByteArrayOutputStream outputStream;

    public ExecLogHandler(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    protected void processLine(String line, int logLevel) {
        System.out.println(line);
        try {
            outputStream.write(line.getBytes());
            outputStream.write("\\r\\n".getBytes());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
