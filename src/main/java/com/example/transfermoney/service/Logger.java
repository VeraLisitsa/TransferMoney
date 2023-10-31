package com.example.transfermoney.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class Logger {
    private final File loggerFile;
    private FileWriter writerLoggerFile;
    private static final String LOGGER_FILE_NAME = "logger.log";

    public Logger() {
        loggerFile = new File(LOGGER_FILE_NAME);
        try {
            loggerFile.createNewFile();
            writerLoggerFile = new FileWriter(loggerFile, true);
        } catch (IOException e) {
            System.out.println("Error creating log file");
        }
    }

    protected boolean writeToLoggerFile(String message) {
        try {
            writerLoggerFile.write(message + "\n");
            writerLoggerFile.flush();
            return true;

        } catch (IOException e) {
            System.out.println("WARNING.Entry not added");
            return false;
        }
    }

    protected File getFile() {
        return loggerFile;
    }


}
