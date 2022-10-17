package com.nekrashevich.b1.exception;

public class FileGenerationException extends Exception{

    public FileGenerationException() {
    }

    public FileGenerationException(String message) {
        super(message);
    }

    public FileGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileGenerationException(Throwable cause) {
        super(cause);
    }
}
