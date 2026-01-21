package com.eco.alert.ecoAlert.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "allegati")
public class AllegatoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_allegato;

    @Column(name = "nome_file", nullable = false)
    private String nomeFile;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Lob
    @Column(name = "file_data", nullable = false)
    private byte[] fileData;

    @Column(name = "data_caricamento")
    private LocalDateTime dataCaricamento;

    @ManyToOne
    @JoinColumn(name = "id_segnalazione", nullable = false)
    private SegnalazioneEntity segnalazione;
}
