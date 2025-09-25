package dao;

import connection.DatabaseConnection;
import entities.Categoria;
import java.sql.*;

public class CategoriaDAO 
{
	public CategoriaDAO() {} // empty constructor (no attributes to initialize)
	
	
	public Categoria getCategoriaById(int id) 
	{
		Categoria categoria = null;
		String query = "SELECT * FROM categoria WHERE id_categoria = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) 
			{
				if (rs.next())
				{
					String descrizione = rs.getString("descrizione");
					categoria = new Categoria(id, descrizione);
				}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return categoria;
	}
}
