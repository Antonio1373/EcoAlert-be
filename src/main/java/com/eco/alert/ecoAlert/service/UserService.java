package com.eco.alert.ecoAlert.service;

import com.eco.alert.ecoAlert.dao.CittadinoDao;
import com.eco.alert.ecoAlert.dao.EnteDao;
import com.eco.alert.ecoAlert.entity.CittadinoEntity;
import com.eco.alert.ecoAlert.entity.EnteEntity;
import com.eco.alert.ecoAlert.entity.UtenteEntity;
import com.eco.alert.ecoAlert.dao.UtenteDao;
import com.eco.alert.ecoAlert.exception.EmailDuplicataException;
import com.eco.alert.ecoAlert.exception.LoginException;
import com.eco.alert.ecoAlert.exception.UtenteNonTrovatoException;
import com.ecoalert.model.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class UserService {

    @Autowired
    private UtenteDao utenteDao;

    @Autowired
    private CittadinoDao cittadinoDao;

    @Autowired
    private EnteDao enteDao;

    public UtenteOutput creaUtente(UtenteInput input) {
        log.info("Creazione nuovo utente...");

        UtenteEntity utenteConStessaMail = utenteDao.findByEmail(input.getEmail());
        if (utenteConStessaMail != null) {
            throw new EmailDuplicataException();
        }

        UtenteOutput output = new UtenteOutput();

        if ("cittadino".equalsIgnoreCase(input.getRuolo())) {
            CittadinoEntity cittadino = new CittadinoEntity();
            cittadino.setEmail(input.getEmail());
            cittadino.setPassword(input.getPassword());
            cittadino.setNome(input.getNome());
            cittadino.setCognome(input.getCognome());
            cittadino.setNazione(input.getNazione());
            cittadino.setCitta(input.getPaese());

            CittadinoEntity saved = cittadinoDao.save(cittadino); // salva e ottieni l’ID generato

            output.setId(saved.getId());
            output.setEmail(saved.getEmail());
            output.setRuolo("cittadino");

        } else if ("ente".equalsIgnoreCase(input.getRuolo())) {
            EnteEntity ente = new EnteEntity();
            ente.setEmail(input.getEmail());
            ente.setPassword(input.getPassword());
            ente.setNomeEnte(input.getNome());
            ente.setCittaEnte(input.getPaese());
            ente.setNazioneEnte(input.getNazione());

            EnteEntity saved = enteDao.save(ente);

            output.setId(saved.getId());
            output.setEmail(saved.getEmail());
            output.setRuolo("ente");

        } else {
            throw new IllegalArgumentException("Ruolo non valido.");
        }

        return output;
    }

    public LoginOutput login (LoginInput loginInput){
        log.info("Login Utente...");

        UtenteEntity utente = utenteDao.findByEmail(loginInput.getEmail());
        if(utente == null){
            throw new LoginException("Email non presente nel database.");
        }

        if(!utente.getPassword().equals(loginInput.getPassword())){
            throw new LoginException("Password Errata.");
        }

        String ruolo = (utente instanceof CittadinoEntity) ? "cittadino" : "ente";
        LoginOutput output = new LoginOutput();
        output.setRuolo(ruolo);
        output.setUserId(utente.getId());
        return output;
    }

    public UtenteDettaglioOutput getUserById(Integer id) {
        log.info("Recupero utente con ID {}", id);

        // Cerca l'utente nella tabella base
        UtenteEntity utente = utenteDao.findById(id)
                .orElseThrow(() -> new UtenteNonTrovatoException("Utente con ID " + id + " non trovato."));

        UtenteDettaglioOutput output = new UtenteDettaglioOutput();
        output.setId(utente.getId());
        output.setEmail(utente.getEmail());

        // Controlla il tipo effettivo dell’utente
        if (utente instanceof CittadinoEntity cittadino) {
            output.setRuolo("cittadino");
            output.setNome(cittadino.getNome());
            output.setCognome(cittadino.getCognome());
            output.setPaese(cittadino.getCitta());
            output.setNazione(cittadino.getNazione());
        } else if (utente instanceof EnteEntity ente) {
            output.setRuolo("ente");
            output.setNomeEnte(ente.getNomeEnte());
            output.setPaese(ente.getCittaEnte());
            output.setNazione(ente.getNazioneEnte());
        }

        return output;
    }

    public List<EnteOutput> getAllEnti() {
        List<EnteEntity> enti = enteDao.findAll();
        List<EnteOutput> result = new ArrayList<>();

        for (EnteEntity ente : enti) {
            EnteOutput enteOutput = new EnteOutput();
            enteOutput.setId(ente.getId());
            enteOutput.setNomeEnte(ente.getNomeEnte());
            enteOutput.setPaese(ente.getCittaEnte());
            enteOutput.setNazione(ente.getNazioneEnte());
            enteOutput.setEmail(ente.getEmail());
            result.add(enteOutput);
        }

        return result;
    }

}
