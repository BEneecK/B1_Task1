package com.nekrashevich.b1.service;

import com.nekrashevich.b1.exception.FileRepositoryException;

public interface FileRepository {
    void importFile(int numberOfFile) throws FileRepositoryException;

    void executeScript(String fileName) throws FileRepositoryException;
}
