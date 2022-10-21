package com.nekrashevich.b1;

import com.nekrashevich.b1.controller.Controller;
import com.nekrashevich.b1.exception.ControllerException;
import com.nekrashevich.b1.exception.FileCombinerException;
import com.nekrashevich.b1.exception.FileGenerationException;
import com.nekrashevich.b1.exception.FileRepositoryException;
import com.nekrashevich.b1.service.impl.FileGeneratorImpl;
import com.nekrashevich.b1.service.impl.StringGeneratorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws FileGenerationException, FileCombinerException,
            FileRepositoryException, ControllerException {

//        logger.log(Level.INFO, "Generating files...");
//        FileGeneratorImpl fileGenerator = new FileGeneratorImpl(new StringGeneratorImpl());
//        fileGenerator.generateFiles();

        Controller controller = new Controller();
        controller.mainController();
    }
}