package com.wmg.springbootrest.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }

    public static BookNotFoundException instance(String message) {
        return new BookNotFoundException(message);
    }
}
