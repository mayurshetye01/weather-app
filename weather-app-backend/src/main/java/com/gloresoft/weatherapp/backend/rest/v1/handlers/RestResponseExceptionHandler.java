package com.gloresoft.weatherapp.backend.rest.v1.handlers;

import com.gloresoft.weatherapp.backend.exceptions.NoDataFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NoDataFoundException.class})
    protected ResponseEntity<Object> handleError(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "No weather data found for this city";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}