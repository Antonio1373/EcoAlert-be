# 🌿 EcoAlert Backend

Backend del progetto **EcoAlert**, un sistema informativo web e mobile per la segnalazione e gestione di problematiche ambientali (rifiuti, vandalismo, inquinamento, ecc.).

## ⚙️ Tecnologie utilizzate
- **Java 17**
- **Spring Boot 3**
- **Spring Security** (autenticazione JWT)
- **Spring Data JPA / Hibernate**
- **MySQL**
- **OpenAPI 3.0** (documentazione API)

## 📁 Struttura del progetto

EcoAlert-be/

├── src/

│ ├── main/java/... # codice sorgente backend

│ ├── main/resources/ # file di configurazione (application.yml)

│ └── test/ # test JUnit

├── api/

│ └── EcoAlert.yaml # definizione OpenAPI delle API

├── pom.xml # configurazione Maven

└── README.md

## 🚀 Avvio del progetto

### 1️⃣ Clonare il repository

**git clone https://github.com/Antonio1373/EcoAlert-be.git**

cd EcoAlert-be

### 2️⃣ Configurare il database

### 3️⃣ Avviare il server

#### 🧩 Endpoints principali
 
| Metodo | Endpoint             | Descrizione                  |
| :----- | :------------------- | :--------------------------- |
| `POST` | `/api/auth/login`    | Login utente                 |
| `POST` | `/api/auth/register` | Registrazione nuovo utente   |
| `GET`  | `/api/user/{id}`     | Recupero dati utente         |
| `POST` | `/api/segnalazioni`  | Creazione nuova segnalazione |

#### 🧱 Database Schema
Lo schema SQL si trova nel file:
📄 database/ecoalert_schema.sql

#### 📘 Documentazione API
La documentazione OpenAPI si trova in:
api/EcoAlert.yaml

#### 👩‍💻 Autori
Progetto realizzato da Antonio Granato per il corso di Informatica – EcoAlert Project.


