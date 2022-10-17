package com.nekrashevich.b1.service.impl;

import com.nekrashevich.b1.exception.FileCombinerException;
import com.nekrashevich.b1.service.FileCombiner;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class FileCombinerImpl implements FileCombiner {
    private static final String FILE_NAME = "combined.txt";
    private static final Logger logger = LogManager.getLogger();
    @Override
    public void combineFiles() throws FileCombinerException {
        try (FileWriter fileWriter = new FileWriter(FILE_NAME, true)) {
            for(int i = 1; i <= 100; i++) {
                String fileName = i + ".txt";
                fileInput(fileWriter, fileName);
                logger.log(Level.INFO, "Готово: " + i);
                logger.log(Level.INFO, "Осталось: " + (100 - i));
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileCombinerException(e);
        }
    }

    @Override
    public void combineFiles(String regex) {

    }

    private void fileInput(FileWriter fileWriter, String fileName) throws FileCombinerException {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            while (true) {
                String currentLine = bufferedReader.readLine();
                if (currentLine == null) {
                    break;
                } else {
                    String str = currentLine + "\n";
                    fileWriter.write(str);
                }
            }
        }
        catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileCombinerException(e);
        }
    }
}
