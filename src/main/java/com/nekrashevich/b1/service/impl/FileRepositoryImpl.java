package com.nekrashevich.b1.service.impl;

import com.nekrashevich.b1.database.Connector;
import com.nekrashevich.b1.service.FileRepository;
import com.nekrashevich.b1.service.consts.DataBaseConsts;
import com.nekrashevich.b1.service.consts.FileConsts;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileRepositoryImpl implements FileRepository {
    private static final Logger logger = LogManager.getLogger();
    private static final String STRINGS_IMPORTED = "Перенесено: ";
    private static final String STRING_LEFT = "Осталось: ";
    private static int countOfLines;

    @Override
    public void importFile(int numberOfFile) {
        String fileName = numberOfFile + ".txt";
        stringsToList(fileName);
    }

    @Override
    public double sumOfInteger() {
        return 0;
    }

    @Override
    public double medianOfDouble() {
        return 0;
    }

    public void stringsToList(String fileName){

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
             Connection connection = Connector.getDbConnection()) {
            List<String> lines = new ArrayList<>();
            String currentLine = bufferedReader.readLine();
            while (currentLine != null) {

                String changedLine = parseLine(currentLine);
                lines.add(changedLine);
                currentLine = bufferedReader.readLine();
            }
            countOfLines = lines.size();
            makeStatement(lines, connection);
        }
        catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException();
        }
    }

    public String parseLine(String line) {
        String[] splitLine = line.split("\\|\\|");
        String date = dateParsing(splitLine[0]);
        String doubleNumber = splitLine[4].replace(',', '.');
        return date + "||" + splitLine[1] + "||" + splitLine[2] + "||" + splitLine[3] + "||" + doubleNumber;
    }

    private String dateParsing(String date) {
        String year;
        String month;
        String day;
        String[] splitDate = date.split("\\.");
        day = splitDate[0];
        month = splitDate[1];
        year = splitDate[2];
        return year + "-" + month + "-" + day;
    }

    private void makeStatement(List<String> lines, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DataBaseConsts.INSERT);
            for(String line : lines) {
                String[] splitLine = line.split("\\|\\|");
                preparedStatement.setDate(1, Date.valueOf(splitLine[0]));
                preparedStatement.setString(2, splitLine[1]);
                preparedStatement.setString(3, splitLine[2]);
                preparedStatement.setInt(4, Integer.parseInt(splitLine[3]));
                preparedStatement.setDouble(5, Double.parseDouble(splitLine[4]));
                if(lines.indexOf(line) % 1000 == 0) {
                    preparedStatement.executeBatch();
                    int countOfImportedStrings = lines.indexOf(line);
                    logger.log(Level.INFO, STRINGS_IMPORTED + countOfImportedStrings);
                    logger.log(Level.INFO, STRING_LEFT + countOfLines);
                    preparedStatement.clearBatch();
                }
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            logger.log(Level.INFO, "Все данные перенесены");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
