package com.eco.alert.ecoAlert.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "utente", schema = "ecoAlert-db")
public class UtenteEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    @NotBlank
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

}
