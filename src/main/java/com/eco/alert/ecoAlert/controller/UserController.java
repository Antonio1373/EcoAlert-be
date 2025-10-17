package com.eco.alert.ecoAlert.controller;

import com.eco.alert.ecoAlert.service.UserService;
import com.ecoalert.api.DefaultApi;
import com.ecoalert.model.LoginInput;
import com.ecoalert.model.LoginOutput;
import com.ecoalert.model.UtenteInput;
import com.ecoalert.model.UtenteOutput;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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

}
