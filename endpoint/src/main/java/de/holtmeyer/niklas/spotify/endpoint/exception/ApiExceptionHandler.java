package de.holtmeyer.niklas.spotify.endpoint.exception;

import kong.unirest.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException exception){
        var apiRequestException = new ApiException(
                exception.getMessage(),
                exception,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(ZoneId.systemDefault().getId()))
        );
        return new ResponseEntity<>(apiRequestException,
                HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST));
    }
}
