package com.nekrashevich.b1;

import com.nekrashevich.b1.controller.Controller;
import com.nekrashevich.b1.exception.ControllerException;
import com.nekrashevich.b1.exception.FileGenerationException;
import com.nekrashevich.b1.service.impl.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws FileGenerationException, ControllerException {

        logger.log(Level.INFO, "Generating files...");
        FileGeneratorImpl fileGenerator = new FileGeneratorImpl(new StringGeneratorImpl());
        fileGenerator.generateFiles();

        Controller controller = new Controller(new FileRepositoryImpl(), new FileCombinerImpl(), new ValidatorImpl());
        controller.startAppLoop();
    }
}