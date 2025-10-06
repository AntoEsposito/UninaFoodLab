package dao;

import connection.DatabaseConnection;
import entities.IngredientiUtilizzati;
import entities.Ingrediente;
import entities.Ricetta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientiUtilizzatiDAO 
{
	private RicettaDAO ricettaDAO;
	
	
	public IngredientiUtilizzatiDAO() 
	{
		ricettaDAO = new RicettaDAO();
	} 
	
	public List<IngredientiUtilizzati> getByIdRicetta(int idRicetta) 
	{
		// Usiamo una JOIN per ottenere tutte le informazioni necessarie in una sola query
		String query = "SELECT iu.dose_in_grammi, i.id_ingrediente, i.nome " +
					   "FROM ingredienti_utilizzati AS iu " +
					   "JOIN ingredienti AS i ON iu.id_ingrediente = i.id_ingrediente " +
					   "WHERE iu.id_ricetta = ?";
		
		List<IngredientiUtilizzati> listaIngredienti = new ArrayList<>();
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			Ricetta ricetta = ricettaDAO.getById(idRicetta);
			if (ricetta == null) 
			{
				// se la ricetta non esiste, ritorna lista vuota
				return listaIngredienti;
			}

			pstmt.setInt(1, idRicetta);
			try (ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next()) {listaIngredienti.add(createIngredientiUtilizzatiFromResultSet(rs, ricetta));}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return listaIngredienti;
	}

	private IngredientiUtilizzati createIngredientiUtilizzatiFromResultSet(ResultSet rs, Ricetta ricetta) throws SQLException
	{
		int doseInGrammi = rs.getInt("iu.dose_in_grammi");
		int idIngrediente = rs.getInt("i.id_ingrediente");
		String nome = rs.getString("i.nome");
		
		Ingrediente ingrediente = new Ingrediente(idIngrediente, nome);
		
		return new IngredientiUtilizzati(ingrediente, ricetta, doseInGrammi);
	}
}
