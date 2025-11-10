package com.eco.alert.ecoAlert.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "cittadino")
@PrimaryKeyJoinColumn(name = "id")
public class CittadinoEntity extends UtenteEntity{

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @Column(name = "nazione")
    private String nazione;

    @Column(name = "citta")
    private String citta;
}
