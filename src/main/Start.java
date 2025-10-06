package main;

import dao.*;
import entities.*;
import java.util.List;

import java.util.ArrayList;
import java.time.LocalDate;

public class Start 
{
	private static List<String> testiPassati = new ArrayList<>();
	private static List<String> testiFalliti = new ArrayList<>();
	
	public static void main(String[] args) throws Exception 
	{
		System.out.println("=================================================");
		System.out.println("         INIZIO TEST DEI METODI DAO");
		System.out.println("=================================================\n");
		
		// Test Entity Classes
		testCategoriaEntity();
		testFrequenzaSessioniEntity();
		testIngredienteEntity();
		testRicettaEntity();
		testChefEntity();
		testCorsoEntity();
		testSessioneEntity();
		testIngredientiUtilizzatiEntity();
		
		// Test CategoriaDAO
		testCategoriaDAO();
		
		// Test FrequenzaSessioniDAO
		testFrequenzaSessioniDAO();
		
		// Test IngredienteDAO
		testIngredienteDAO();
		
		// Test RicettaDAO
		testRicettaDAO();
		
		// Test ChefDAO
		testChefDAO();
		
		// Test CorsoDAO
		testCorsoDAO();
		
		// Test SessioneDAO
		testSessioneDAO();
		
		// Test IngredientiUtilizzatiDAO
		testIngredientiUtilizzatiDAO();
		
		// Test avanzati di integrazione
		testIntegrationAdvanced();
		
		// Stampa riepilogo finale
		stampaRiepilogo();
	}
	
	// ========== TEST ENTITY CLASSES ==========
	
	private static void testCategoriaEntity() {
		System.out.println("\n--- TEST Categoria Entity ---");
		try {
			Categoria cat = new Categoria(1, "Cucina Italiana");
			if (cat.getId() == 1 && "Cucina Italiana".equals(cat.getDescrizione())) {
				testiPassati.add("Categoria Entity - Costruttore e getter funzionano");
				System.out.println("✓ Categoria Entity: OK");
			} else {
				testiFalliti.add("Categoria Entity - Getter non restituiscono valori corretti");
				System.out.println("✗ Categoria Entity: FALLITO");
			}
		} catch (Exception e) {
			testiFalliti.add("Categoria Entity - Eccezione: " + e.getMessage());
			System.out.println("✗ Categoria Entity: ERRORE - " + e.getMessage());
		}
	}
	
	private static void testFrequenzaSessioniEntity() {
		System.out.println("\n--- TEST FrequenzaSessioni Entity ---");
		try {
			FrequenzaSessioni freq = new FrequenzaSessioni(1, "Settimanale");
			if (freq.getId() == 1 && "Settimanale".equals(freq.getDescrizione())) {
				testiPassati.add("FrequenzaSessioni Entity - Costruttore e getter funzionano");
				System.out.println("✓ FrequenzaSessioni Entity: OK");
			} else {
				testiFalliti.add("FrequenzaSessioni Entity - Getter non corretti");
				System.out.println("✗ FrequenzaSessioni Entity: FALLITO");
			}
		} catch (Exception e) {
			testiFalliti.add("FrequenzaSessioni Entity - Eccezione: " + e.getMessage());
			System.out.println("✗ FrequenzaSessioni Entity: ERRORE - " + e.getMessage());
		}
	}
	
	private static void testIngredienteEntity() {
		System.out.println("\n--- TEST Ingrediente Entity ---");
		try {
			Ingrediente ing = new Ingrediente(1, "Pomodoro");
			if (ing.getId() == 1 && "Pomodoro".equals(ing.getNome())) {
				testiPassati.add("Ingrediente Entity - Costruttore e getter funzionano");
				System.out.println("✓ Ingrediente Entity: OK");
			} else {
				testiFalliti.add("Ingrediente Entity - Getter non corretti");
				System.out.println("✗ Ingrediente Entity: FALLITO");
			}
		} catch (Exception e) {
			testiFalliti.add("Ingrediente Entity - Eccezione: " + e.getMessage());
			System.out.println("✗ Ingrediente Entity: ERRORE - " + e.getMessage());
		}
	}
	
	private static void testRicettaEntity() {
		System.out.println("\n--- TEST Ricetta Entity ---");
		try {
			// Test costruttore senza sessioni
			Ricetta ric1 = new Ricetta(1, "Pasta al Pomodoro");
			if (ric1.getId() == 1 && "Pasta al Pomodoro".equals(ric1.getNome()) 
				&& ric1.getSessioniInCuiVieneRealizata() != null 
				&& ric1.getSessioniInCuiVieneRealizata().isEmpty()) {
				testiPassati.add("Ricetta Entity - Costruttore base funziona");
				System.out.println("✓ Ricetta Entity (costruttore base): OK");
			} else {
				testiFalliti.add("Ricetta Entity - Costruttore base non corretto");
				System.out.println("✗ Ricetta Entity (costruttore base): FALLITO");
			}
			
			// Test costruttore con sessioni
			List<Sessione> sessioni = new ArrayList<>();
			Ricetta ric2 = new Ricetta(2, "Risotto", sessioni);
			if (ric2.getSessioniInCuiVieneRealizata() != null) {
				testiPassati.add("Ricetta Entity - Costruttore completo funziona");
				System.out.println("✓ Ricetta Entity (costruttore completo): OK");
			} else {
				testiFalliti.add("Ricetta Entity - Lista sessioni null");
				System.out.println("✗ Ricetta Entity (costruttore completo): FALLITO");
			}
		} catch (Exception e) {
			testiFalliti.add("Ricetta Entity - Eccezione: " + e.getMessage());
			System.out.println("✗ Ricetta Entity: ERRORE - " + e.getMessage());
		}
	}
	
