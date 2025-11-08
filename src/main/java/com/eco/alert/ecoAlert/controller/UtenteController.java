package com.eco.alert.ecoAlert.controller;

import com.eco.alert.ecoAlert.service.UserService;
import com.ecoalert.api.UtentiApi;
import com.ecoalert.model.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@RestController
@Log4j2
public class UtenteController implements UtentiApi {

    @Autowired
    private UserService utenteService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
            return Optional.empty();
    }

    @Override
    public ResponseEntity<UtenteDettaglioOutput> getUserById(Integer id) {
        log.info("Richiesta dettaglio utente con ID {}", id);
        UtenteDettaglioOutput utente = utenteService.getUserById(id);
        return ResponseEntity.ok(utente);
    }

}
