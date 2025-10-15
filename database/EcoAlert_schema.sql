-- ==================================================
--  ECOALERT DATABASE SCHEMA - MySQL
-- ==================================================

CREATE DATABASE IF NOT EXISTS ecoalert;
USE ecoalert;

-- ==========================
--  TABELLA: Utente
-- ==========================
CREATE TABLE utente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    ruolo ENUM('cittadino', 'amministratore') NOT NULL DEFAULT 'cittadino',
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================
--  TABELLA: Tipologia
-- ==========================
CREATE TABLE tipologia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

-- ==========================
--  TABELLA: Stato
-- ==========================
CREATE TABLE stato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome ENUM('In attesa', 'In gestione', 'Risolta') NOT NULL
);

-- ==========================
--  TABELLA: Ente
-- ==========================
CREATE TABLE ente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    area_geografica VARCHAR(255)
);

-- ==========================
--  TABELLA: Segnalazione
-- ==========================
CREATE TABLE segnalazione (
    id INT AUTO_INCREMENT PRIMARY KEY,
    utente_id INT NOT NULL,
    tipo_id INT NOT NULL,
    stato_id INT NOT NULL,
    descrizione TEXT,
    lat DECIMAL(10, 7),
    lng DECIMAL(10, 7),
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ente_id INT,
    FOREIGN KEY (utente_id) REFERENCES utente(id) ON DELETE CASCADE,
    FOREIGN KEY (tipo_id) REFERENCES tipologia(id) ON DELETE RESTRICT,
    FOREIGN KEY (stato_id) REFERENCES stato(id) ON DELETE RESTRICT,
    FOREIGN KEY (ente_id) REFERENCES ente(id) ON DELETE SET NULL
);

-- ==========================
--  TABELLA: Allegato
-- ==========================
CREATE TABLE allegato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    segnalazione_id INT NOT NULL,
    url_file VARCHAR(255) NOT NULL,
    tipo ENUM('foto', 'video', 'altro') DEFAULT 'foto',
    FOREIGN KEY (segnalazione_id) REFERENCES segnalazione(id) ON DELETE CASCADE
);
