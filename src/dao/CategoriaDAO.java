package dao;

import connection.DatabaseConnection;
import entities.Categoria;
import java.sql.*;

public class CategoriaDAO 
{
	public CategoriaDAO() {} 
	
	
	public Categoria getById(int id) 
	{
		String query = "SELECT * FROM categoria WHERE id_categoria = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery())
			{
				if (rs.next()) {return createCategoriaFromResultSet(rs);}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}

	private Categoria createCategoriaFromResultSet(ResultSet rs) throws SQLException 
	{
		int id = rs.getInt("id_categoria");
		String nome = rs.getString("descrizione");
		
		return new Categoria(id, nome);
	}
}