package dao;

import entities.Ricetta;
import entities.Sessione;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import connection.DatabaseConnection;

public class RicettaDAO 
{
	public RicettaDAO() {} // empty constructor (no attributes to initialize)
	
	
	public Ricetta getRicettaById(int id)
	{
		Ricetta ricetta = null;
		String queryRicetta = "SELECT nome FROM ricetta WHERE id_ricetta = ?";
		String querySessioni = "SELECT id_sessione FROM realizzazione_ricetta WHERE id_ricetta = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmtRicetta = conn.prepareStatement(queryRicetta);
			 PreparedStatement pstmtSessioni = conn.prepareStatement(querySessioni))
		{
			pstmtRicetta.setInt(1, id);
			try (ResultSet rsRicetta = pstmtRicetta.executeQuery())
			{
				if (rsRicetta.next())
				{
					String nome = rsRicetta.getString("nome");
					
					List<Sessione> sessioni = new ArrayList<>();
					pstmtSessioni.setInt(1, id);
					try (ResultSet rsSessioni = pstmtSessioni.executeQuery())
					{
						SessioneDAO sessioneDAO = new SessioneDAO();
						while(rsSessioni.next())
						{
							sessioni.add(sessioneDAO.getSessioneById(rsSessioni.getInt("id_sessione")));
						}
					}
					
					ricetta = new Ricetta(id, nome, sessioni);
				}
			}
		}
		catch (SQLException e) {e.printStackTrace();}
		
		return ricetta;
	}
}