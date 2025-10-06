package entities;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Corso 
{
	private int id;
	private LocalDate dataInizio;
	private int numeroSessioni;
	private FrequenzaSessioni frequenza;
	private Categoria categoria;
	private Chef chef;
	private List<Sessione> sessioni;
	
	
	public Corso(int id, LocalDate dataInizio, int numeroSessioni, FrequenzaSessioni frequenza, Categoria categoria, Chef chef, List<Sessione> sessioni) 
	{
		this.id = id;
		this.dataInizio = dataInizio;
		this.numeroSessioni = numeroSessioni;
		this.frequenza = frequenza;
		this.categoria = categoria;
		this.chef = chef;
		this.sessioni = new ArrayList<>(sessioni);
	}
	public Corso(int id, LocalDate dataInizio, int numeroSessioni, FrequenzaSessioni frequenza, Categoria categoria, Chef chef) 
	{
		this(id, dataInizio, numeroSessioni, frequenza, categoria, chef, new ArrayList<>());
	}

	
	public int getId() {return id;}
	
	public LocalDate getDataInizio() {return dataInizio;}
	
	public int getNumeroSessioni() {return numeroSessioni;}
	
	public FrequenzaSessioni getFrequenza() {return frequenza;}
	
	public Categoria getCategoria() {return categoria;}
	
	public Chef getChef() {return chef;}
	
	public List<Sessione> getSessioni() {return new ArrayList<>(sessioni);}
	
	public void addSessioni (List<Sessione> sessioniToAdd) {sessioni.addAll(sessioniToAdd);}
}
