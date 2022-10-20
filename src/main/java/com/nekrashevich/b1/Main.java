package com.nekrashevich.b1;

import com.nekrashevich.b1.exception.FileCombinerException;
import com.nekrashevich.b1.exception.FileGenerationException;
import com.nekrashevich.b1.service.FileRepository;
import com.nekrashevich.b1.service.impl.*;

public class Main {
    public static void main(String[] args) throws FileGenerationException, FileCombinerException {

        FileGeneratorImpl fileGenerator = new FileGeneratorImpl(new StringGeneratorImpl());
        fileGenerator.generateFiles();

        FileCombinerImpl fileCombiner = new FileCombinerImpl();
        fileCombiner.combineFiles("а");


//        FileRepositoryImpl fileRepository = new FileRepositoryImpl();
//        fileRepository.importFile(1);


    }
}