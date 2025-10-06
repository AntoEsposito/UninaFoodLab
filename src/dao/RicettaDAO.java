package dao;

import connection.DatabaseConnection;
import entities.Ricetta;
import java.sql.*;
import java.util.List;

public class RicettaDAO
{
	public RicettaDAO() {} 
	
	
	public Ricetta getById(int id) 
	{
		String query = "SELECT * FROM ricetta WHERE id_ricetta = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery())
			{
				if (rs.next()) {return createRicettaFromResultSet(rs);}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public List<Ricetta> getByIdSessione(int idSessione) 
	{
		String query = "SELECT r.* FROM ricetta AS r " +
					   "NATURAL JOIN realizzazione_ricetta AS rr " +
					   "WHERE rr.id_sessione = ?";
		
		List<Ricetta> listaRicette = new java.util.ArrayList<>();
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, idSessione);
			try (ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next()) {listaRicette.add(createRicettaFromResultSet(rs));}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return listaRicette;
	}
	
	private Ricetta createRicettaFromResultSet(ResultSet rs) throws SQLException
	{
		int id = rs.getInt("id_ricetta");
		String nome = rs.getString("nome");
		
		return new Ricetta(id, nome);
	}
}