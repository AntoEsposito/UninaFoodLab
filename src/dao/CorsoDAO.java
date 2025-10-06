package dao;

import connection.DatabaseConnection;
import entities.Corso;
import entities.FrequenzaSessioni;
import entities.Categoria;
import entities.Chef;
import entities.Sessione;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class CorsoDAO 
{
	private FrequenzaSessioniDAO frequenzaSessioniDAO;
	private CategoriaDAO categoriaDAO;
	private SessioneDAO sessioneDAO;
	
	
	public CorsoDAO() 
	{
		frequenzaSessioniDAO = new FrequenzaSessioniDAO();
		categoriaDAO = new CategoriaDAO();
		sessioneDAO = new SessioneDAO();
	}
	
	
	public List<Corso> getByChef(Chef chef) 
	{
		String query = "SELECT * FROM corso WHERE id_chef = ? ";
		List<Corso> corsi = new ArrayList<>();
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, chef.getId());
			try (ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next()) {corsi.add(createCorsoFromResultSetAndChef(rs, chef));}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return corsi;
	}
	
	private Corso createCorsoFromResultSetAndChef(ResultSet rs, Chef chef) throws SQLException
	{
		int id = rs.getInt("id_corso");
		LocalDate dataInizio = rs.getDate("data_inizio").toLocalDate();
		int numeroSessioni = rs.getInt("numero_sessioni");
		
		int idFrequenza = rs.getInt("id_frequenza");
		FrequenzaSessioni frequenza = frequenzaSessioniDAO.getById(idFrequenza);
		
		int idCategoria = rs.getInt("id_categoria");
		Categoria categoria = categoriaDAO.getById(idCategoria);
		
		List<Sessione> sessioni = sessioneDAO.getByCorso(new Corso(id, dataInizio, numeroSessioni, frequenza, categoria, chef));
		
		return new Corso(id, dataInizio, numeroSessioni, frequenza, categoria, chef, sessioni);
	}
}
