package com.nekrashevich.b1.service.impl;

import com.nekrashevich.b1.exception.FileGenerationException;
import com.nekrashevich.b1.service.FileGenerator;
import com.nekrashevich.b1.service.StringGenerator;
import com.nekrashevich.b1.service.consts.FileConsts;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class FileGeneratorImpl implements FileGenerator {

    private static final Logger logger = LogManager.getLogger();
    private final StringGenerator stringGenerator;

    public FileGeneratorImpl(StringGenerator stringGenerator) {
        this.stringGenerator = stringGenerator;
    }

    @Override
    public void generateFiles() throws FileGenerationException {
        CountDownLatch countDownLatch = new CountDownLatch(FileConsts.COUNT_OF_FILES);
        for (int i = 1; i <= FileConsts.COUNT_OF_FILES; i++) {
            String fileName = i + ".txt";
            Runnable runnable = () -> {
                try {
                    writeInFile(fileName);
                    countDownLatch.countDown();
                } catch (FileGenerationException e) {
                    e.printStackTrace();
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileGenerationException(e);
        }
        logger.log(Level.INFO, "All files have been generated");
    }

    private void writeInFile(String fileName) throws FileGenerationException {
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            for (int i = 0; i < FileConsts.COUNT_OF_STRINGS; i++) {
                String str = stringGenerator.generate() + "\n";
                fileWriter.write(str);
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileGenerationException(e);
        }
    }
}
