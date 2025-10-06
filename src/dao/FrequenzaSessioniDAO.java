package dao;

import connection.DatabaseConnection;
import entities.FrequenzaSessioni;
import java.sql.*;

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

	private FrequenzaSessioni createFrequenzaSessioniFromResultSet(ResultSet rs) throws SQLException 
	{
		int id = rs.getInt("id_frequenza");
		String descrizione = rs.getString("descrizione");
		
		return new FrequenzaSessioni(id, descrizione);
	}
}