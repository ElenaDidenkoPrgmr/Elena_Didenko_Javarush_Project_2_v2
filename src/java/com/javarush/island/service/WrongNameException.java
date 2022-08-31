package com.javarush.island.service;

public class WrongNameException extends RuntimeException {
    public WrongNameException() {
    }

    public WrongNameException(String message) {
        super(message);
    }

    public WrongNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
