package dao;

import entities.Ingrediente;
import entities.IngredientiUtilizzati;
import entities.Ricetta;

import java.sql.*;
import connection.DatabaseConnection;

import java.util.List;
import java.util.ArrayList;

public class IngredientiUtilizzatiDAO 
{
	public IngredientiUtilizzatiDAO() {} // empty constructor (no attributes to initialize)
	
	
	public List<IngredientiUtilizzati> getIngredientiUtilizzatiByRicetta(int idRicetta)
	{
		List<IngredientiUtilizzati> ingredientiUtilizzati = new ArrayList<IngredientiUtilizzati>();
		String query = "SELECT * FROM ingredienti_utilizzati WHERE id_ricetta = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, idRicetta);
			
			try (ResultSet rs = pstmt.executeQuery()) 
			{
				while (rs.next())
				{
					int doseInGrammi = rs.getInt("dose_grammi");
					
					IngredienteDAO ingredienteDAO = new IngredienteDAO();
					Ingrediente ingrediente = ingredienteDAO.getIngredienteById(rs.getInt("id_ingrediente"));
					
					RicettaDAO ricettaDAO = new RicettaDAO();
					Ricetta ricetta = ricettaDAO.getRicettaById(rs.getInt("id_ricetta"));
					
					IngredientiUtilizzati toAdd = new IngredientiUtilizzati(ingrediente, ricetta, doseInGrammi);
					ingredientiUtilizzati.add(toAdd);
					
				}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return ingredientiUtilizzati; // null if not found
	}
}
