package com.gloresoft.weatherapp.backend.exceptions;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String message) {
        super(message);
    }
}
