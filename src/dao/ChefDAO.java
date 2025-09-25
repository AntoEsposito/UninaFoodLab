package dao;

import entities.Chef;
import java.sql.*;
import connection.DatabaseConnection;

public class ChefDAO 
{
	public ChefDAO() {} // empty constructor (no attributes to initialize)
	
	
	public Chef getChefById (int id)
	{
		Chef chef = null;
		String query = "SELECT * FROM chef WHERE id_chef = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) 
			{
				if (rs.next())
				{
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					String username = rs.getString("username");
					String password = rs.getString("password");
					chef = new Chef(id, nome, cognome, username, password);
				}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return chef; // null if not found
}
}
