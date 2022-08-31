package com.javarush.island.service;

public class WrongFileException extends RuntimeException {
    public WrongFileException() {
    }

    public WrongFileException(String message) {
        super(message);
    }

    public WrongFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