	private static void testChefEntity() {
		System.out.println("\n--- TEST Chef Entity ---");
		try {
			// Test costruttore senza corsi
			Chef chef1 = new Chef(1, "Carlo", "Cracco", "ccracco", "pass123");
			if (chef1.getId() == 1 && "Carlo".equals(chef1.getNome()) 
				&& "Cracco".equals(chef1.getCognome())
				&& "ccracco".equals(chef1.getUsername())
				&& "pass123".equals(chef1.getPassword())
				&& chef1.getCorsiGestiti() != null
				&& chef1.getCorsiGestiti().isEmpty()) {
				testiPassati.add("Chef Entity - Costruttore base funziona");
				System.out.println("✓ Chef Entity (costruttore base): OK");
			} else {
				testiFalliti.add("Chef Entity - Costruttore base non corretto");
				System.out.println("✗ Chef Entity (costruttore base): FALLITO");
			}
			
			// Test costruttore con corsi
			List<Corso> corsi = new ArrayList<>();
			Chef chef2 = new Chef(2, "Antonino", "Cannavacciuolo", "acanna", "pass456", corsi);
			if (chef2.getCorsiGestiti() != null) {
				testiPassati.add("Chef Entity - Costruttore completo funziona");
				System.out.println("✓ Chef Entity (costruttore completo): OK");
			} else {
				testiFalliti.add("Chef Entity - Lista corsi null");
				System.out.println("✗ Chef Entity (costruttore completo): FALLITO");
			}
			
			// Test metodo addCorsi
			List<Corso> nuoviCorsi = new ArrayList<>();
			chef1.addCorsi(nuoviCorsi);
			if (chef1.getCorsiGestiti() != null) {
				testiPassati.add("Chef Entity - Metodo addCorsi funziona");
				System.out.println("✓ Chef Entity (addCorsi): OK");
			} else {
				testiFalliti.add("Chef Entity - addCorsi non funziona");
				System.out.println("✗ Chef Entity (addCorsi): FALLITO");
			}
		} catch (Exception e) {
			testiFalliti.add("Chef Entity - Eccezione: " + e.getMessage());
			System.out.println("✗ Chef Entity: ERRORE - " + e.getMessage());
		}
	}
	
	private static void testCorsoEntity() {
		System.out.println("\n--- TEST Corso Entity ---");
		try {
			LocalDate data = LocalDate.of(2024, 1, 10);
			FrequenzaSessioni freq = new FrequenzaSessioni(1, "Settimanale");
			Categoria cat = new Categoria(1, "Cucina Italiana");
			Chef chef = new Chef(1, "Carlo", "Cracco", "ccracco", "pass");
			
			// Test costruttore senza sessioni
			Corso corso1 = new Corso(1, data, 8, freq, cat, chef);
			if (corso1.getId() == 1 
				&& corso1.getDataInizio().equals(data)
				&& corso1.getNumeroSessioni() == 8
				&& corso1.getFrequenza() != null
				&& corso1.getCategoria() != null
				&& corso1.getChef() != null
				&& corso1.getSessioni() != null
				&& corso1.getSessioni().isEmpty()) {
				testiPassati.add("Corso Entity - Costruttore base funziona");
				System.out.println("✓ Corso Entity (costruttore base): OK");
			} else {
				testiFalliti.add("Corso Entity - Costruttore base non corretto");
				System.out.println("✗ Corso Entity (costruttore base): FALLITO");
			}
			
			// Test costruttore completo
			List<Sessione> sessioni = new ArrayList<>();
			Corso corso2 = new Corso(2, data, 10, freq, cat, chef, sessioni);
			if (corso2.getSessioni() != null) {
				testiPassati.add("Corso Entity - Costruttore completo funziona");
				System.out.println("✓ Corso Entity (costruttore completo): OK");
			} else {
				testiFalliti.add("Corso Entity - Costruttore completo non corretto");
				System.out.println("✗ Corso Entity (costruttore completo): FALLITO");
			}
			
			// Test metodo addSessioni
			List<Sessione> nuoveSessioni = new ArrayList<>();
			corso1.addSessioni(nuoveSessioni);
			if (corso1.getSessioni() != null) {
				testiPassati.add("Corso Entity - Metodo addSessioni funziona");
				System.out.println("✓ Corso Entity (addSessioni): OK");
			} else {
				testiFalliti.add("Corso Entity - addSessioni non funziona");
				System.out.println("✗ Corso Entity (addSessioni): FALLITO");
			}
		} catch (Exception e) {
			testiFalliti.add("Corso Entity - Eccezione: " + e.getMessage());
			System.out.println("✗ Corso Entity: ERRORE - " + e.getMessage());
		}
	}
	
	private static void testSessioneEntity() {
		System.out.println("\n--- TEST Sessione Entity ---");
		try {
			LocalDate data = LocalDate.of(2024, 1, 15);
			
			// Test costruttore minimo
			Sessione sess1 = new Sessione(1, true, data, 1, "http://meeting.com");
			if (sess1.getId() == 1 
				&& sess1.isInPresenza() == true
				&& sess1.getData().equals(data)
				&& sess1.getNumeroSessione() == 1
				&& "http://meeting.com".equals(sess1.getUrlMeeting())
				&& sess1.getRicetteTrattate() != null) {
				testiPassati.add("Sessione Entity - Costruttore minimo funziona");
				System.out.println("✓ Sessione Entity (costruttore minimo): OK");
			} else {
				testiFalliti.add("Sessione Entity - Costruttore minimo non corretto");
				System.out.println("✗ Sessione Entity (costruttore minimo): FALLITO");
			}
			
			// Test costruttore con corso
			Chef chef = new Chef(1, "Test", "Chef", "test", "pass");
			Corso corso = new Corso(1, data, 8, 
				new FrequenzaSessioni(1, "Settimanale"),
				new Categoria(1, "Test"), chef);
			Sessione sess2 = new Sessione(2, false, data, 2, "http://zoom.com", corso);
			if (sess2.getCorsoDiAppartenenza() != null) {
				testiPassati.add("Sessione Entity - Costruttore con corso funziona");
				System.out.println("✓ Sessione Entity (con corso): OK");
			} else {
				testiFalliti.add("Sessione Entity - Corso di appartenenza null");
				System.out.println("✗ Sessione Entity (con corso): FALLITO");
			}
			
			// Test costruttore completo
			List<Ricetta> ricette = new ArrayList<>();
			ricette.add(new Ricetta(1, "Test Ricetta"));
			Sessione sess3 = new Sessione(3, true, data, 3, "http://meet.com", corso, ricette);
			if (sess3.getRicetteTrattate() != null && sess3.getRicetteTrattate().size() == 1) {
				testiPassati.add("Sessione Entity - Costruttore completo funziona");
				System.out.println("✓ Sessione Entity (completo): OK");
			} else {
				testiFalliti.add("Sessione Entity - Costruttore completo non corretto");
				System.out.println("✗ Sessione Entity (completo): FALLITO");
			}
		} catch (Exception e) {
			testiFalliti.add("Sessione Entity - Eccezione: " + e.getMessage());
			System.out.println("✗ Sessione Entity: ERRORE - " + e.getMessage());
		}
	}
	
