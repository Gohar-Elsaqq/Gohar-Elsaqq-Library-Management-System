package com.library.exception;

public class DaplicateRecoredException extends RuntimeException{
    public DaplicateRecoredException() {
    }

    public DaplicateRecoredException(String message) {
        super(message);
    }

}
