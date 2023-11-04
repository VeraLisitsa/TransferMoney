package com.example.transfermoney.util;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@NoArgsConstructor
public class Logger {
    private File loggerFile;
    private FileWriter writerLoggerFile;

    @Value("${logger.fileName}")
    private String fileName;

    private final static String DATE_TIME_FORMAT = "dd-MM-yyyy, kk:mm:ss";

    public synchronized boolean createLogFile() {
        if (loggerFile == null) {
            loggerFile = new File(fileName);
            try {
                loggerFile.createNewFile();
                writerLoggerFile = new FileWriter(loggerFile, true);
                return true;
            } catch (IOException e) {
                System.out.println("Error creating log file");
                return false;
            }
        }
        return false;
    }

    public synchronized boolean writeToLoggerFile(String message) {
        try {
            writerLoggerFile.write("[" + DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
                    .format(LocalDateTime.now()) + "]" + message + "\n");
            writerLoggerFile.flush();
            return true;

        } catch (IOException e) {
            System.out.println("WARNING.Entry not added");
            return false;
        }
    }

    public void setWriterLoggerFile(FileWriter writerLoggerFile) {
        this.writerLoggerFile = writerLoggerFile;
    }

    public File getLoggerFile() {
        return loggerFile;
    }

    public FileWriter getWriterLoggerFile() {
        return writerLoggerFile;
    }

    public String getFileName() {
        return fileName;
    }
}
