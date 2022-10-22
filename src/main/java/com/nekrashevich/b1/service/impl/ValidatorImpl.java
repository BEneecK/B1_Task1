package com.nekrashevich.b1.service.impl;

import com.nekrashevich.b1.service.Validator;

import java.io.File;

public class ValidatorImpl implements Validator {
    private static final String NUMBER_REGEX = "[1-5]";
    private static final String FILE_EXTENSION = ".txt";
    private static final String INPUT_STRING_REGEX = "^[a-zA-Zа-яА-Я]+$";

    @Override
    public boolean isValidInputNumber(String input) {
        return input != null && input.matches(NUMBER_REGEX);
    }

    @Override
    public boolean isFileExist(String numberOfFile) {
        String fileName = numberOfFile + FILE_EXTENSION;
        File file = new File(fileName);
        return file.exists();
    }

    @Override
    public boolean isValidInputRegex(String input) {
        return input != null && input.matches(INPUT_STRING_REGEX);
    }
}
