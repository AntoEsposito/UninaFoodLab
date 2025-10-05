package dao;

import connection.DatabaseConnection;
import entities.Ingrediente;
import java.sql.*;

public class IngredienteDAO
{
	public IngredienteDAO() {} 
	
	
	public Ingrediente getById(int id) 
	{
		Ingrediente ingrediente = null;
		String query = "SELECT * FROM ingrediente WHERE id_ingrediente = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) 
			{
				String nome = rs.getString("nome");
				ingrediente = new Ingrediente(id, nome);
			}
			
			rs.close();
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return ingrediente;
	}
}
