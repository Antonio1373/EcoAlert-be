package com.eco.alert.ecoAlert.service;

import com.eco.alert.ecoAlert.dao.EnteDao;
import com.eco.alert.ecoAlert.dao.SegnalazioneDao;
import com.eco.alert.ecoAlert.dao.UtenteDao;
import com.eco.alert.ecoAlert.entity.EnteEntity;
import com.eco.alert.ecoAlert.entity.SegnalazioneEntity;
import com.eco.alert.ecoAlert.entity.UtenteEntity;
import com.eco.alert.ecoAlert.entity.CittadinoEntity;
import com.eco.alert.ecoAlert.enums.StatoSegnalazione;
import com.eco.alert.ecoAlert.exception.*;
import com.ecoalert.model.SegnalazioneInput;
import com.ecoalert.model.SegnalazioneOutput;
import com.ecoalert.model.StatoEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Log4j2
public class SegnalazioneService {

    @Autowired
    private SegnalazioneDao segnalazioneDao;

    @Autowired
    private EnteDao enteDao;

    @Autowired
    private UtenteDao utenteDao;

    @Transactional
    public SegnalazioneOutput creaSegnalazione(Integer idUtente, SegnalazioneInput input) {
        log.info("Creazione segnalazione per utente con ID {}", idUtente);

        if (idUtente == null || input == null)
            throw new IllegalArgumentException("ID utente o dati della segnalazione mancanti");
        if (!StringUtils.hasText(input.getDescrizione()))
            throw new IllegalArgumentException("Descrizione obbligatoria");
        if (input.getLatitudine() == null || input.getLongitudine() == null)
            throw new IllegalArgumentException("Coordinate obbligatorie");
        if (input.getIdEnte() == null)
            throw new IllegalArgumentException("ID ente obbligatorio");

        UtenteEntity utente = utenteDao.findById(idUtente)
                .orElseThrow(() -> new UtenteNonTrovatoException("Utente con ID " + idUtente + " non trovato."));

        if (!(utente instanceof CittadinoEntity))
            throw new UtenteNonCittadinoException("Solo i cittadini possono creare segnalazioni");

        EnteEntity ente = enteDao.findById(input.getIdEnte())
                .orElseThrow(() -> new EnteNonTrovatoException("Ente non trovato"));

        SegnalazioneEntity segnalazione = new SegnalazioneEntity();
        segnalazione.setDescrizione(input.getDescrizione().trim());
        segnalazione.setLatitudine(input.getLatitudine());
        segnalazione.setLongitudine(input.getLongitudine());
        segnalazione.setCittadino((CittadinoEntity) utente); // mapping corretto
        segnalazione.setEnte(ente);
        segnalazione.setStato(StatoSegnalazione.INSERITO);

        SegnalazioneEntity salvata = segnalazioneDao.save(segnalazione);
        log.info("Segnalazione {} creata in stato {}", salvata.getIdSegnalazione(), salvata.getStato());

        SegnalazioneOutput output = new SegnalazioneOutput();
        output.setId(salvata.getIdSegnalazione());
        output.setDescrizione(salvata.getDescrizione());
        output.setLatitudine(salvata.getLatitudine());
        output.setLongitudine(salvata.getLongitudine());
        output.setStato(StatoEnum.valueOf(salvata.getStato().name())); // mappatura enum
        output.setIdUtente(utente.getId());
        output.setIdEnte(ente.getId());
        return output;
    }

    public SegnalazioneOutput aggiornaStatoSegnalazione(
            Integer idEnte,
            Integer idSegnalazione,
            StatoSegnalazione nuovoStato) {

        log.info("Aggiornamento segnalazione {} da parte dell'ente {}", idSegnalazione, idEnte);

        // Verifica esistenza segnalazione
        SegnalazioneEntity segnalazione = segnalazioneDao.findById(idSegnalazione)
                .orElseThrow(() -> new SegnalazioneNonTrovataException("Segnalazione non trovata"));

        // Verifica esistenza ente
        EnteEntity ente = enteDao.findById(idEnte)
                .orElseThrow(() -> new EnteNonTrovatoException("Ente non trovato"));

        // Controllo autorizzazione: solo l'ente associato può aggiornare
        if (!segnalazione.getEnte().getId().equals(ente.getId())) {
            throw new EnteSbagliatoException("Questo ente non può modificare la segnalazione");
        }

        // Aggiorna stato
        segnalazione.setStato(nuovoStato);
        SegnalazioneEntity salvata = segnalazioneDao.save(segnalazione);

        // Mappa output
        SegnalazioneOutput output = new SegnalazioneOutput();
        output.setId(salvata.getIdSegnalazione());
        output.setDescrizione(salvata.getDescrizione());
        output.setLatitudine(salvata.getLatitudine());
        output.setLongitudine(salvata.getLongitudine());
        output.setStato(StatoEnum.valueOf(salvata.getStato().name()));
        output.setIdUtente(salvata.getCittadino().getId());
        output.setIdEnte(salvata.getEnte().getId());

        return output;
    }
}
