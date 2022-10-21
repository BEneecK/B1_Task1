package com.nekrashevich.b1.service;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerService {
    public static boolean isValidInputNumber(String input) {
        Pattern pattern = Pattern.compile("[1-5]");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) return true;
        return false;
    }

    public static boolean isFileExist(String numberOfFile) {
        String fileName = numberOfFile + ".txt";
        File file = new File(fileName);
        if (file.exists()) return true;
        return false;
    }

    public static boolean isValidInputRegex(String input) {
        Pattern pattern = Pattern.compile("^[a-zA-Zа-яА-Я]+$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) return true;
        return false;
    }
}
