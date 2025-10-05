package dao;

import connection.DatabaseConnection;
import entities.IngredientiUtilizzati;
import entities.Ingrediente;
import entities.Ricetta;
import java.sql.*;

public class IngredientiUtilizzatiDAO 
{
	public IngredientiUtilizzatiDAO() {} 
	
	
	public IngredientiUtilizzati getByIdRicettaAndIdIngrediente(int idRicetta, int idIngrediente) 
	{
		IngredientiUtilizzati ingredientiUtilizzati = null;
		String query = "SELECT * FROM ingredienti_utilizzati WHERE id_ricetta = ? AND id_ingrediente = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, idRicetta);
			pstmt.setInt(2, idIngrediente);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) 
			{
				int doseInGrammi = rs.getInt("dose_in_grammi");
				
				IngredienteDAO ingredienteDAO = new IngredienteDAO();
				Ingrediente ingrediente = ingredienteDAO.getById(idIngrediente);
				
				RicettaDAO ricettaDAO = new RicettaDAO();
				Ricetta ricetta = ricettaDAO.getById(idRicetta);
				
				ingredientiUtilizzati = new IngredientiUtilizzati(ingrediente, ricetta, doseInGrammi);
			}
			
			rs.close();
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return ingredientiUtilizzati;
	}
}