	private static void testIngredientiUtilizzatiEntity() {
		System.out.println("\n--- TEST IngredientiUtilizzati Entity ---");
		try {
			Ingrediente ing = new Ingrediente(1, "Sale");
			Ricetta ric = new Ricetta(1, "Pasta");
			IngredientiUtilizzati iu = new IngredientiUtilizzati(ing, ric, 10);
			
			if (iu.getIngrediente() != null 
				&& iu.getRicetta() != null
				&& iu.getDoseInGrammi() == 10) {
				testiPassati.add("IngredientiUtilizzati Entity - Costruttore e getter funzionano");
				System.out.println("✓ IngredientiUtilizzati Entity: OK");
			} else {
				testiFalliti.add("IngredientiUtilizzati Entity - Getter non corretti");
				System.out.println("✗ IngredientiUtilizzati Entity: FALLITO");
			}
		} catch (Exception e) {
			testiFalliti.add("IngredientiUtilizzati Entity - Eccezione: " + e.getMessage());
			System.out.println("✗ IngredientiUtilizzati Entity: ERRORE - " + e.getMessage());
		}
	}
	
	// ========== TEST CATEGORIA DAO ==========
	private static void testCategoriaDAO() {
		System.out.println("\n--- TEST CategoriaDAO ---");
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		
		// Test getById con ID esistente
		try {
			Categoria cat = categoriaDAO.getById(1);
			if (cat != null && cat.getDescrizione() != null) {
				testiPassati.add("CategoriaDAO.getById(1) - Categoria trovata: " + cat.getDescrizione());
				System.out.println("✓ getById(1): OK - " + cat.getDescrizione());
			} else {
				testiFalliti.add("CategoriaDAO.getById(1) - Nessuna categoria trovata");
				System.out.println("✗ getById(1): FALLITO - Nessuna categoria trovata");
			}
		} catch (Exception e) {
			testiFalliti.add("CategoriaDAO.getById(1) - Eccezione: " + e.getMessage());
			System.out.println("✗ getById(1): ERRORE - " + e.getMessage());
		}
		
		// Test getById con ID non esistente
		try {
			Categoria cat = categoriaDAO.getById(9999);
			if (cat == null) {
				testiPassati.add("CategoriaDAO.getById(9999) - Correttamente null");
				System.out.println("✓ getById(9999): OK - Nessun risultato (atteso)");
			} else {
				testiFalliti.add("CategoriaDAO.getById(9999) - Dovrebbe essere null");
				System.out.println("✗ getById(9999): FALLITO - Dovrebbe essere null");
			}
		} catch (Exception e) {
			testiFalliti.add("CategoriaDAO.getById(9999) - Eccezione: " + e.getMessage());
			System.out.println("✗ getById(9999): ERRORE - " + e.getMessage());
		}
		
		// Test con ID 0
		try {
			Categoria cat = categoriaDAO.getById(0);
			if (cat == null) {
				testiPassati.add("CategoriaDAO.getById(0) - Gestito correttamente");
				System.out.println("✓ getById(0): OK - Nessun risultato (atteso)");
			} else {
				testiFalliti.add("CategoriaDAO.getById(0) - Comportamento inatteso");
				System.out.println("✗ getById(0): FALLITO");
			}
		} catch (Exception e) {
			testiFalliti.add("CategoriaDAO.getById(0) - Eccezione: " + e.getMessage());
			System.out.println("✗ getById(0): ERRORE - " + e.getMessage());
		}
		
		// Test con ID negativo
		try {
			Categoria cat = categoriaDAO.getById(-1);
			if (cat == null) {
				testiPassati.add("CategoriaDAO.getById(-1) - Gestito correttamente");
				System.out.println("✓ getById(-1): OK - Nessun risultato (atteso)");
			} else {
				testiFalliti.add("CategoriaDAO.getById(-1) - Comportamento inatteso");
				System.out.println("✗ getById(-1): FALLITO");
			}
		} catch (Exception e) {
			testiFalliti.add("CategoriaDAO.getById(-1) - Eccezione: " + e.getMessage());
			System.out.println("✗ getById(-1): ERRORE - " + e.getMessage());
		}
	}
	
