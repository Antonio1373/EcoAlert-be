package com.eco.alert.ecoAlert.controller;

import com.eco.alert.ecoAlert.service.SegnalazioneService;
import com.eco.alert.ecoAlert.service.UserService;
import com.ecoalert.api.DefaultApi;
import com.ecoalert.model.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
public class UserController implements DefaultApi {

    @Autowired
    private UserService utenteService;

    @Override
    public ResponseEntity<UtenteOutput> signIn(UtenteInput utenteInput) {
        log.info("signIn api");
        return ResponseEntity.ok(utenteService.creaUtente(utenteInput));
    }

    @Override
    public ResponseEntity<LoginOutput> login(LoginInput loginInput){
        log.info("login api");
        return ResponseEntity.ok(utenteService.login(loginInput));
    }

    @Override
    public ResponseEntity<UtenteDettaglioOutput> getUserById(Integer id) {
        log.info("Richiesta dettaglio utente con ID {}", id);
        UtenteDettaglioOutput utente = utenteService.getUserById(id);
        return ResponseEntity.ok(utente);
    }

    @Override
    public ResponseEntity<List<EnteOutput>> getAllEnti() {
        log.info("getAllEnti api");
        return ResponseEntity.ok(utenteService.getAllEnti());
    }

    @Autowired
    private SegnalazioneService segnalazioneService;

    @Override
    public ResponseEntity<SegnalazioneOutput> createSegnalazione(Integer id, SegnalazioneInput segnalazioneInput) {
        log.info("POST /user/{}/segnalazioni", id);
        return ResponseEntity.status(201).body(segnalazioneService.creaSegnalazione(id, segnalazioneInput));
    }

}
