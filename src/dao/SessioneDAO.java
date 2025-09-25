package dao;

import entities.Sessione;
import entities.Corso;
import entities.Ricetta;

import java.sql.*;
import connection.DatabaseConnection;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class SessioneDAO 
{
	public SessioneDAO() {} // empty constructor (no attributes to initialize)
	
	
	public List<Sessione> getSessioniByCorso(Corso corso)
	{
		List<Sessione> sessioni = new ArrayList<Sessione>();
		String query = "SELECT * FROM sessione WHERE id_corso = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, corso.getId());
			
			try (ResultSet rs = pstmt.executeQuery()) 
			{
				while (rs.next())
				{
					int id = rs.getInt("id_sessione");
					boolean inPresenza = rs.getBoolean("in_presenza");
					LocalDate data = rs.getDate("data").toLocalDate();
					int numeroSessione = rs.getInt("numero_sessione");
					String urlMeeting = rs.getString("url_meeting");
					
					Sessione sessione = new Sessione(id, inPresenza, data, numeroSessione, urlMeeting, corso);
					sessioni.add(sessione);
				}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return sessioni; // null if not found
	}
	
	public Sessione getSessioneById(int id)
	{
		Sessione sessione = null;
		String query = "SELECT * FROM sessione WHERE id_sessione = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) 
			{
				if (rs.next())
				{
					boolean inPresenza = rs.getBoolean("in_presenza");
					LocalDate data = rs.getDate("data").toLocalDate();
					int numeroSessione = rs.getInt("numero_sessione");
					String urlMeeting = rs.getString("url_meeting");
					
					CorsoDAO corsoDAO = new CorsoDAO();
					Corso corso = corsoDAO.getCorsoById(rs.getInt("id_corso"));
					
					List<Ricetta> ricette = new ArrayList<>();
					String queryRicette = "SELECT id_ricetta FROM realizzazione_ricetta WHERE id_sessione = ?";
					try (PreparedStatement pstmtRicette = conn.prepareStatement(queryRicette))
					{
						pstmtRicette.setInt(1, id);
						try (ResultSet rsRicette = pstmtRicette.executeQuery())
						{
							RicettaDAO ricettaDAO = new RicettaDAO();
							while(rsRicette.next())
							{
								ricette.add(ricettaDAO.getRicettaById(rsRicette.getInt("id_ricetta")));
							}
						}
					}
					
					sessione = new Sessione(id, inPresenza, data, numeroSessione, urlMeeting, corso, ricette);
				}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return sessione; // null if not found
	}
}
