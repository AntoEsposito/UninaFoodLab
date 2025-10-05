package dao;

import connection.DatabaseConnection;
import entities.Ricetta;
import java.sql.*;

public class RicettaDAO
{
	public RicettaDAO() {} 
	
	
	public Ricetta getById(int id) 
	{
		Ricetta ricetta = null;
		String query = "SELECT * FROM ricetta WHERE id_ricetta = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) 
			{
				String nome = rs.getString("nome");
				ricetta = new Ricetta(id, nome);
			}
			
			rs.close();
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return ricetta;
	}
}
