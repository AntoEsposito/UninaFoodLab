package entities;

import java.util.List;
import java.util.ArrayList;

public class Ricetta 
{
	private int id;
	private String nome;
	private List<Sessione> sessioniInCuiVieneRealizzata;
	
	
	public Ricetta(int id, String nome, List<Sessione> sessioni) 
	{
		this.id = id;
		this.nome = nome;
		this.sessioniInCuiVieneRealizzata = new ArrayList<>(sessioni);
	}
	public Ricetta(int id, String nome) 
	{
		this(id, nome, new ArrayList<Sessione>());
	}
	
	
	public int getId() {return id;}
	
	public String getNome() {return nome;}
	
	public List<Sessione> getSessioniInCuiVieneRealizatta() {return new ArrayList<>(sessioniInCuiVieneRealizzata);}
}