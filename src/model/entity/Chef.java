package model.entity;

import java.util.List;
import java.util.ArrayList;

public class Chef 
{
	private int id;
	private String nome;
	private String cognome;
	private String username;
	private String password;
	private List<Corso> corsiGestiti;
	
	
	public Chef(int id, String nome, String cognome, String username, String password, List<Corso> corsiGestiti) 
	{
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.password = password;
		this.corsiGestiti = new ArrayList<>(corsiGestiti);
	}
	public Chef(int id, String nome, String cognome, String username, String password) 
	{
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.password = password;
		this.corsiGestiti = new ArrayList<>();
	}
	
	
	public int getId() {return id;}
	
	public String getNome() {return nome;}
	
	public String getCognome() {return cognome;}
	
	public String getUsername() {return username;}
	
	public String getPassword() {return password;}
	
	public List<Corso> getCorsiGestiti() {return new ArrayList<>(corsiGestiti);}
	
	public void addCorsi (List<Corso> corsi) {corsiGestiti.addAll(corsi);}
}
