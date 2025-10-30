-- ==================================================
--  ECOALERT DATABASE SCHEMA - MySQL
-- ==================================================

CREATE DATABASE IF NOT EXISTS ecoalert;
USE ecoalert;

-- ==========================
--  TABELLA: Utente
-- ==========================
CREATE TABLE Utente (
    id_Utente INT PRIMARY KEY AUTO_INCREMENT,
    Email VARCHAR(100) NOT NULL,
    Password VARCHAR(255) NOT NULL
);

-- =====================================
-- TABELLA: CITTADINO (sottoclasse di Utente)
-- =====================================
CREATE TABLE Cittadino (
    id_Cittadino INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(50) NOT NULL,
    Cognome VARCHAR(50) NOT NULL,
    id_Utente INT NOT NULL,
    FOREIGN KEY (id_Utente) REFERENCES Utente(id_Utente)
        ON DELETE CASCADE
);

-- =====================================
-- TABELLA: ENTE (sottoclasse di Utente)
-- =====================================
CREATE TABLE Ente (
    id_Ente INT PRIMARY KEY AUTO_INCREMENT,
    Nome_Ente VARCHAR(50) NOT NULL,
    Paese VARCHAR(100) NOT NULL,
    Nazione VARCHAR(100) NOT NULL,
    id_Utente INT NOT NULL,
    FOREIGN KEY (id_Utente) REFERENCES Utente(id_Utente)
        ON DELETE CASCADE
);

-- =====================================
-- TABELLA: SEGNALAZIONE
-- =====================================
CREATE TABLE Segnalazione (
    id_Segnalazione INT PRIMARY KEY AUTO_INCREMENT,
    descrizione TEXT NOT NULL,
    latitudine DECIMAL(10, 7),
    longitudine DECIMAL(10, 7),
    stato ENUM('in attesa', 'in corso', 'risolta') DEFAULT 'in attesa',
    id_Utente INT NOT NULL,
    FOREIGN KEY (id_Utente) REFERENCES Utente(id_Utente)
        ON DELETE CASCADE
);

-- =====================================
-- TABELLA: COMMENTI
-- =====================================
CREATE TABLE Commenti (
    id_Commento INT PRIMARY KEY AUTO_INCREMENT,
    id_Segnalazione INT NOT NULL,
    id_Utente INT NOT NULL,
    testo TEXT,
    likes INT DEFAULT 0,
    FOREIGN KEY (id_Segnalazione) REFERENCES Segnalazione(id_Segnalazione)
        ON DELETE CASCADE,
    FOREIGN KEY (id_Utente) REFERENCES Utente(id_Utente)
        ON DELETE CASCADE
);
