package entities;

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
	

	public int getId() {return id;}
	
	public boolean isInPresenza() {return inPresenza;}
	
	public LocalDate getData() {return data;}
	
	public int getNumeroSessione() {return numeroSessione;}	
	
	public String getUrlMeeting() {return url;}
	
	public Corso getCorsoDiAppartenenza() {return corsoDiAppartenenza;}
	
	public List<Ricetta> getRicetteTrattate() {return new ArrayList<>(ricetteTrattate);}
}