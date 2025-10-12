package dao;

import connection.DatabaseConnection;
import entities.Chef;
import entities.Corso;
import java.sql.*;
import java.util.List;

public class ChefDAO
{
	private CorsoDAO corsoDAO;
	
	
	public ChefDAO() 
	{
		corsoDAO = new CorsoDAO();
	}
	
	public Chef getById(int id) 
	{
		String query = "SELECT * FROM chef WHERE id_chef = ? ";
		Chef chef = null;
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery())
			{
				if (rs.next()) chef = createChefFromResultSet(rs);
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return chef;
	}
	
	public Chef getByUsernameAndPassword(String username, String password) 
	{
		String query = "SELECT * FROM chef WHERE username = ? AND password = ? ";
		Chef chef = null;
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			try (ResultSet rs = pstmt.executeQuery())
			{
				if (rs.next()) {chef = createChefFromResultSet(rs);}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return chef;
	}
	
	private Chef createChefFromResultSet(ResultSet rs) throws SQLException
	{
		int id = rs.getInt("id_chef");
		String nome = rs.getString("nome");
		String cognome = rs.getString("cognome");
		String username = rs.getString("username");
		String password = rs.getString("password");
		
		List<Corso> corsi = corsoDAO.getByChef(new Chef(id, nome, cognome, username, password));
		
		return new Chef(id, nome, cognome, username, password, corsi);
	}
}
