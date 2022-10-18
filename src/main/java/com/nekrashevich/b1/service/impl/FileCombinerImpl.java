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
    private static final String FILE_NAME = "combined.txt";
    private static int countOfDeletedStrings = 0;
    private static final Logger logger = LogManager.getLogger();
    @Override
    public void combineFiles() throws FileCombinerException {
        try (FileWriter fileWriter = new FileWriter(FILE_NAME, true)) {
            for(int i = 1; i <= FileConsts.COUNT_OF_FILES; i++) {
                String fileName = i + ".txt";
                fileInput(fileWriter, fileName);
                logger.log(Level.INFO, "Готово: " + i);
                logger.log(Level.INFO, "Осталось: " + (FileConsts.COUNT_OF_FILES - i));
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileCombinerException(e);
        }
    }

    @Override
    public void combineFiles(String regex) throws FileCombinerException {
        try (FileWriter fileWriterCombiner = new FileWriter(FILE_NAME, true)) {
            for(int i = 1; i <= FileConsts.COUNT_OF_FILES; i++) {
                String fileName = i + ".txt";
                fileInput(fileWriterCombiner, fileName, regex);
                logger.log(Level.INFO, "Готово: " + i);
                logger.log(Level.INFO, "Осталось: " + (FileConsts.COUNT_OF_FILES - i));
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileCombinerException(e);
        }
        logger.log(Level.INFO, "Count: " + countOfDeletedStrings);
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

    private void fileInput(FileWriter fileWriterCombiner, String fileName, String regex) throws FileCombinerException {
        List<String> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            while (true) {
                String currentLine = bufferedReader.readLine();
                if (currentLine == null) {
                    break;
                } else {
                    String str = currentLine + "\n";
                    //проверка есть ли символы в строке
                    if(str.contains(regex)) {
                        //обнуляем строку
                        str = "";
                        //увеличиваем число удаленных
                        countOfDeletedStrings++;
                    }
                    else {
                        //если нет, то добавляем строку в список
                        list.add(str);
                    }
                    //записываем строку в файл
                    fileWriterCombiner.write(str);
                }
            }
        }
        catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileCombinerException(e);
        }
        // удаление строк из основных файлов
        updateFile(list, fileName);
    }

    private void updateFile(List<String> list, String fileName) throws FileCombinerException {
        try (FileWriter fileWriter = new FileWriter(fileName, false)){
            list.forEach((el) -> {
                try {
                    fileWriter.write(el);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileCombinerException(e);
        }
    }
}
