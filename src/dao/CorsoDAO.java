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
	private ChefDAO chefDAO;
	private SessioneDAO sessioneDAO;
	
	
	public CorsoDAO() 
	{
		frequenzaSessioniDAO = new FrequenzaSessioniDAO();
		categoriaDAO = new CategoriaDAO();
		chefDAO = new ChefDAO();
		sessioneDAO = new SessioneDAO();
	}
	
	
	public Corso getById(int id) 
	{
		String query = "SELECT * FROM corso	NATURAL JOIN sessione WHERE id_corso = ? ";
		Corso corso = null;
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery())
			{
				if (rs.next()) {corso = createCorsoFromResultSet(rs);}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return corso;
	}
	
	private Corso createCorsoFromResultSet(ResultSet rs) throws SQLException
	{
		int id = rs.getInt("id_corso");
		LocalDate dataInizio = rs.getDate("data_inizio").toLocalDate();
		int numeroSessioni = rs.getInt("numero_sessioni");
		
		int idFrequenza = rs.getInt("id_frequenza");
		FrequenzaSessioni frequenza = frequenzaSessioniDAO.getById(idFrequenza);
		
		int idCategoria = rs.getInt("id_categoria");
		Categoria categoria = categoriaDAO.getById(idCategoria);
		
		int idChef = rs.getInt("id_chef");
		Chef chef = chefDAO.getById(idChef);
		
		List<Sessione> sessioni = sessioneDAO.getByIdCorso(id);
		
		return new Corso(id, dataInizio, numeroSessioni, frequenza, categoria, chef, sessioni);
	}
}
