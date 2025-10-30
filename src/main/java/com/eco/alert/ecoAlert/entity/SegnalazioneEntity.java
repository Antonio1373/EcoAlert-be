package com.eco.alert.ecoAlert.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "segnalazione")
public class SegnalazioneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Segnalazione")
    private Integer idSegnalazione;

    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    @Column(name = "latitudine", nullable = false)
    private Double latitudine;

    @Column(name = "longitudine", nullable = false)
    private Double longitudine;

    @Column(name = "stato", nullable = false)
    private String stato = "in attesa";

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private UtenteEntity utente;
}
