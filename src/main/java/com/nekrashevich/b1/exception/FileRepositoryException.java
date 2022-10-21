package com.nekrashevich.b1.exception;

public class FileRepositoryException extends Exception {
    public FileRepositoryException() {
    }

    public FileRepositoryException(String message) {
        super(message);
    }

    public FileRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileRepositoryException(Throwable cause) {
        super(cause);
    }
}
