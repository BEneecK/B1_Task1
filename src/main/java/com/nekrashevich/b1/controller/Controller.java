package com.nekrashevich.b1.controller;

import com.nekrashevich.b1.exception.ControllerException;
import com.nekrashevich.b1.exception.FileCombinerException;
import com.nekrashevich.b1.exception.FileRepositoryException;
import com.nekrashevich.b1.service.ControllerService;
import com.nekrashevich.b1.service.impl.FileCombinerImpl;
import com.nekrashevich.b1.service.impl.FileRepositoryImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Controller {

    private static final Logger logger = LogManager.getLogger();
    private static final String SCRIPT_NAME_SUM = "sum_script.sql";
    private static final String SCRIPT_NAME_MEDIAN = "median_script.sql";
    private static final String CHOICE_MENU = """
            1. Combine files into one
            2. Combine files into one with setting characters to delete
            3. Import files to database
            4. Perform a stored operation of sum and median.
            5. Exit""";
    private static final FileRepositoryImpl fileRepository = new FileRepositoryImpl();
    private static final FileCombinerImpl fileCombiner = new FileCombinerImpl();

    public void mainController() throws FileCombinerException, FileRepositoryException, ControllerException {

        while (true) {
            System.out.println(CHOICE_MENU);
            Scanner scanner = new Scanner(System.in);
            String choice;
            try {
                choice = scanner.nextLine();
            } catch (InputMismatchException e) {
                logger.log(Level.ERROR, e.getMessage());
                throw new ControllerException(e);
            }
            if (ControllerService.isValidInputNumber(choice)) {
                if (choice.equals("1")) fileCombiner.combineFiles();
                else if (choice.equals("2")) regexCombineController();
                else if (choice.equals("3")) importFileController();
                else if (choice.equals("4")) {
                    fileRepository.executeScript(SCRIPT_NAME_SUM);
                    fileRepository.executeScript(SCRIPT_NAME_MEDIAN);
                } else if (choice.equals("5")) System.exit(0);
            } else {
                System.out.println("Incorrect input");
            }
        }
    }

    private void regexCombineController() throws FileCombinerException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Your characters: ");
        String regex = scanner.nextLine();

        if (ControllerService.isValidInputRegex(regex)) {
            fileCombiner.combineFiles(regex);
        } else {
            System.out.println("Incorrect input");
        }
    }

    private void importFileController() throws FileRepositoryException, ControllerException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Number of file: ");
        String numberOfFile;
        try {
            numberOfFile = scanner.nextLine();
        } catch (InputMismatchException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ControllerException(e);
        }

        if (ControllerService.isFileExist(numberOfFile)) {
            fileRepository.importFile(Integer.parseInt(numberOfFile));
        } else {
            System.out.println("Incorrect input");
        }
    }
}
