package dao;

import connection.DatabaseConnection;
import entities.Sessione;
import entities.Ricetta;
import java.sql.*;

public class RealizzazioneRicettaDAO 
{
    
    public boolean associaRicettaASessione(Sessione sessione, Ricetta ricetta) 
    {
        String query = "INSERT INTO realizzazione_ricetta (id_sessione, id_ricetta) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) 
        {    
            pstmt.setInt(1, sessione.getId());
            pstmt.setInt(2, ricetta.getId());
           
            if (pstmt.executeUpdate() > 0) return true;
            else return false;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean rimuoviRicettaDaSessione(Sessione sessione, Ricetta ricetta) 
	{
		String query = "DELETE FROM realizzazione_ricetta WHERE id_sessione = ? AND id_ricetta = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{    
			pstmt.setInt(1, sessione.getId());
			pstmt.setInt(2, ricetta.getId());
		   
			if (pstmt.executeUpdate() > 0) return true;
			else return false;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
  
}
