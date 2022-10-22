package com.nekrashevich.b1.service;

public interface Validator {
    boolean isValidInputNumber(String input);

    boolean isFileExist(String numberOfFile);

    boolean isValidInputRegex(String input);
}