	// ========== TEST FREQUENZA SESSIONI DAO ==========
	private static void testFrequenzaSessioniDAO() {
		System.out.println("\n--- TEST FrequenzaSessioniDAO ---");
		FrequenzaSessioniDAO frequenzaDAO = new FrequenzaSessioniDAO();
		
		// Test getById con ID esistente
		try {
			FrequenzaSessioni freq = frequenzaDAO.getById(1);
			if (freq != null && freq.getDescrizione() != null) {
				testiPassati.add("FrequenzaSessioniDAO.getById(1) - Frequenza trovata: " + freq.getDescrizione());
				System.out.println("✓ getById(1): OK - " + freq.getDescrizione());
			} else {
				testiFalliti.add("FrequenzaSessioniDAO.getById(1) - Nessuna frequenza trovata");
				System.out.println("✗ getById(1): FALLITO - Nessuna frequenza trovata");
			}
		} catch (Exception e) {
			testiFalliti.add("FrequenzaSessioniDAO.getById(1) - Eccezione: " + e.getMessage());
			System.out.println("✗ getById(1): ERRORE - " + e.getMessage());
		}
		
		// Test getById con ID non esistente
		try {
			FrequenzaSessioni freq = frequenzaDAO.getById(9999);
			if (freq == null) {
				testiPassati.add("FrequenzaSessioniDAO.getById(9999) - Correttamente null");
				System.out.println("✓ getById(9999): OK - Nessun risultato (atteso)");
			} else {
				testiFalliti.add("FrequenzaSessioniDAO.getById(9999) - Dovrebbe essere null");
				System.out.println("✗ getById(9999): FALLITO - Dovrebbe essere null");
			}
		} catch (Exception e) {
			testiFalliti.add("FrequenzaSessioniDAO.getById(9999) - Eccezione: " + e.getMessage());
			System.out.println("✗ getById(9999): ERRORE - " + e.getMessage());
		}
		
		// Test con ID 0
		try {
			FrequenzaSessioni freq = frequenzaDAO.getById(0);
			if (freq == null) {
				testiPassati.add("FrequenzaSessioniDAO.getById(0) - Gestito correttamente");
				System.out.println("✓ getById(0): OK");
			} else {
				testiFalliti.add("FrequenzaSessioniDAO.getById(0) - Comportamento inatteso");
				System.out.println("✗ getById(0): FALLITO");
			}
		} catch (Exception e) {
			testiFalliti.add("FrequenzaSessioniDAO.getById(0) - Eccezione: " + e.getMessage());
			System.out.println("✗ getById(0): ERRORE - " + e.getMessage());
		}
	}
	
	// ========== TEST INGREDIENTE DAO ==========
	private static void testIngredienteDAO() {
		System.out.println("\n--- TEST IngredienteDAO ---");
		IngredienteDAO ingredienteDAO = new IngredienteDAO();
		
		// Test getById con ID esistente
		try {
			Ingrediente ing = ingredienteDAO.getById(1);
			if (ing != null && ing.getNome() != null) {
				testiPassati.add("IngredienteDAO.getById(1) - Ingrediente trovato: " + ing.getNome());
				System.out.println("✓ getById(1): OK - " + ing.getNome());
			} else {
				testiFalliti.add("IngredienteDAO.getById(1) - Nessun ingrediente trovato");
				System.out.println("✗ getById(1): FALLITO - Nessun ingrediente trovato");
			}
		} catch (Exception e) {
			testiFalliti.add("IngredienteDAO.getById(1) - Eccezione: " + e.getMessage());
			System.out.println("✗ getById(1): ERRORE - " + e.getMessage());
		}
		
		// Test getById con ID non esistente
		try {
			Ingrediente ing = ingredienteDAO.getById(9999);
			if (ing == null) {
				testiPassati.add("IngredienteDAO.getById(9999) - Correttamente null");
				System.out.println("✓ getById(9999): OK - Nessun risultato (atteso)");
			} else {
				testiFalliti.add("IngredienteDAO.getById(9999) - Dovrebbe essere null");
				System.out.println("✗ getById(9999): FALLITO - Dovrebbe essere null");
			}
		} catch (Exception e) {
			testiFalliti.add("IngredienteDAO.getById(9999) - Eccezione: " + e.getMessage());
			System.out.println("✗ getById(9999): ERRORE - " + e.getMessage());
		}
		
		// Test multipli ID
		try {
			Ingrediente ing2 = ingredienteDAO.getById(2);
			Ingrediente ing3 = ingredienteDAO.getById(3);
			int count = 0;
			if (ing2 != null) count++;
			if (ing3 != null) count++;
			testiPassati.add("IngredienteDAO - Test multipli ID: " + count + " ingredienti trovati");
			System.out.println("✓ Test multipli ID: OK - " + count + " ingredienti trovati");
		} catch (Exception e) {
			testiFalliti.add("IngredienteDAO - Test multipli ID: " + e.getMessage());
			System.out.println("✗ Test multipli ID: ERRORE - " + e.getMessage());
		}
	}
	
	// ========== TEST RICETTA DAO ==========
	private static void testRicettaDAO() {
		System.out.println("\n--- TEST RicettaDAO ---");
		RicettaDAO ricettaDAO = new RicettaDAO();
		
		// Test getById con ID esistente
		try {
			Ricetta ric = ricettaDAO.getById(1);
			if (ric != null && ric.getNome() != null) {
				testiPassati.add("RicettaDAO.getById(1) - Ricetta trovata: " + ric.getNome());
				System.out.println("✓ getById(1): OK - " + ric.getNome());
			} else {
				testiFalliti.add("RicettaDAO.getById(1) - Nessuna ricetta trovata");
				System.out.println("✗ getById(1): FALLITO - Nessuna ricetta trovata");
			}
		} catch (Exception e) {
			testiFalliti.add("RicettaDAO.getById(1) - Eccezione: " + e.getMessage());
			System.out.println("✗ getById(1): ERRORE - " + e.getMessage());
		}
		
		// Test getById con ID non esistente
		try {
			Ricetta ric = ricettaDAO.getById(9999);
			if (ric == null) {
				testiPassati.add("RicettaDAO.getById(9999) - Correttamente null");
				System.out.println("✓ getById(9999): OK - Nessun risultato (atteso)");
			} else {
				testiFalliti.add("RicettaDAO.getById(9999) - Dovrebbe essere null");
				System.out.println("✗ getById(9999): FALLITO - Dovrebbe essere null");
			}
		} catch (Exception e) {
			testiFalliti.add("RicettaDAO.getById(9999) - Eccezione: " + e.getMessage());
			System.out.println("✗ getById(9999): ERRORE - " + e.getMessage());
		}
		
		// Test multipli ID
		try {
			Ricetta ric2 = ricettaDAO.getById(2);
			Ricetta ric3 = ricettaDAO.getById(3);
			int count = 0;
			if (ric2 != null) count++;
			if (ric3 != null) count++;
			testiPassati.add("RicettaDAO - Test multipli ID: " + count + " ricette trovate");
			System.out.println("✓ Test multipli ID: OK - " + count + " ricette trovate");
		} catch (Exception e) {
			testiFalliti.add("RicettaDAO - Test multipli ID: " + e.getMessage());
			System.out.println("✗ Test multipli ID: ERRORE - " + e.getMessage());
		}
		
		// Test getBySessione (verrà testato più avanti con una sessione reale)
	}
	
