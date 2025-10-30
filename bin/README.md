# 🍳 UninaFoodLab - Sistema di Gestione Corsi di Cucina

## 📋 Descrizione del Progetto

UninaFoodLab è un'applicazione desktop Java per la gestione di corsi di cucina. Il sistema permette agli chef di gestire i propri corsi, creare sessioni di insegnamento, associare ricette e monitorare le partecipazioni degli studenti attraverso un'interfaccia grafica intuitiva.

## 🎯 Funzionalità Principali

- **Autenticazione Chef**: Sistema di login sicuro per gli chef
- **Gestione Corsi**: Creazione e visualizzazione dei corsi di cucina
- **Gestione Sessioni**: Organizzazione di sessioni in presenza e online
- **Associazione Ricette**: Collegamento di ricette alle sessioni in presenza
- **Tracking Partecipazioni**: Monitoraggio delle presenze degli studenti
- **Report e Statistiche**: Visualizzazione di report dettagliati sui corsi

## 🏗️ Architettura

Il progetto segue il pattern architetturale **MVC (Model-View-Controller)**:

### Model
- **Entity**: Chef, Corso, Sessione, Ricetta, Categoria, FrequenzaSessioni
- **DAO**: Gestione della persistenza con PostgreSQL
  - ChefDAO
  - CorsoDAO
  - SessioneDAO
  - RicettaDAO
  - CategoriaDAO
  - FrequenzaSessioniDAO
  - RealizzazioneRicettaDAO

### View
- LoginPage
- MainPage
- NuovoCorsoPage
- VisualizzaCorsiPage
- DettagliCorsoPage
- AssociaRicettePage
- VisualizzaReportPage

### Controller
- Controller centrale che gestisce la logica di business e coordina Model e View

## 🛠️ Tecnologie Utilizzate

- **Java**: Linguaggio di programmazione principale
- **Swing**: Framework per interfaccia grafica
- **PostgreSQL**: Database relazionale
- **JDBC**: Connettività database
- **JFreeChart**: Libreria per la generazione di grafici e report

## 📦 Dipendenze

Le librerie esterne si trovano nella cartella `lib/`:
- `postgresql-42.7.8.jar` - Driver JDBC per PostgreSQL
- `jfreechart-1.5.4.jar` - Libreria per grafici e visualizzazioni

## 🗄️ Database

Il database PostgreSQL `UninaFoodLab` include le seguenti tabelle principali:
- `chef` - Informazioni sugli chef
- `corso` - Dettagli dei corsi
- `sessione` - Sessioni di insegnamento
- `ricetta` - Ricette associate ai corsi
- `categoria` - Categorie dei corsi
- `frequenza_sessioni` - Frequenza delle sessioni (giornaliera, settimanale, ecc.)
- `realizzazione_ricetta` - Associazione ricette-sessioni
- `partecipazione` - Presenze degli studenti

### Trigger e Vincoli
Il database include trigger per garantire l'integrità dei dati:
- Verifica della data di partecipazione
- Controllo del limite di sessioni per corso
- Validazione sessioni in presenza per ricette e partecipazioni

## 🚀 Installazione e Configurazione

### Prerequisiti
- Java JDK 8 o superiore
- PostgreSQL 12 o superiore
- IDE Java (Eclipse, IntelliJ IDEA, ecc.)

### Passi per l'installazione

1. **Clona o scarica il progetto**
   ```bash
   git clone <repository-url>
   cd ProgettoOO
   ```

2. **Configura il Database**
   - Crea un database PostgreSQL chiamato `UninaFoodLab`
   - Esegui lo script SQL contenuto in `database/database.sql`
   ```bash
   psql -U postgres -d UninaFoodLab -f database/database.sql
   ```

3. **Configura la Connessione al Database**
   
   Modifica il file `src/connection/DatabaseConnection.java` con le tue credenziali:
   ```java
   private final String url = "jdbc:postgresql://localhost:5432/UninaFoodLab";
   private final String user = "tuo_username";
   private final String password = "tua_password";
   ```

4. **Compila il Progetto**
   ```bash
   javac -d bin -cp "lib/*" src/**/*.java
   ```

5. **Esegui l'Applicazione**
   ```bash
   java -cp "bin;lib/*" main.Start
   ```

   Su Linux/Mac:
   ```bash
   java -cp "bin:lib/*" main.Start
   ```

## 📂 Struttura del Progetto

```
ProgettoOO/
├── src/                    # Codice sorgente Java
│   ├── connection/         # Gestione connessione database
│   ├── control/            # Controller MVC
│   ├── main/               # Classe principale
│   ├── model/              # Model MVC
│   │   ├── dao/            # Data Access Objects
│   │   └── entity/         # Entità del dominio
│   ├── view/               # View MVC (GUI)
│   │   └── color/          # Costanti per i colori
│   └── images/             # Risorse grafiche
├── lib/                    # Librerie esterne
├── database/               # Script SQL
└── README.md               # Questo file
```

## 💻 Utilizzo

1. **Login**: Avvia l'applicazione e accedi con le credenziali di uno chef registrato nel database
2. **Pagina Principale**: Naviga tra le diverse funzionalità disponibili
3. **Crea Corso**: Inserisci i dettagli di un nuovo corso di cucina
4. **Visualizza Corsi**: Consulta l'elenco dei corsi esistenti
5. **Gestisci Sessioni**: Aggiungi sessioni ai corsi e associa ricette
6. **Report**: Visualizza statistiche e report sui corsi

## 🔐 Sicurezza

- Sistema di autenticazione basato su username e password
- Connessioni database gestite tramite PreparedStatement per prevenire SQL injection
- Pattern Singleton per la gestione della connessione al database

## 👥 Contributi

Progetto sviluppato per il corso di Programmazione Orientata agli Oggetti.

## 📝 Note

- L'applicazione richiede una connessione attiva al database PostgreSQL
- Assicurati che le librerie nella cartella `lib/` siano incluse nel classpath
- Le immagini del logo sono incluse nel pacchetto e vengono caricate dinamicamente

## 🐛 Risoluzione Problemi

### Errore di connessione al database
- Verifica che PostgreSQL sia in esecuzione
- Controlla le credenziali in `DatabaseConnection.java`
- Verifica che il database `UninaFoodLab` esista

### ClassNotFoundException per PostgreSQL
- Assicurati che `postgresql-42.7.8.jar` sia nel classpath
- Verifica che la libreria sia presente nella cartella `lib/`

### Immagini non visualizzate
- Controlla che le immagini siano presenti in `src/images/`
- Verifica che il percorso delle risorse sia corretto