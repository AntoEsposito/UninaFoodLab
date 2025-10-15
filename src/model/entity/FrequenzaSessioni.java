package model.entity;

public class FrequenzaSessioni 
{
	private int id;
	private String descrizione;
	
	
	public FrequenzaSessioni(int id, String descrizione) 
	{
		this.id = id;
		this.descrizione = descrizione;
	}
	
	
	public int getId() {return id;}
	
	public String getDescrizione() {return descrizione;}
}