	// ========== TEST CHEF DAO ==========
	private static void testChefDAO() {
		System.out.println("\n--- TEST ChefDAO ---");
		ChefDAO chefDAO = new ChefDAO();
		
		// Test getByUsernameAndPassword con credenziali esistenti
		try {
			Chef chef = chefDAO.getByUsernameAndPassword("chefCannavacciuolo", "chefpasscannavacciuolo");
			if (chef != null && chef.getNome() != null) {
				testiPassati.add("ChefDAO.getByUsernameAndPassword - Chef trovato: " + chef.getNome() + " " + chef.getCognome());
				System.out.println("✓ getByUsernameAndPassword: OK - " + chef.getNome() + " " + chef.getCognome());
				System.out.println("  Username: " + chef.getUsername());
				System.out.println("  ID: " + chef.getId());
				if (chef.getCorsiGestiti() != null) {
					System.out.println("  Corsi associati: " + chef.getCorsiGestiti().size());
					testiPassati.add("ChefDAO - Lista corsi caricata correttamente");
				}
			} else {
				testiFalliti.add("ChefDAO.getByUsernameAndPassword - Nessuno chef trovato (prova con credenziali diverse)");
				System.out.println("✗ getByUsernameAndPassword: FALLITO - Nessuno chef trovato");
			}
		} catch (Exception e) {
			testiFalliti.add("ChefDAO.getByUsernameAndPassword - Eccezione: " + e.getMessage());
			System.out.println("✗ getByUsernameAndPassword: ERRORE - " + e.getMessage());
		}
		
		// Test getByUsernameAndPassword con credenziali non esistenti
		try {
			Chef chef = chefDAO.getByUsernameAndPassword("utente.inesistente", "passwordsbagliata");
			if (chef == null) {
				testiPassati.add("ChefDAO.getByUsernameAndPassword (credenziali errate) - Correttamente null");
				System.out.println("✓ getByUsernameAndPassword (errate): OK - Nessun risultato (atteso)");
			} else {
				testiFalliti.add("ChefDAO.getByUsernameAndPassword (credenziali errate) - Dovrebbe essere null");
				System.out.println("✗ getByUsernameAndPassword (errate): FALLITO - Dovrebbe essere null");
			}
		} catch (Exception e) {
			testiFalliti.add("ChefDAO.getByUsernameAndPassword (credenziali errate) - Eccezione: " + e.getMessage());
			System.out.println("✗ getByUsernameAndPassword (errate): ERRORE - " + e.getMessage());
		}
		
		// Test con username vuoto
		try {
			Chef chef = chefDAO.getByUsernameAndPassword("", "");
			if (chef == null) {
				testiPassati.add("ChefDAO - Username vuoto gestito correttamente");
				System.out.println("✓ getByUsernameAndPassword (vuoto): OK");
			} else {
				testiFalliti.add("ChefDAO - Username vuoto non gestito");
				System.out.println("✗ getByUsernameAndPassword (vuoto): FALLITO");
			}
		} catch (Exception e) {
			testiFalliti.add("ChefDAO - Username vuoto: " + e.getMessage());
			System.out.println("✗ getByUsernameAndPassword (vuoto): ERRORE - " + e.getMessage());
		}
		
		// Test con username corretto ma password errata
		try {
			Chef chef = chefDAO.getByUsernameAndPassword("chefCannavacciuolo", "passworderrata");
			if (chef == null) {
				testiPassati.add("ChefDAO - Password errata gestita correttamente");
				System.out.println("✓ getByUsernameAndPassword (password errata): OK");
			} else {
				testiFalliti.add("ChefDAO - Password errata non gestita");
				System.out.println("✗ getByUsernameAndPassword (password errata): FALLITO");
			}
		} catch (Exception e) {
			testiFalliti.add("ChefDAO - Password errata: " + e.getMessage());
			System.out.println("✗ getByUsernameAndPassword (password errata): ERRORE - " + e.getMessage());
		}
	}
	
