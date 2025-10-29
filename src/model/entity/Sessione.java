package model.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Sessione 
{
	private int id;
	private boolean inPresenza;
	private LocalDate data;
	private int numeroSessione;
	private String url;
	private Corso corsoDiAppartenenza;
	private List<Ricetta> ricetteTrattate;
	

	public Sessione(int id, boolean inPresenza, LocalDate data, int numeroSessione, String url, Corso corso, List<Ricetta> ricette) 
	{
		this.id = id;
		this.inPresenza = inPresenza;
		this.data = data;
		this.numeroSessione = numeroSessione;
		this.url = url;
		this.corsoDiAppartenenza = corso;
		this.ricetteTrattate = new ArrayList<>(ricette);
	}
	public Sessione(int id, boolean inPresenza, LocalDate data, int numeroSessione, String urlMeeting, Corso corso) 
	{
		this(id, inPresenza, data, numeroSessione, urlMeeting, corso, new ArrayList<Ricetta>());
	}
	public Sessione(int id, boolean inPresenza, LocalDate data, int numeroSessione, String urlMeeting)
	{
		this(id, inPresenza, data, numeroSessione, urlMeeting, null, new ArrayList<Ricetta>());
	}
	public Sessione(boolean inPresenza, LocalDate data, int numeroSessione, String urlMeeting, Corso corso) 
	{
		this(-1, inPresenza, data, numeroSessione, urlMeeting, corso, new ArrayList<Ricetta>()); // costruttore per inserimento nuova sessione (id = -1)
	}
	

	public int getId() {return id;}
	
	public void setId(int id) {this.id = id;}
	
	public boolean isInPresenza() {return inPresenza;}
	
	public LocalDate getData() {return data;}
	
	public int getNumeroSessione() {return numeroSessione;}	
	
	public String getUrlMeeting() {return url;}
	
	public Corso getCorsoDiAppartenenza() {return corsoDiAppartenenza;}
	
	public void addRicettaTrattata(Ricetta ricetta) {ricetteTrattate.add(ricetta);}
	
	public void removeRicettaTrattata(Ricetta ricetta) {ricetteTrattate.remove(ricetta);}
	
	public List<Ricetta> getRicetteTrattate() {return new ArrayList<>(ricetteTrattate);}
}