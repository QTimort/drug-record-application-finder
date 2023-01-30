package com.diguiet.draf.back.controllers.rest;

import com.diguiet.draf.back.exceptions.InvalidParameterException;
import com.diguiet.draf.common.models.api.error.ApiError;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {HttpClientErrorException.NotFound.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiError resourceNotFoundException(HttpClientErrorException.NotFound ex, WebRequest request) {
        return new ApiError("The server is unable to find the requested resource.");
    }

    @ExceptionHandler(value = {HttpClientErrorException.BadRequest.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError badRequestException(HttpClientErrorException.BadRequest ex, WebRequest request) {
        return new ApiError("The request is not valid.");
    }

    @ExceptionHandler(value = {InvalidParameterException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError invalidParameterException(InvalidParameterException ex, WebRequest request) {
        return new ApiError(ex.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError unknownException(Exception ex, WebRequest request) {
        log.warn("Unhandled exception", ex);
        return new ApiError("An unknown server error occurred. Please contact the server administrator.");
    }
}