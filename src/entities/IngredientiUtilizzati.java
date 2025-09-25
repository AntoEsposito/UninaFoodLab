package entities;

// rappresenta l`associazione M:N tra Ricetta e Ingrediente
public class IngredientiUtilizzati 
{
	private Ingrediente ingrediente;
	private Ricetta ricetta;
	private int doseInGrammi;
	
	
	public IngredientiUtilizzati(Ingrediente ingrediente, Ricetta ricetta, int doseInGrammi) 
	{
		this.ingrediente = ingrediente;
		this.ricetta = ricetta;
		this.doseInGrammi = doseInGrammi;
	}
	
	
	public Ingrediente getIngrediente() {return ingrediente;}
	
	public Ricetta getRicetta() {return ricetta;}
	
	public int getDoseInGrammi() {return doseInGrammi;}
}