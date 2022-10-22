package com.nekrashevich.b1.service.impl;

import com.nekrashevich.b1.exception.FileCombinerException;
import com.nekrashevich.b1.service.FileCombiner;
import com.nekrashevich.b1.service.consts.FileConsts;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileCombinerImpl implements FileCombiner {
    private static final Logger logger = LogManager.getLogger();
    private static String FILE_NAME;
    private static int countOfDeletedStrings = 0;

    @Override
    public void combineFiles() throws FileCombinerException {
        createFileName();
        try (FileWriter fileWriter = new FileWriter(FILE_NAME, true)) {
            for (int i = 1; i <= FileConsts.COUNT_OF_FILES; i++) {
                String fileName = i + ".txt";
                fileInput(fileWriter, fileName);
                logger.log(Level.INFO, "Done: " + i);
                logger.log(Level.INFO, "Left: " + (FileConsts.COUNT_OF_FILES - i));
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileCombinerException(e);
        }
    }

    @Override
    public void combineFiles(String regex) throws FileCombinerException {
        createFileName();
        try (FileWriter fileWriterCombiner = new FileWriter(FILE_NAME, true)) {
            for (int i = 1; i <= FileConsts.COUNT_OF_FILES; i++) {
                String fileName = i + ".txt";
                fileInput(fileWriterCombiner, fileName, regex);
                logger.log(Level.INFO, "Done: " + i);
                logger.log(Level.INFO, "Left: " + (FileConsts.COUNT_OF_FILES - i));
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileCombinerException(e);
        }
        logger.log(Level.INFO, "Count of deleted strings: " + countOfDeletedStrings);
    }

    private void fileInput(FileWriter fileWriter, String fileName) throws FileCombinerException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String currentLine = bufferedReader.readLine();
            while (currentLine != null) {

                String str = currentLine + "\n";
                fileWriter.write(str);
                currentLine = bufferedReader.readLine();
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileCombinerException(e);
        }
    }

    private void fileInput(FileWriter fileWriterCombiner, String fileName, String regex) throws FileCombinerException {
        List<String> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String currentLine = bufferedReader.readLine();
            while (currentLine != null) {
                String str = currentLine + "\n";
                //проверка есть ли символы в строке
                if (str.contains(regex)) {
                    //увеличиваем число удаленных
                    countOfDeletedStrings++;
                } else {
                    //если нет, то добавляем строку в список
                    list.add(str);
                    fileWriterCombiner.write(str);
                }
                currentLine = bufferedReader.readLine();
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileCombinerException(e);
        }
        // удаление строк из основных файлов
        updateFile(list, fileName);
    }

    private void updateFile(List<String> list, String fileName) throws FileCombinerException {
        try (FileWriter fileWriter = new FileWriter(fileName, false)) {
            for (String el : list) {
                fileWriter.write(el);
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileCombinerException(e);
        }
    }

    private void createFileName() {
        FILE_NAME = "combined";
        for (int i = 1; true; i++) {
            String tempFileName = FILE_NAME + i + ".txt";
            File file = new File(tempFileName);
            if (!file.exists()) {
                FILE_NAME = FILE_NAME + i + ".txt";
                break;
            }
        }
    }
}