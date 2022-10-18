package com.nekrashevich.b1;

import com.nekrashevich.b1.exception.FileCombinerException;
import com.nekrashevich.b1.exception.FileGenerationException;
import com.nekrashevich.b1.service.impl.FileGeneratorImpl;
import com.nekrashevich.b1.service.impl.StringGeneratorImpl;

public class Main {
    public static void main(String[] args) throws FileGenerationException, FileCombinerException {

        FileGeneratorImpl fileGenerator = new FileGeneratorImpl(new StringGeneratorImpl());
        fileGenerator.generateFiles();
//
//        FileCombinerImpl fileCombiner = new FileCombinerImpl();
//        fileCombiner.combineFiles("zrw");

//        Date date = new Date(2015, 12, 25);
//
//        DataBase dataBase = new DataBase();
//        dataBase.addData(date, "asdas", "вавава", 12345, 4.12345678);
    }
}