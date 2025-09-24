package dao;

import connection.DatabaseConnection;
import entities.FrequenzaSessioni;
import java.sql.*;

public class FrequenzaSessioniDAO 
{
	public FrequenzaSessioniDAO() {} // empty constructor (no attributes to initialize)
	
	
	public FrequenzaSessioni getFrequenzaById(int id)
	{
		FrequenzaSessioni frequenza = null;
		String query = "SELECT * FROM frequenza_sessioni WHERE id = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) 
			{
				if (rs.next())
				{
					String descrizione = rs.getString("descrizione");
					frequenza = new FrequenzaSessioni(id, descrizione);
				}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return frequenza;
	}
}
