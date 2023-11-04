package com.example.transfermoney.util;

import org.apache.commons.io.input.ReversedLinesFileReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class LoggerTest {

    private static final String TEST_FILE_NAME = "test.log";

    private File testFile;

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

        testFile = new File(TEST_FILE_NAME);
        logger.setWriterLoggerFile(new FileWriter(testFile, true));
        String message = "test";
        String expected = message;
        logger.writeToLoggerFile(message);
        ReversedLinesFileReader reader = new ReversedLinesFileReader(testFile);
        String entry = reader.readLine().replaceAll("\r\n", "");
        Assertions.assertEquals(expected, entry.substring(entry.length() - message.length()));
    }


}


