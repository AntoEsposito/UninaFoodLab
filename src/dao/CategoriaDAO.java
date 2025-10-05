package dao;

import connection.DatabaseConnection;
import entities.Categoria;
import java.sql.*;

public class CategoriaDAO 
{
	public CategoriaDAO() {} 
	
	
	public Categoria getById(int id) 
	{
		Categoria categoria = null;
		String query = "SELECT * FROM categoria WHERE id_categoria = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) 
			{
				String nome = rs.getString("nome");
				categoria = new Categoria(id, nome);
			}
			
			rs.close();
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return categoria;
	}
}
