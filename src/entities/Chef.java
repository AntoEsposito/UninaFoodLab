package entities;

public class Chef 
{
	private int id;
	private String nome;
	private String cognome;
	private String username;
	private String password;
	
	
	public Chef(int id, String nome, String cognome, String username, String password) 
	{
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.password = password;
	}
	
	
	public int getId() {return id;}
	
	public String getNome() {return nome;}
	
	public String getCognome() {return cognome;}
	
	public String getUsername() {return username;}
	
	public String getPassword() {return password;}
}
