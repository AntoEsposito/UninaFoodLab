package entities;

import java.util.ArrayList;
import java.util.List;

public class Ricetta 
{
	private int id;
	private String nome;
	private List<IngredientiUtilizzati> ingredienti;
	
	
	public Ricetta(int id, String nome, List<IngredientiUtilizzati> ingredienti) 
	{
		this.id = id;
		this.nome = nome;
		this.ingredienti = new ArrayList<>(ingredienti);
	}
	
	
	public int getId() {return id;}
	
	public String getNome() {return nome;}
	
	public List<IngredientiUtilizzati> getIngredienti() {return new ArrayList<>(ingredienti);}
}