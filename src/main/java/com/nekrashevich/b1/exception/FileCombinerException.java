package com.nekrashevich.b1.exception;

public class FileCombinerException extends Exception {
    public FileCombinerException() {
    }

    public FileCombinerException(String message) {
        super(message);
    }

    public FileCombinerException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileCombinerException(Throwable cause) {
        super(cause);
    }
}
