package com.eco.alert.ecoAlert.service;

import com.eco.alert.ecoAlert.dao.CittadinoDao;
import com.eco.alert.ecoAlert.dao.SegnalazioneDao;
import com.eco.alert.ecoAlert.dao.UtenteDao;
import com.eco.alert.ecoAlert.entity.CittadinoEntity;
import com.eco.alert.ecoAlert.entity.SegnalazioneEntity;
import com.eco.alert.ecoAlert.entity.UtenteEntity;
import com.eco.alert.ecoAlert.exception.UtenteNonCittadinoException;
import com.eco.alert.ecoAlert.exception.UtenteNonTrovatoException;
import com.ecoalert.model.SegnalazioneInput;
import com.ecoalert.model.SegnalazioneOutput;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SegnalazioneService {

    @Autowired
    private UtenteDao utenteDao;

    @Autowired
    private CittadinoDao cittadinoDao;

    @Autowired
    private SegnalazioneDao segnalazioneDao;

    public SegnalazioneOutput creaSegnalazione(Integer idUtente, SegnalazioneInput input) {
        log.info("Creazione segnalazione per utente con ID {}", idUtente);

        UtenteEntity utente = utenteDao.findById(idUtente)
                .orElseThrow(() -> new UtenteNonTrovatoException("Utente con ID " + idUtente + " non trovato."));

        if (!(utente instanceof CittadinoEntity)) {
            throw new UtenteNonCittadinoException("L'utente con ID " + idUtente + " non è un cittadino");
        }

        SegnalazioneEntity segnalazione = new SegnalazioneEntity();
        segnalazione.setDescrizione(input.getDescrizione());
        segnalazione.setLatitudine(input.getLatitudine());
        segnalazione.setLongitudine(input.getLongitudine());
        segnalazione.setStato("in attesa");
        segnalazione.setUtente(utente);

        segnalazioneDao.save(segnalazione);

        SegnalazioneOutput output = new SegnalazioneOutput();
        output.setId(segnalazione.getIdSegnalazione());
        output.setDescrizione(segnalazione.getDescrizione());
        output.setLatitudine(segnalazione.getLatitudine());
        output.setLongitudine(segnalazione.getLongitudine());
        output.setStato(segnalazione.getStato());
        output.setIdUtente(idUtente);

        return output;
    }
}

