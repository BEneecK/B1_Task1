package com.nekrashevich.b1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector extends Configs {
    static Connection dbConnection;

    public static Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString,
                dbUser, dbPass);

        return dbConnection;
    }
}
