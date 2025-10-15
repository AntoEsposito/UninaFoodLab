package model.dao;

import connection.DatabaseConnection;
import model.entity.FrequenzaSessioni;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class FrequenzaSessioniDAO 
{
	public FrequenzaSessioniDAO() {} 
	
	
	public FrequenzaSessioni getById(int id) 
	{
		String query = "SELECT * FROM frequenza_sessioni WHERE id_frequenza = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery())
			{
				if (rs.next()) {return createFrequenzaSessioniFromResultSet(rs);}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public List<FrequenzaSessioni> getAll() 
	{
		String query = "SELECT * FROM frequenza_sessioni";
		List<FrequenzaSessioni> listaFrequenze = new ArrayList<>();
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query);
			 ResultSet rs = pstmt.executeQuery()) 
		{
			while (rs.next()) {listaFrequenze.add(createFrequenzaSessioniFromResultSet(rs));}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return listaFrequenze;
	}
	
	private FrequenzaSessioni createFrequenzaSessioniFromResultSet(ResultSet rs) throws SQLException 
	{
		int id = rs.getInt("id_frequenza");
		String descrizione = rs.getString("descrizione");
		
		return new FrequenzaSessioni(id, descrizione);
	}
}