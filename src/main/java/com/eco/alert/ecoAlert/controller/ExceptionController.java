package com.eco.alert.ecoAlert.controller;


import com.eco.alert.ecoAlert.exception.EmailDuplicataException;
import com.eco.alert.ecoAlert.exception.LoginException;
import com.eco.alert.ecoAlert.exception.UtenteNonCittadinoException;
import com.eco.alert.ecoAlert.exception.UtenteNonTrovatoException;
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

    @ExceptionHandler(UtenteNonTrovatoException.class)
    public ResponseEntity<Error> handleUserNotFound(UtenteNonTrovatoException ex) {
        com.ecoalert.model.Error error = new com.ecoalert.model.Error();
        error.detail("Utente non trovato");
        error.message(ex.getMessage());
        log.error("UtenteNonTrovatoException: {}", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UtenteNonCittadinoException.class)
    public ResponseEntity<Error> handleUserNotFound(UtenteNonCittadinoException ex) {
        com.ecoalert.model.Error error = new com.ecoalert.model.Error();
        error.detail("Solo i cittadini possono creare segnalazioni");
        error.message(ex.getMessage());
        log.error("UtenteNonCittadinoException: {}", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

}
