package com.nekrashevich.b1.database;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static final Logger logger = LogManager.getLogger();
    static Connection dbConnection;

    public static Connection getDbConnection()
            throws ClassNotFoundException, SQLException {

        Properties property = new Properties();

        String name = "";
        String url = "";
        String username = "";
        String password = "";
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/application.properties")) {

            property.load(fileInputStream);

            name = property.getProperty("jdbc.driver");
            url = property.getProperty("jdbc.url");
            username = property.getProperty("jdbc.username");
            password = property.getProperty("jdbc.password");

        } catch (IOException e) {
            logger.log(Level.ERROR, "File not found");
        }

        Class.forName(name);

        dbConnection = DriverManager.getConnection(url,
                username, password);

        return dbConnection;
    }
}
