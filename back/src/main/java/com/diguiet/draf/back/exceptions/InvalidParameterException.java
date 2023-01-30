package com.diguiet.draf.back.exceptions;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@StandardException
public class InvalidParameterException extends RuntimeException {
}