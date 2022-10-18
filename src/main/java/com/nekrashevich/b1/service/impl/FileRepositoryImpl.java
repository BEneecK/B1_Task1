package com.nekrashevich.b1.service.impl;

import com.nekrashevich.b1.database.Connector;
import com.nekrashevich.b1.service.FileRepository;
import com.nekrashevich.b1.service.consts.DataBaseConsts;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FileRepositoryImpl implements FileRepository {
    @Override
    public void importFile() {

    }

    @Override
    public double sumOfInteger() {
        return 0;
    }

    @Override
    public double medianOfDouble() {
        return 0;
    }

//    private PreparedStatement initStatement() {
//
//        try {
//            PreparedStatement preparedStatement = Connector.getDbConnection().prepareStatement(DataBaseConsts.INSERT);
//            preparedStatement.setDate(1, date);
//            preparedStatement.setString(2, l);
//            preparedStatement.setString(3, c);
//            preparedStatement.setInt(4, i);
//            preparedStatement.setDouble(5, d);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
}
