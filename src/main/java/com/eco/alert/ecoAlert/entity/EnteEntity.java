package com.eco.alert.ecoAlert.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ente")
@PrimaryKeyJoinColumn(name = "id")
public class EnteEntity extends UtenteEntity{

    @Column(name = "nome_Ente")
    private String nomeEnte;

    @Column(name = "citta")
    private String cittaEnte;

    @Column(name = "nazione")
    private String nazioneEnte;
}
