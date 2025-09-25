package main;

import dao.*;
import entities.*;
import java.util.List;

public class Start 
{
	public static void main(String[] args) throws Exception 
	{
		// Test ChefDAO
		System.out.println("--- TEST CHEF ---");
		ChefDAO chefDAO = new ChefDAO();
		Chef chef = chefDAO.getChefById(1); // Assumendo che esista uno chef con ID 1
		if (chef != null) {
			System.out.println("Chef: " + chef.getNome() + " " + chef.getCognome());

			// Test CorsoDAO.getCorsoByChef
			System.out.println("\n--- TEST CORSI DELLO CHEF ---");
			CorsoDAO corsoDAO = new CorsoDAO();
			List<Corso> corsi = corsoDAO.getCorsoByChef(chef);
			if (corsi != null && !corsi.isEmpty()) {
				for (Corso corso : corsi) {
					System.out.println("Corso: " + corso.getCategoria().getDescrizione());
					System.out.println("  Data Inizio: " + corso.getDataInizio());
					System.out.println("  Numero Sessioni: " + corso.getNumeroSessioni());
					System.out.println("  Frequenza: " + corso.getFrequenza().getDescrizione());

					// Test SessioneDAO.getSessioniByCorso
					System.out.println("\n  --- TEST SESSIONI DEL CORSO ---");
					SessioneDAO sessioneDAO = new SessioneDAO();
					List<Sessione> sessioni = sessioneDAO.getSessioniByCorso(corso);
					if(sessioni != null && !sessioni.isEmpty()){
						for (Sessione sessione : sessioni) {
							System.out.println("    Sessione Numero: " + sessione.getNumeroSessione());
							System.out.println("      Data: " + sessione.getData());
							System.out.println("      Modalità: " + (sessione.isInPresenza() ? "In Presenza" : "Online"));
							if(!sessione.isInPresenza()){
								System.out.println("      URL Meeting: " + sessione.getUrlMeeting());
							}
						}
					} else {
						System.out.println("    Nessuna sessione trovata per questo corso.");
					}
				}
			} else {
				System.out.println("Nessun corso trovato per questo chef.");
			}
		} else {
			System.out.println("Chef non trovato.");
		}

		// Test RicettaDAO e IngredientiUtilizzatiDAO
		System.out.println("\n--- TEST RICETTA E INGREDIENTI ---");
		RicettaDAO ricettaDAO = new RicettaDAO();
		Ricetta ricetta = ricettaDAO.getRicettaById(1); // Assumendo che esista una ricetta con ID 1
		if (ricetta != null) {
			System.out.println("Ricetta: " + ricetta.getNome());

			IngredientiUtilizzatiDAO ingredientiUtilizzatiDAO = new IngredientiUtilizzatiDAO();
			List<IngredientiUtilizzati> ingredienti = ingredientiUtilizzatiDAO.getIngredientiUtilizzatiByRicetta(ricetta.getId());
			if (ingredienti != null && !ingredienti.isEmpty()) {
				System.out.println("  Ingredienti:");
				for (IngredientiUtilizzati ingrediente : ingredienti) {
					System.out.println("    - " + ingrediente.getIngrediente().getNome() + " (" + ingrediente.getDoseInGrammi() + "g)");
				}
			} else {
				System.out.println("  Nessun ingrediente trovato per questa ricetta.");
			}

			// Test per le sessioni in cui la ricetta viene realizzata
			System.out.println("  Realizzata nelle sessioni:");
			List<Sessione> sessioniRicetta = ricetta.getSessioniInCuiVieneRealizata();
			if(sessioniRicetta != null && !sessioniRicetta.isEmpty()){
				for(Sessione s : sessioniRicetta){
					System.out.println("    - Sessione " + s.getNumeroSessione() + " del corso di " + s.getCorsoDiAppartenenza().getCategoria().getDescrizione() + " tenuto da " + s.getCorsoDiAppartenenza().getChef().getNome());
				}
			} else {
				System.out.println("    Questa ricetta non è realizzata in nessuna sessione.");
			}

		} else {
			System.out.println("Ricetta non trovata.");
		}
	}
}