package entities;

public class Ricetta 
{
	private int id;
	private String nome;
	
	
	public Ricetta(int id, String nome) 
	{
		this.id = id;
		this.nome = nome;
	}
	
	
	public int getId() {return id;}
	
	public String getNome() {return nome;}
	
}