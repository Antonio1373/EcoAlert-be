package com.eco.alert.ecoAlert.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "commenti")
public class CommentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Commento")
    private Integer idCommento;

    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    @ManyToOne
    @JoinColumn(name="id")
    private UtenteEntity utente;

    @ManyToOne
    @JoinColumn(name="id_Segnalazione")
    private SegnalazioneEntity segnalazione;

}
