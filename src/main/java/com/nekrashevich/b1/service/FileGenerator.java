package com.nekrashevich.b1.service;

import com.nekrashevich.b1.exception.FileGenerationException;

public interface FileGenerator {
    void generateFiles() throws FileGenerationException;
}
