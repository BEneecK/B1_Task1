package com.nekrashevich.b1.service;

import com.nekrashevich.b1.exception.FileCombinerException;

import java.io.FileNotFoundException;

public interface FileCombiner {
    void combineFiles() throws FileCombinerException;
    void combineFiles(String regex);
}
