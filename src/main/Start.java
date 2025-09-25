package main;

import dao.*;
import entities.*;
import connection.DatabaseConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.Connection;

public class Start 
{
	public static void main(String[] args) throws Exception 
	{
		// Entities smoke test (no DB required)
		System.out.println("-- Entities test --");
		Categoria cat = new Categoria(10, "Dolci");
		FrequenzaSessioni freq = new FrequenzaSessioni(2, "Settimanale");
		Chef chefSample = new Chef(7, "Mario", "Rossi", "mrossi", "pwd");
		Corso corsoSample = new Corso(100, LocalDate.now(), 3, freq, cat, chefSample);
		Ingrediente farina = new Ingrediente(1, "Farina");
		Ingrediente zucchero = new Ingrediente(2, "Zucchero");
		IngredientiUtilizzati iu1 = new IngredientiUtilizzati(farina, 200);
		IngredientiUtilizzati iu2 = new IngredientiUtilizzati(zucchero, 100);
		Ricetta ricetta = new Ricetta(50, "Torta", Arrays.asList(iu1, iu2));
		Sessione sessione = new Sessione(500, true, LocalDate.now(), 1, "http://meet.example/abc", corsoSample, Arrays.asList(ricetta));

		System.out.println("Categoria: " + cat.getId() + " - " + cat.getDescrizione());
		System.out.println("Chef: " + chefSample.getNome() + " " + chefSample.getCognome());
		System.out.println("Corso: id=" + corsoSample.getId() + ", dataInizio=" + corsoSample.getDataInizio() + ", freq=" + corsoSample.getFrequenza().getDescrizione());
		System.out.println("Ricetta: " + ricetta.getNome() + ", ingredienti=" + ricetta.getIngredienti().size());
		System.out.println("Sessione #" + sessione.getNumeroSessione() + ", inPresenza=" + sessione.isInPresenza());

		// DB connectivity check
		System.out.println("\n-- DB connectivity test --");
		boolean dbOk = false;
		try {
			Connection conn = DatabaseConnection.getInstance().getConnection();
			dbOk = (conn != null && !conn.isClosed());
			System.out.println("DB connection OK: " + dbOk);
		} catch (Exception e) {
			System.out.println("DB connection failed: " + e.getMessage());
		} finally {
			try { DatabaseConnection.getInstance().closeConnection(); } catch (Exception ignored) {}
		}

		// DAO tests (only if DB is reachable)
		if (dbOk) {
			System.out.println("\n-- DAO tests --");
			ChefDAO chefDAO = new ChefDAO();
			CategoriaDAO categoriaDAO = new CategoriaDAO();
			FrequenzaSessioniDAO frequenzaDAO = new FrequenzaSessioniDAO();
			CorsoDAO corsoDAO = new CorsoDAO();

			Chef chef = chefDAO.getChefById(1);
			System.out.println(chef != null ? ("Chef[1]: " + chef.getNome() + " " + chef.getCognome()) : "Chef[1]: non trovato");

			Categoria categoria = categoriaDAO.getCategoriaById(1);
			System.out.println(categoria != null ? ("Categoria[1]: " + categoria.getDescrizione()) : "Categoria[1]: non trovata");

			FrequenzaSessioni f = frequenzaDAO.getFrequenzaById(1);
			System.out.println(f != null ? ("Frequenza[1]: " + f.getDescrizione()) : "Frequenza[1]: non trovata");
			
			if (chef != null) {
				List<Corso> corsi = corsoDAO.getCorsoByChef(chef);
				if (corsi != null && !corsi.isEmpty()) {
					System.out.println("Corsi by Chef[1]:");
					for (Corso c : corsi) {
						System.out.println(" - Corso[id=" + c.getId() + ", inizio=" + c.getDataInizio() + ", freq=" + c.getFrequenza().getDescrizione() + ", cat=" + c.getCategoria().getDescrizione() + "]");
					}
				} else {
					System.out.println("Nessun corso trovato per Chef[1]");
				}
			} else {
				System.out.println("Impossibile cercare corsi: Chef[1] non trovato");
			}
	}
}
}