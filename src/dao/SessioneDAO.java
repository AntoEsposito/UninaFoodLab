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
	
	private Sessione createSessioneFromResultSetAndCorso(ResultSet rs, Corso corso) throws SQLException
	{
		int id = rs.getInt("id_sessione");
		boolean inPresenza = rs.getBoolean("in_presenza");
		Date dataSql = rs.getDate("data");
		LocalDate data = dataSql.toLocalDate();
		int numeroSessione = rs.getInt("numero_sessione");
		String url = rs.getString("url");
		
		List<Ricetta> ricette = ricettaDAO.getBySessione(new Sessione(id, inPresenza, data, numeroSessione, url, corso));
		
		return new Sessione(id, inPresenza, data, numeroSessione, url, corso, ricette);
	}
}