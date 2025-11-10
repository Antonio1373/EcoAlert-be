package com.eco.alert.ecoAlert.controller;


import com.eco.alert.ecoAlert.enums.StatoSegnalazione;
import com.eco.alert.ecoAlert.service.SegnalazioneService;
import com.ecoalert.api.SegnalazioniApi;
import com.ecoalert.model.SegnalazioneInput;
import com.ecoalert.model.SegnalazioneOutput;
import com.ecoalert.model.StatoUpdateInput;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
public class SegnalazioneController implements SegnalazioniApi {

    @Autowired
    private SegnalazioneService segnalazioneService;

    @Override
    public ResponseEntity<SegnalazioneOutput> createSegnalazione(
            Integer id,
            SegnalazioneInput segnalazioneInput
    ) {
        log.info("POST /user/{}/segnalazione", id);
        return ResponseEntity.status(201).body(segnalazioneService.creaSegnalazione(id, segnalazioneInput));
    }

    @Override
    public ResponseEntity<SegnalazioneOutput> updateSegnalazioneStatus(
            Integer idSegnalazione,
            Integer idEnte,
            StatoUpdateInput statoUpdateInput
    ) {
        System.out.println("Controller → idSegnalazione: " + idSegnalazione);
        System.out.println("Controller → idEnte: " + idEnte);
        System.out.println("Controller → stato richiesto: " + statoUpdateInput.getStato());

        // Converti enum OpenAPI → enum interno
        StatoSegnalazione nuovoStato =
                StatoSegnalazione.valueOf(statoUpdateInput.getStato().name());

        SegnalazioneOutput updated =
                segnalazioneService.aggiornaStatoSegnalazione(
                        idEnte,
                        idSegnalazione,
                        nuovoStato
                );

        return ResponseEntity.ok(updated);
    }
}




