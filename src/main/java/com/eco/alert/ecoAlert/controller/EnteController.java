package com.eco.alert.ecoAlert.controller;

import com.eco.alert.ecoAlert.service.UserService;
import com.ecoalert.api.EntiApi;
import com.ecoalert.model.EnteOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EnteController implements EntiApi {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<List<EnteOutput>> getAllEnti() {
        return ResponseEntity.ok(userService.getAllEnti());
    }
}
