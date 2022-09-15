package com.javarush.island.population.abstracts;

public class WrongEntityException extends RuntimeException{
    public WrongEntityException() {
    }

    public WrongEntityException(String message) {
        super(message);
    }

    public WrongEntityException(String message, Throwable cause) {
        super(message, cause);
    }

}
