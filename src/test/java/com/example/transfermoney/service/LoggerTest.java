package com.example.transfermoney.service;

import org.apache.commons.io.input.ReversedLinesFileReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LoggerTest {
    private Logger logger;


    @BeforeEach
    protected void beforeEach() {
        logger = new Logger();
    }

    @AfterEach
    protected void afterEach() {
        logger = null;
    }

    @Test
    protected void writeToLoggerFileTest() throws IOException {
        String message = "test";
        String expected = message;
        logger.writeToLoggerFile(message);
        ReversedLinesFileReader reader = new ReversedLinesFileReader(logger.getFile());
        String entry = reader.readLine().replaceAll("\r\n", "");
        Assertions.assertEquals(expected, entry.substring(entry.length() - message.length()));
    }

}


