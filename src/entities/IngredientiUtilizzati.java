package entities;

// rappresenta l`associazione M:N tra Ricetta e Ingrediente
public class IngredientiUtilizzati 
{
	private Ingrediente ingrediente;
	private int doseInGrammi;
	
	
	public IngredientiUtilizzati(Ingrediente ingrediente, int doseInGrammi) 
	{
		this.ingrediente = ingrediente;
		this.doseInGrammi = doseInGrammi;
	}
	
	
	public Ingrediente getIngrediente() {return ingrediente;}
	
	public int getDoseInGrammi() {return doseInGrammi;}
}