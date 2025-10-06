package dao;

import connection.DatabaseConnection;
import entities.Ingrediente;
import java.sql.*;

public class IngredienteDAO
{
	public IngredienteDAO() {} 
	
	
	public Ingrediente getById(int id) 
	{
		String query = "SELECT * FROM ingrediente WHERE id_ingrediente = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery())
			{
				if (rs.next()) {return createIngredienteFromResultSet(rs);}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	private Ingrediente createIngredienteFromResultSet(ResultSet rs) throws SQLException 
	{	
		int id = rs.getInt("id_ingrediente");
		String nome = rs.getString("nome");
		
		return new Ingrediente(id, nome);
	}
}
