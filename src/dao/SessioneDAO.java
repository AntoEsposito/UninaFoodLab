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
	private CorsoDAO corsoDAO;
	private RicettaDAO ricettaDAO;
	
	
	public SessioneDAO() 
	{
		corsoDAO = new CorsoDAO();
		ricettaDAO = new RicettaDAO();
	}
	
	
	public List<Sessione> getByIdCorso(int idCorso) 
	{
		String query = "SELECT * FROM sessione NATURAL JOIN ricetta WHERE id_corso = ? ";
		List<Sessione> listaSessioni = new ArrayList<>();
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, idCorso);
			try (ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next()) {listaSessioni.add(createSessioneFromResultSet(rs));}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return listaSessioni;
	}
	
	private Sessione createSessioneFromResultSet(ResultSet rs) throws SQLException
	{
		int id = rs.getInt("id_sessione");
		boolean inPresenza = rs.getBoolean("in_presenza");
		Date dataSql = rs.getDate("data");
		LocalDate data = dataSql.toLocalDate();
		int numeroSessione = rs.getInt("numero_sessione");
		String url = rs.getString("url");
		
		int idCorso = rs.getInt("id_corso");
		Corso corso = corsoDAO.getById(idCorso);
		
		List<Ricetta> ricette = ricettaDAO.getByIdSessione(id);
		
		return new Sessione(id, inPresenza, data, numeroSessione, url, corso, ricette);
	}
}