	// ========== TEST CORSO DAO ==========
	private static void testCorsoDAO() {
		System.out.println("\n--- TEST CorsoDAO ---");
		CorsoDAO corsoDAO = new CorsoDAO();
		ChefDAO chefDAO = new ChefDAO();
		
		// Prima otteniamo uno chef per testare getByChef
		try {
			Chef chef = chefDAO.getByUsernameAndPassword("chefCannavacciuolo", "chefpasscannavacciuolo");
			if (chef != null) {
				List<Corso> corsi = corsoDAO.getByChef(chef);
				if (corsi != null) {
					testiPassati.add("CorsoDAO.getByChef - Trovati " + corsi.size() + " corsi");
					System.out.println("✓ getByChef: OK - Trovati " + corsi.size() + " corsi");
					
					// Test dettagliato di ogni corso
					for (Corso c : corsi) {
						System.out.println("  - Corso ID: " + c.getId());
						System.out.println("    Data inizio: " + c.getDataInizio());
						System.out.println("    Numero sessioni: " + c.getNumeroSessioni());
						
						if (c.getCategoria() != null) {
							System.out.println("    Categoria: " + c.getCategoria().getDescrizione());
							testiPassati.add("CorsoDAO - Categoria caricata per corso " + c.getId());
						} else {
							testiFalliti.add("CorsoDAO - Categoria null per corso " + c.getId());
						}
						
						if (c.getFrequenza() != null) {
							System.out.println("    Frequenza: " + c.getFrequenza().getDescrizione());
							testiPassati.add("CorsoDAO - Frequenza caricata per corso " + c.getId());
						} else {
							testiFalliti.add("CorsoDAO - Frequenza null per corso " + c.getId());
						}
						
						if (c.getChef() != null) {
							testiPassati.add("CorsoDAO - Chef associato al corso " + c.getId());
						}
						
						if (c.getSessioni() != null) {
							System.out.println("    Sessioni: " + c.getSessioni().size());
							testiPassati.add("CorsoDAO - Sessioni caricate per corso " + c.getId());
						}
					}
				} else {
					testiFalliti.add("CorsoDAO.getByChef - Lista corsi null");
					System.out.println("✗ getByChef: FALLITO - Lista null");
				}
			} else {
				testiFalliti.add("CorsoDAO.getByChef - Impossibile testare: chef non trovato");
				System.out.println("⚠ getByChef: SKIP - Chef non trovato per il test");
			}
		} catch (Exception e) {
			testiFalliti.add("CorsoDAO.getByChef - Eccezione: " + e.getMessage());
			System.out.println("✗ getByChef: ERRORE - " + e.getMessage());
			e.printStackTrace();
		}
		
		// Test con chef senza corsi (ID inesistente)
		try {
			Chef chefFake = new Chef(99999, "Fake", "Chef", "fake", "fake");
			List<Corso> corsi = corsoDAO.getByChef(chefFake);
			if (corsi != null && corsi.isEmpty()) {
				testiPassati.add("CorsoDAO.getByChef - Lista vuota per chef senza corsi");
				System.out.println("✓ getByChef (chef senza corsi): OK - Lista vuota");
			} else if (corsi == null) {
				testiFalliti.add("CorsoDAO.getByChef - Lista null invece di vuota");
				System.out.println("✗ getByChef (chef senza corsi): FALLITO - Lista null");
			}
		} catch (Exception e) {
			testiFalliti.add("CorsoDAO.getByChef (chef inesistente): " + e.getMessage());
			System.out.println("✗ getByChef (chef inesistente): ERRORE - " + e.getMessage());
		}
	}
	
	// ========== TEST SESSIONE DAO ==========
	private static void testSessioneDAO() {
		System.out.println("\n--- TEST SessioneDAO ---");
		SessioneDAO sessioneDAO = new SessioneDAO();
		ChefDAO chefDAO = new ChefDAO();
		
		// Otteniamo un corso per testare getByCorso
		try {
			Chef chef = chefDAO.getByUsernameAndPassword("chefCannavacciuolo", "chefpasscannavacciuolo");
			if (chef != null && chef.getCorsiGestiti() != null && !chef.getCorsiGestiti().isEmpty()) {
				Corso corso = chef.getCorsiGestiti().get(0);
				List<Sessione> sessioni = sessioneDAO.getByCorso(corso);
				if (sessioni != null) {
					testiPassati.add("SessioneDAO.getByCorso - Trovate " + sessioni.size() + " sessioni");
					System.out.println("✓ getByCorso: OK - Trovate " + sessioni.size() + " sessioni");
					
					// Test dettagliato di ogni sessione
					for (Sessione s : sessioni) {
						System.out.println("  - Sessione #" + s.getNumeroSessione());
						System.out.println("    ID: " + s.getId());
						System.out.println("    Data: " + s.getData());
						System.out.println("    In presenza: " + s.isInPresenza());
						System.out.println("    URL: " + (s.getUrlMeeting() != null ? s.getUrlMeeting() : "N/A"));
						
						if (s.getCorsoDiAppartenenza() != null) {
							testiPassati.add("SessioneDAO - Corso associato alla sessione " + s.getId());
						}
						
						if (s.getRicetteTrattate() != null) {
							System.out.println("    Ricette: " + s.getRicetteTrattate().size());
							testiPassati.add("SessioneDAO - Ricette caricate per sessione " + s.getId());
							
							for (Ricetta r : s.getRicetteTrattate()) {
								System.out.println("      * " + r.getNome());
							}
						}
					}
					
					// Test verifica presenza/assenza
					long inPresenza = sessioni.stream().filter(Sessione::isInPresenza).count();
					long online = sessioni.size() - inPresenza;
					System.out.println("  Statistiche: " + inPresenza + " in presenza, " + online + " online");
					testiPassati.add("SessioneDAO - Test statistiche sessioni completato");
					
				} else {
					testiFalliti.add("SessioneDAO.getByCorso - Lista sessioni null");
					System.out.println("✗ getByCorso: FALLITO - Lista null");
				}
			} else {
				testiFalliti.add("SessioneDAO.getByCorso - Impossibile testare: nessun corso disponibile");
				System.out.println("⚠ getByCorso: SKIP - Nessun corso disponibile per il test");
			}
		} catch (Exception e) {
			testiFalliti.add("SessioneDAO.getByCorso - Eccezione: " + e.getMessage());
			System.out.println("✗ getByCorso: ERRORE - " + e.getMessage());
			e.printStackTrace();
		}
		
		// Test con corso senza sessioni
		try {
			Chef chefFake = new Chef(1, "Test", "Chef", "test", "test");
			Corso corsoFake = new Corso(99999, LocalDate.now(), 0,
				new FrequenzaSessioni(1, "Test"),
				new Categoria(1, "Test"), chefFake);
			List<Sessione> sessioni = sessioneDAO.getByCorso(corsoFake);
			if (sessioni != null && sessioni.isEmpty()) {
				testiPassati.add("SessioneDAO - Lista vuota per corso senza sessioni");
				System.out.println("✓ getByCorso (corso senza sessioni): OK");
			}
		} catch (Exception e) {
			testiFalliti.add("SessioneDAO (corso senza sessioni): " + e.getMessage());
			System.out.println("✗ getByCorso (corso senza sessioni): ERRORE");
		}
	}
	
