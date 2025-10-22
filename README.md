# hexagonal-architecture-approach
# Architettura Esagonale (Hexagonal Architecture / Ports and Adapters)

L’**architettura esagonale**, anche nota come **Ports and Adapters**, è un'architettura software ideata da **Alistair Cockburn** con l’obiettivo di rendere le applicazioni **indipendenti da framework, tecnologie esterne e interfacce utente**, facilitando **testabilità, manutenibilità e sostituibilità dei componenti**.

---

## 🧩 Cos’è l’architettura esagonale?

L’idea centrale è che **l’applicazione core (il dominio)** stia al **centro**, isolata da tutto il resto. Tutte le interazioni con il mondo esterno (come database, interfacce grafiche, API REST, file system, messaggi Kafka, ecc.) sono considerate **"adattatori"** e comunicano con il core tramite **"porte" (ports)**.

> Il dominio **non conosce** nulla dell’infrastruttura: è il mondo esterno ad adattarsi al dominio, non il contrario.

---

## 📐 Strutture principali dell’architettura esagonale

### 1. Dominio (Core Application / Inside)

Questa è la parte **pura e indipendente** dell’applicazione:
- Contiene la **logica di business**.
- Non dipende da alcun framework o libreria esterna.
- È composta da:
    - **Entità** (Entities): oggetti centrali con regole di business.
    - **Servizi di dominio**: logica di business che non può stare in un’entità.
    - **Porte (Ports)**: interfacce che definiscono ciò che il dominio **richiede** (output port) o **offre** (input port).

---

### 2. Porte (Ports)

Le **ports** sono interfacce che definiscono i **punti di ingresso e uscita** dal core.

- **Input Ports (driven ports)**: definiscono cosa può fare il dominio (es: `CreaOrdine`, `CalcolaPrezzo`).
    - Sono implementate da componenti esterni (es. controller REST, consumer Kafka, CLI).

- **Output Ports (driving ports)**: definiscono cosa serve al dominio (es: `OrdineRepository`, `NotificaService`).
    - Sono implementate da componenti infrastrutturali (es: database, HTTP client, sistemi di messaggistica).

---

### 3. Adattatori (Adapters / Outside)

Gli **adapters** sono le **implementazioni** concrete delle interfacce (ports), e si trovano alla **periferia** dell’esagono.

- **Adattatori di ingresso (driving adapters)**:
    - Usano gli **input ports** per attivare il dominio.
    - Esempi: Controller REST, CLI, messaggi RabbitMQ, schedulazioni cron.

- **Adattatori di uscita (driven adapters)**:
    - Implementano gli **output ports** richiesti dal dominio.
    - Esempi: Repository JPA, chiamate REST a servizi esterni, invio email, salvataggio su file system.

---

## 🧱 Schema visivo (semplificato)

      [ UI / REST / CLI ]       <-- driving adapters (input)
              |
              v
     [ Input Port / Application Service ]
              |
     ----------------------------
     |       Core Domain         |
     |  - Entità                |
     |  - Servizi di dominio   |
     |  - Output Ports         |
     ----------------------------
              |
              v

---

## ✅ Vantaggi principali

- **Testabilità**: il dominio è facilmente testabile senza dipendenze esterne.
- **Separazione delle responsabilità**: ogni parte ha un compito ben definito.
- **Flessibilità e sostituibilità**: puoi cambiare il tipo di input (REST → gRPC) o output (JPA → Mongo) senza toccare il dominio.
- **Isolamento del dominio**: il cuore dell’app è indipendente da framework e tecnologie.

---

## 🔄 Confronto con architettura a strati (Layered)

| Architettura a strati             | Architettura Esagonale              |
|-----------------------------------|-------------------------------------|
| Dipendenze solo top-down          | Dipendenze **verso il dominio**     |
| UI → Service → Repository         | Adapter → Port → Domain             |
| Test complicati per logica interna| Test facile sul core isolato        |
| Più fragile a cambiamenti tecnici | Core stabile, adattatori flessibili |

---

## 🛠 Applicazione pratica con Java / Spring Boot

Un possibile schema dei pacchetti potrebbe essere:

- `com.example.domain` → entità, servizi, interfacce (output ports)
- `com.example.application` → casi d'uso / input ports
- `com.example.adapters.in.rest` → controller REST (driving adapters)
- `com.example.adapters.out.jpa` → repository JPA (driven adapters)
- `com.example.adapters.out.gcs` → adapter per GCS, Kafka, ecc.

---

## 📎 Risorse utili

- Alistair Cockburn: [https://alistair.cockburn.us](https://alistair.cockburn.us)
- Hexagonal Architecture on Martin Fowler: [https://martinfowler.com/bliki/HexagonalArchitecture.html](https://martinfowler.com/bliki/HexagonalArchitecture.html)
