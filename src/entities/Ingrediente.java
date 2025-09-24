package entities;

public class Ingrediente 
{
	private int id;
	private String nome;
	
	
	public Ingrediente(int id, String nome) 
	{
		this.id = id;
		this.nome = nome;
	}
	
	
	public int getId() {return id;}
	
	public String getNome() {return nome;}
}
