package com.javarush.island.service;

public class WrongSettingException extends RuntimeException {
    public WrongSettingException() {
    }

    public WrongSettingException(String message) {
        super(message);
    }

    public WrongSettingException(String message, Throwable cause) {
        super(message, cause);
    }
}
