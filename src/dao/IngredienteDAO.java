package dao;

import entities.Ingrediente;
import java.sql.*;
import connection.DatabaseConnection;

public class IngredienteDAO 
{
	public IngredienteDAO() {} // empty constructor (no attributes to initialize)
	
	
	public Ingrediente getIngredienteById(int id) 
	{
		Ingrediente ingrediente = null;
		String query = "SELECT * FROM ingrediente WHERE id_ingrediente = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) 
			{
				if (rs.next())
				{
					String nome = rs.getString("nome");
					ingrediente = new Ingrediente(id, nome);
				}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return ingrediente; // null if not found
	}
}
