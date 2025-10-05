package dao;

import connection.DatabaseConnection;
import entities.FrequenzaSessioni;
import java.sql.*;

public class FrequenzaSessioniDAO 
{
	public FrequenzaSessioniDAO() {} 
	
	
	public FrequenzaSessioni getById(int id) 
	{
		FrequenzaSessioni frequenza = null;
		String query = "SELECT * FROM frequenza_sessioni WHERE id_frequenza = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) 
			{
				String descrizione = rs.getString("descrizione");
				frequenza = new FrequenzaSessioni(id, descrizione);
			}
			
			rs.close();
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return frequenza;
	}
}
