package model.entity;

public class Categoria 
{
	private int id;
	private String descrizione;
	
	
	public Categoria(int id, String descrizione) 
	{
		this.id = id;
		this.descrizione = descrizione;
	}
	
	
	public int getId() {return id;}
	
	public String getDescrizione() {return descrizione;}
}
