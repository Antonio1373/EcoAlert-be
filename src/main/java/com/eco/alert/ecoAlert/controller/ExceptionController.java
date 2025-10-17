package com.eco.alert.ecoAlert.controller;


import com.eco.alert.ecoAlert.exception.EmailDuplicataException;
import com.eco.alert.ecoAlert.exception.LoginException;
import com.ecoalert.model.Error;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class ExceptionController {

    @ExceptionHandler(EmailDuplicataException.class)
    public ResponseEntity<Error> handleEmailDuplicata(EmailDuplicataException ex) {
        com.ecoalert.model.Error error = new com.ecoalert.model.Error();
        error.detail("Dati non validi");
        error.message("Email già presente nel database");
        log.error("EmailDuplicataException");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Error> handleLoginException(LoginException exception) {
        com.ecoalert.model.Error error = new com.ecoalert.model.Error();
        error.detail("Dati non validi");
        error.message(exception.getMessage());
        log.error("LoginException:  {}", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
