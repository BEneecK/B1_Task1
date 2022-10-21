package com.nekrashevich.b1.service;

import com.nekrashevich.b1.exception.FileCombinerException;

public interface FileCombiner {
    void combineFiles() throws FileCombinerException;

    void combineFiles(String regex) throws FileCombinerException;
}
