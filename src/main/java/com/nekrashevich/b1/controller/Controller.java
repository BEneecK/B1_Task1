package com.nekrashevich.b1.controller;

import com.nekrashevich.b1.database.Connector;
import com.nekrashevich.b1.exception.ControllerException;
import com.nekrashevich.b1.exception.FileCombinerException;
import com.nekrashevich.b1.exception.FileRepositoryException;
import com.nekrashevich.b1.service.FileCombiner;
import com.nekrashevich.b1.service.FileRepository;
import com.nekrashevich.b1.service.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
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
    private final FileRepository fileRepository;
    private final FileCombiner fileCombiner;
    private final Validator validator;

    public Controller(FileRepository fileRepository, FileCombiner fileCombiner, Validator validator) {
        this.fileCombiner = fileCombiner;
        this.fileRepository = fileRepository;
        this.validator = validator;
    }

    public void startAppLoop() throws ControllerException {
        try {
            while (true) {
                System.out.println(CHOICE_MENU);
                Scanner scanner = new Scanner(System.in);
                String choice;

                choice = scanner.nextLine();

                if (validator.isValidInputNumber(choice)) {
                    if (choice.equals("1")) fileCombiner.combineFiles();
                    else if (choice.equals("2")) regexCombineController();
                    else if (choice.equals("3")) importFileController();
                    else if (choice.equals("4")) {
                        fileRepository.executeScript(SCRIPT_NAME_SUM);
                        fileRepository.executeScript(SCRIPT_NAME_MEDIAN);
                    } else if (choice.equals("5")) break;
                } else {
                    System.out.println("Incorrect input");
                }
            }
            Connector.closeConnection();
        } catch (InputMismatchException | FileRepositoryException | FileCombinerException | SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ControllerException(e);
        }
    }

    private void regexCombineController() throws ControllerException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Your characters: ");
        String regex = scanner.nextLine();

        if (validator.isValidInputRegex(regex)) {
            try {
                fileCombiner.combineFiles(regex);
            } catch (FileCombinerException e) {
                logger.log(Level.ERROR, e.getMessage());
                throw new ControllerException(e);
            }
        } else {
            System.out.println("Incorrect input");
        }
    }

    private void importFileController() throws ControllerException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Number of file: ");
        String numberOfFile;

        numberOfFile = scanner.nextLine();

        if (validator.isFileExist(numberOfFile)) {
            try {
                fileRepository.importFile(Integer.parseInt(numberOfFile));
            } catch (FileRepositoryException e) {
                logger.log(Level.ERROR, e.getMessage());
                throw new ControllerException(e);
            }
        } else {
            System.out.println("Incorrect input");
        }
    }
}