	// ========== TEST INGREDIENTI UTILIZZATI DAO ==========
	private static void testIngredientiUtilizzatiDAO() {
		System.out.println("\n--- TEST IngredientiUtilizzatiDAO ---");
		IngredientiUtilizzatiDAO ingredientiUtilizzatiDAO = new IngredientiUtilizzatiDAO();
		
		// Test getByIdRicetta con ID esistente
		try {
			List<IngredientiUtilizzati> ingredienti = ingredientiUtilizzatiDAO.getByIdRicetta(1);
			if (ingredienti != null) {
				testiPassati.add("IngredientiUtilizzatiDAO.getByIdRicetta(1) - Trovati " + ingredienti.size() + " ingredienti");
				System.out.println("✓ getByIdRicetta(1): OK - Trovati " + ingredienti.size() + " ingredienti");
				
				int totaleDose = 0;
				for (IngredientiUtilizzati iu : ingredienti) {
					System.out.println("  - " + iu.getIngrediente().getNome() + ": " + iu.getDoseInGrammi() + "g");
					totaleDose += iu.getDoseInGrammi();
					
					if (iu.getRicetta() != null) {
						testiPassati.add("IngredientiUtilizzatiDAO - Ricetta associata all'ingrediente");
					}
					if (iu.getIngrediente() != null && iu.getIngrediente().getNome() != null) {
						testiPassati.add("IngredientiUtilizzatiDAO - Ingrediente " + iu.getIngrediente().getNome() + " caricato");
					}
				}
				System.out.println("  Dose totale: " + totaleDose + "g");
				testiPassati.add("IngredientiUtilizzatiDAO - Calcolo dose totale: " + totaleDose + "g");
			} else {
				testiFalliti.add("IngredientiUtilizzatiDAO.getByIdRicetta(1) - Lista null");
				System.out.println("✗ getByIdRicetta(1): FALLITO - Lista null");
			}
		} catch (Exception e) {
			testiFalliti.add("IngredientiUtilizzatiDAO.getByIdRicetta(1) - Eccezione: " + e.getMessage());
			System.out.println("✗ getByIdRicetta(1): ERRORE - " + e.getMessage());
			e.printStackTrace();
		}
		
		// Test getByIdRicetta con ID non esistente
		try {
			List<IngredientiUtilizzati> ingredienti = ingredientiUtilizzatiDAO.getByIdRicetta(9999);
			if (ingredienti != null && ingredienti.isEmpty()) {
				testiPassati.add("IngredientiUtilizzatiDAO.getByIdRicetta(9999) - Lista vuota (atteso)");
				System.out.println("✓ getByIdRicetta(9999): OK - Lista vuota (atteso)");
			} else if (ingredienti == null) {
				testiFalliti.add("IngredientiUtilizzatiDAO.getByIdRicetta(9999) - Lista null invece di vuota");
				System.out.println("✗ getByIdRicetta(9999): FALLITO - Lista null");
			} else {
				testiFalliti.add("IngredientiUtilizzatiDAO.getByIdRicetta(9999) - Lista non vuota");
				System.out.println("✗ getByIdRicetta(9999): FALLITO - Lista non vuota");
			}
		} catch (Exception e) {
			testiFalliti.add("IngredientiUtilizzatiDAO.getByIdRicetta(9999) - Eccezione: " + e.getMessage());
			System.out.println("✗ getByIdRicetta(9999): ERRORE - " + e.getMessage());
		}
		
		// Test con altre ricette
		try {
			List<IngredientiUtilizzati> ing2 = ingredientiUtilizzatiDAO.getByIdRicetta(2);
			List<IngredientiUtilizzati> ing3 = ingredientiUtilizzatiDAO.getByIdRicetta(3);
			int count = 0;
			if (ing2 != null) count += ing2.size();
			if (ing3 != null) count += ing3.size();
			testiPassati.add("IngredientiUtilizzatiDAO - Test multipli: " + count + " ingredienti totali");
			System.out.println("✓ Test multipli ID ricette: " + count + " ingredienti");
		} catch (Exception e) {
			testiFalliti.add("IngredientiUtilizzatiDAO - Test multipli: " + e.getMessage());
			System.out.println("✗ Test multipli: ERRORE");
		}
		
		// Test con ID 0
		try {
			List<IngredientiUtilizzati> ing = ingredientiUtilizzatiDAO.getByIdRicetta(0);
			if (ing != null && ing.isEmpty()) {
				testiPassati.add("IngredientiUtilizzatiDAO - ID 0 gestito correttamente");
				System.out.println("✓ getByIdRicetta(0): OK");
			}
		} catch (Exception e) {
			testiFalliti.add("IngredientiUtilizzatiDAO - ID 0: " + e.getMessage());
			System.out.println("✗ getByIdRicetta(0): ERRORE");
		}
	}
	
