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
import java.util.List;

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
            throw new IdODatiMancantiException("ID utente o dati della segnalazione mancanti");

        if (!StringUtils.hasText(input.getTitolo()))
            throw new TitoloMancanteException("Titolo obbligatorio");

        if (!StringUtils.hasText(input.getDescrizione()))
            throw new DescrizioneMancanteException("Descrizione obbligatoria");

        if (input.getIdEnte() == null)
            throw new EnteNonTrovatoException("ID ente obbligatorio");

        UtenteEntity utente = utenteDao.findById(idUtente)
                .orElseThrow(() -> new UtenteNonTrovatoException("Utente con ID " + idUtente + " non trovato."));

        if (!(utente instanceof CittadinoEntity))
            throw new UtenteNonCittadinoException("Solo i cittadini possono creare segnalazioni");

        EnteEntity ente = enteDao.findById(input.getIdEnte())
                .orElseThrow(() -> new EnteNonTrovatoException("Ente non trovato"));

        SegnalazioneEntity segnalazione = new SegnalazioneEntity();
        segnalazione.setTitolo(input.getTitolo());
        segnalazione.setDescrizione(input.getDescrizione());
        segnalazione.setLatitudine(input.getLatitudine());
        segnalazione.setLongitudine(input.getLongitudine());
        segnalazione.setCittadino((CittadinoEntity) utente); // mapping corretto
        segnalazione.setEnte(ente);
        segnalazione.setStato(StatoSegnalazione.INSERITO);

        SegnalazioneEntity salvata = segnalazioneDao.save(segnalazione);
        log.info("Segnalazione {} creata in stato {}", salvata.getIdSegnalazione(), salvata.getStato());

        return toOutput(salvata);
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
            throw new EnteNonAutorizzatoException("Questo ente non può modificare la segnalazione");
        }

        // Aggiorna stato
        segnalazione.setStato(nuovoStato);
        SegnalazioneEntity salvata = segnalazioneDao.save(segnalazione);

        return toOutput(salvata);
    }

    private List<SegnalazioneOutput> mapToOutputList(List<SegnalazioneEntity> entities) {
        return entities.stream().map(se -> {
            SegnalazioneOutput output = new SegnalazioneOutput();
            output.setId(se.getIdSegnalazione());
            output.setTitolo(se.getTitolo());
            output.setDescrizione(se.getDescrizione());
            output.setLatitudine(se.getLatitudine());
            output.setLongitudine(se.getLongitudine());
            output.setStato(StatoEnum.valueOf(se.getStato().name()));
            output.setIdUtente(se.getCittadino().getId());
            output.setIdEnte(se.getEnte().getId());
            return output;
        }).toList();
    }

    public SegnalazioneOutput toOutput(SegnalazioneEntity entity) {
        SegnalazioneOutput output = new SegnalazioneOutput();
        output.setId(entity.getIdSegnalazione());
        output.setTitolo(entity.getTitolo());
        output.setDescrizione(entity.getDescrizione());
        output.setLatitudine(entity.getLatitudine());
        output.setLongitudine(entity.getLongitudine());
        output.setStato(StatoEnum.valueOf(entity.getStato().name()));
        output.setIdUtente(entity.getCittadino().getId());
        output.setIdEnte(entity.getEnte().getId());
        return output;
    }


    public List<SegnalazioneOutput> getSegnalazioniByUserId(Integer id) {
        UtenteEntity utente = utenteDao.findById(id)
                .orElseThrow(() -> new UtenteNonTrovatoException("Utente non trovato."));

        if (utente instanceof CittadinoEntity) {
            List<SegnalazioneEntity> segnalazioni = segnalazioneDao.findByCittadino_Id(id);
            return mapToOutputList(segnalazioni);
        }

        if (utente instanceof EnteEntity) {
            List<SegnalazioneEntity> segnalazioni = segnalazioneDao.findByEnte_Id(id);
            return mapToOutputList(segnalazioni);
        }

        throw new RuntimeException("Ruolo utente non valido");
    }

    public SegnalazioneOutput getSegnalazioneById(Integer idUtente, Integer idSegnalazione) {

        // Recupera la segnalazione
        SegnalazioneEntity segnalazione = segnalazioneDao.findById(idSegnalazione)
                .orElseThrow(() -> new SegnalazioneNonTrovataException("Segnalazione non trovata"));

        // Recupera l'utente
        UtenteEntity utente = utenteDao.findById(idUtente)
                .orElseThrow(() -> new UtenteNonTrovatoException("Utente non trovato"));

        // Controlla i permessi
        if (utente instanceof CittadinoEntity) {
            // Il cittadino può vedere solo le proprie segnalazioni
            if (!segnalazione.getCittadino().getId().equals(idUtente)) {
                throw new AccessoNonAutorizzatoException("Non puoi vedere questa segnalazione");
            }
        } else if (utente instanceof EnteEntity) {
            // L'ente può vedere solo le segnalazioni associate al proprio ente
            if (!segnalazione.getEnte().getId().equals(idUtente)) {
                throw new AccessoNonAutorizzatoException("Non puoi vedere questa segnalazione");
            }
        } else {
            throw new AccessoNonAutorizzatoException("Tipo utente non autorizzato");
        }

        // Converte Entity -> DTO
        return toOutput(segnalazione);
    }

}
