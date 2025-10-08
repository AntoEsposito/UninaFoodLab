package dao;

import connection.DatabaseConnection;
import entities.Corso;
import entities.Ricetta;
import entities.Sessione;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SessioneDAO 
{
	private RicettaDAO ricettaDAO;
	
	
	public SessioneDAO() 
	{
		ricettaDAO = new RicettaDAO();
	}
	
	
	public List<Sessione> getByCorso(Corso corso) 
	{
		String query = "SELECT * FROM sessione WHERE id_corso = ? ";
		List<Sessione> listaSessioni = new ArrayList<>();
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, corso.getId());
			try (ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next()) {listaSessioni.add(createSessioneFromResultSetAndCorso(rs, corso));}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return listaSessioni;
	}
	
	public boolean addSessione(Sessione sessione) 
	{
		String query = "INSERT INTO sessione (in_presenza, data, numero_sessione, url_meeting, id_corso) VALUES (?, ?, ?, ?, ?)";
		boolean added = false;
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) 
		{
			pstmt.setBoolean(1, sessione.isInPresenza());
			pstmt.setDate(2, Date.valueOf(sessione.getData()));
			pstmt.setInt(3, sessione.getNumeroSessione());
			pstmt.setString(4, sessione.getUrlMeeting());
			pstmt.setInt(5, sessione.getCorsoDiAppartenenza().getId());
			
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows > 0) 
			{
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) 
				{
					if (generatedKeys.next()) 
					{
						sessione.setId(generatedKeys.getInt(1));
						added = true;
					}
				}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return added;
	}
	
	private Sessione createSessioneFromResultSetAndCorso(ResultSet rs, Corso corso) throws SQLException
	{
		int id = rs.getInt("id_sessione");
		boolean inPresenza = rs.getBoolean("in_presenza");
		Date dataSql = rs.getDate("data");
		LocalDate data = dataSql.toLocalDate();
		int numeroSessione = rs.getInt("numero_sessione");
		String url = rs.getString("url_meeting");
		
		List<Ricetta> ricette = ricettaDAO.getBySessione(new Sessione(id, inPresenza, data, numeroSessione, url, corso));
		
		return new Sessione(id, inPresenza, data, numeroSessione, url, corso, ricette);
	}
}