	// ========== TEST INTEGRAZIONE AVANZATI ==========
	private static void testIntegrationAdvanced() {
		System.out.println("\n--- TEST INTEGRAZIONE AVANZATI ---");
		
		// Test catena completa: Chef -> Corsi -> Sessioni -> Ricette -> Ingredienti
		try {
			ChefDAO chefDAO = new ChefDAO();
			RicettaDAO ricettaDAO = new RicettaDAO();
			IngredientiUtilizzatiDAO ingredientiDAO = new IngredientiUtilizzatiDAO();
			
			Chef chef = chefDAO.getByUsernameAndPassword("chefCannavacciuolo", "chefpasscannavacciuolo");
			if (chef != null && chef.getCorsiGestiti() != null && !chef.getCorsiGestiti().isEmpty()) {
				System.out.println("✓ Chef caricato con successo");
				
				for (Corso corso : chef.getCorsiGestiti()) {
					System.out.println("  Corso ID " + corso.getId() + ":");
					
					if (corso.getSessioni() != null) {
						for (Sessione sessione : corso.getSessioni()) {
							System.out.println("    Sessione #" + sessione.getNumeroSessione() + ":");
							
							if (sessione.getRicetteTrattate() != null) {
								for (Ricetta ricetta : sessione.getRicetteTrattate()) {
									System.out.println("      Ricetta: " + ricetta.getNome());
									
									List<IngredientiUtilizzati> ingredienti = ingredientiDAO.getByIdRicetta(ricetta.getId());
									if (ingredienti != null && !ingredienti.isEmpty()) {
										System.out.println("        Ingredienti: " + ingredienti.size());
										testiPassati.add("Test Integrazione - Catena completa verificata per ricetta " + ricetta.getId());
									}
								}
							}
						}
					}
				}
				testiPassati.add("Test Integrazione - Catena completa Chef->Corsi->Sessioni->Ricette->Ingredienti");
				System.out.println("✓ Test integrazione catena completa: OK");
			}
		} catch (Exception e) {
			testiFalliti.add("Test Integrazione - Catena completa: " + e.getMessage());
			System.out.println("✗ Test integrazione: ERRORE - " + e.getMessage());
		}
		
		// Test getBySessione di RicettaDAO
		try {
			ChefDAO chefDAO = new ChefDAO();
			RicettaDAO ricettaDAO = new RicettaDAO();
			
			Chef chef = chefDAO.getByUsernameAndPassword("chefCannavacciuolo", "chefpasscannavacciuolo");
			if (chef != null && chef.getCorsiGestiti() != null && !chef.getCorsiGestiti().isEmpty()) {
				Corso corso = chef.getCorsiGestiti().get(0);
				if (corso.getSessioni() != null && !corso.getSessioni().isEmpty()) {
					Sessione sessione = corso.getSessioni().get(0);
					
					List<Ricetta> ricette = ricettaDAO.getBySessione(sessione);
					if (ricette != null) {
						testiPassati.add("RicettaDAO.getBySessione - Trovate " + ricette.size() + " ricette");
						System.out.println("✓ RicettaDAO.getBySessione: OK - " + ricette.size() + " ricette");
						
						for (Ricetta r : ricette) {
							System.out.println("  - " + r.getNome() + " (ID: " + r.getId() + ")");
						}
					} else {
						testiFalliti.add("RicettaDAO.getBySessione - Lista null");
						System.out.println("✗ RicettaDAO.getBySessione: FALLITO");
					}
				}
			}
		} catch (Exception e) {
			testiFalliti.add("RicettaDAO.getBySessione: " + e.getMessage());
			System.out.println("✗ RicettaDAO.getBySessione: ERRORE - " + e.getMessage());
		}
		
		// Test getBySessione con sessione senza ricette
		try {
			RicettaDAO ricettaDAO = new RicettaDAO();
			Sessione sessioneFake = new Sessione(99999, true, LocalDate.now(), 1, "http://test.com");
			List<Ricetta> ricette = ricettaDAO.getBySessione(sessioneFake);
			
			if (ricette != null && ricette.isEmpty()) {
				testiPassati.add("RicettaDAO.getBySessione - Lista vuota per sessione senza ricette");
				System.out.println("✓ RicettaDAO.getBySessione (sessione senza ricette): OK");
			}
		} catch (Exception e) {
			testiFalliti.add("RicettaDAO.getBySessione (sessione senza ricette): " + e.getMessage());
			System.out.println("✗ RicettaDAO.getBySessione (sessione senza ricette): ERRORE");
		}
		
		// Test verifica coerenza dati
		try {
			ChefDAO chefDAO = new ChefDAO();
			Chef chef = chefDAO.getByUsernameAndPassword("chefCannavacciuolo", "chefpasscannavacciuolo");
			
			if (chef != null && chef.getCorsiGestiti() != null) {
				for (Corso corso : chef.getCorsiGestiti()) {
					// Verifica che il numero di sessioni corrisponda
					if (corso.getSessioni() != null) {
						int numSessioniDichiarate = corso.getNumeroSessioni();
						int numSessioniCaricate = corso.getSessioni().size();
						
						if (numSessioniDichiarate == numSessioniCaricate) {
							testiPassati.add("Test Coerenza - Corso " + corso.getId() + ": numero sessioni coerente");
							System.out.println("✓ Coerenza corso " + corso.getId() + ": " + numSessioniCaricate + " sessioni");
						} else {
							System.out.println("⚠ Corso " + corso.getId() + ": dichiarate " + numSessioniDichiarate + ", caricate " + numSessioniCaricate);
						}
					}
				}
			}
		} catch (Exception e) {
			testiFalliti.add("Test Coerenza dati: " + e.getMessage());
			System.out.println("✗ Test coerenza: ERRORE");
		}
	}
	
	// ========== STAMPA RIEPILOGO ==========
	private static void stampaRiepilogo() {
		System.out.println("\n\n=================================================");
		System.out.println("              RIEPILOGO DEI TEST");
		System.out.println("=================================================");
		
		int totaleTest = testiPassati.size() + testiFalliti.size();
		int percentualeSuccesso = totaleTest > 0 ? (testiPassati.size() * 100 / totaleTest) : 0;
		
		System.out.println("\nTOTALE TEST ESEGUITI: " + totaleTest);
		System.out.println("TEST SUPERATI: " + testiPassati.size() + " ✓");
		System.out.println("TEST FALLITI: " + testiFalliti.size() + " ✗");
		System.out.println("PERCENTUALE SUCCESSO: " + percentualeSuccesso + "%");
		
		if (!testiPassati.isEmpty()) {
			System.out.println("\n--- TEST SUPERATI ---");
			for (String test : testiPassati) {
				System.out.println("✓ " + test);
			}
		}
		
		if (!testiFalliti.isEmpty()) {
			System.out.println("\n--- TEST FALLITI ---");
			for (String test : testiFalliti) {
				System.out.println("✗ " + test);
			}
		}
		
		System.out.println("\n=================================================");
		if (testiFalliti.isEmpty()) {
			System.out.println("    🎉 TUTTI I TEST SONO STATI SUPERATI! 🎉");
		} else {
			System.out.println("    ⚠ ALCUNI TEST SONO FALLITI ⚠");
		}
		System.out.println("=================================================\n");
	}
}
