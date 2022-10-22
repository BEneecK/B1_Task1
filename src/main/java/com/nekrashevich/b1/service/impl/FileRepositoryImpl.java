package com.nekrashevich.b1.service.impl;

import com.nekrashevich.b1.database.Connector;
import com.nekrashevich.b1.exception.FileRepositoryException;
import com.nekrashevich.b1.service.FileRepository;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileRepositoryImpl implements FileRepository {
    private static final Logger logger = LogManager.getLogger();
    private static final String STRINGS_IMPORTED = "Imported: ";
    private static final String STRING_LEFT = "Left: ";
    private static final String INSERT = "INSERT INTO task1_db.table (date, latinString, cyrillicString, integerNumb, doubleNumb) VALUES (?,?,?,?,?)";
    private static int countOfLines;
    private static Connection connection;

    {
        try {
            connection = Connector.getDbConnection();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    @Override
    public void importFile(int numberOfFile) throws FileRepositoryException {
        String fileName = numberOfFile + ".txt";
        stringsToList(fileName);
    }

    //TODO check if db exist
    @Override
    public void executeScript(String fileName) throws FileRepositoryException {
        //Initialize the script runner
        ScriptRunner sr = new ScriptRunner(connection);
        //Creating a reader object
        Reader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileRepositoryException(e);
        }
        //Running the script
        sr.runScript(reader);
    }

    private void stringsToList(String fileName) throws FileRepositoryException {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            List<String> lines = new ArrayList<>();
            String currentLine = bufferedReader.readLine();
            while (currentLine != null) {

                String changedLine = parseLine(currentLine);
                lines.add(changedLine);
                currentLine = bufferedReader.readLine();
            }
            countOfLines = lines.size();
            writeDataInDB(lines, connection);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileRepositoryException(e);
        }
    }

    private String parseLine(String line) {
        String[] splitLine = line.split("\\|\\|");
        String date = parseDate(splitLine[0]);
        String doubleNumber = splitLine[4].replace(',', '.');
        return date + "||" + splitLine[1] + "||" + splitLine[2] + "||" + splitLine[3] + "||" + doubleNumber;
    }

    private String parseDate(String date) {
        String year;
        String month;
        String day;
        String[] splitDate = date.split("\\.");
        day = splitDate[0];
        month = splitDate[1];
        year = splitDate[2];
        return year + "-" + month + "-" + day;
    }

    private void writeDataInDB(List<String> lines, Connection connection) throws FileRepositoryException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            for (int i = 0; i < lines.size(); i++) {
                String[] splitLine = lines.get(i).split("\\|\\|");
                preparedStatement.setDate(1, Date.valueOf(splitLine[0]));
                preparedStatement.setString(2, splitLine[1]);
                preparedStatement.setString(3, splitLine[2]);
                preparedStatement.setInt(4, Integer.parseInt(splitLine[3]));
                preparedStatement.setDouble(5, Double.parseDouble(splitLine[4]));
                preparedStatement.addBatch();

                if (i % 1000 == 0) {
                    preparedStatement.executeBatch();

                    logger.log(Level.INFO, STRINGS_IMPORTED + i);
                    logger.log(Level.INFO, STRING_LEFT + (countOfLines - i));
                    preparedStatement.clearBatch();
                }
            }
            preparedStatement.executeBatch();
            logger.log(Level.INFO, "All strings have been imported");
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new FileRepositoryException(e);
        }
    }


}
