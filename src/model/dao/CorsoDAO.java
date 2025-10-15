package model.dao;

import connection.DatabaseConnection;
import model.entity.Categoria;
import model.entity.Chef;
import model.entity.Corso;
import model.entity.FrequenzaSessioni;
import model.entity.Sessione;

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
	
	public Corso getById(int id) 
	{
		String query = "SELECT * FROM corso WHERE id_corso = ? ";
		Corso corso = null;
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery())
			{
				if (rs.next()) 
				{
					int idChef = rs.getInt("id_chef");
					Chef chef = new ChefDAO().getById(idChef);
					corso = createCorsoFromResultSetAndChef(rs, chef);
				}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return corso;
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
	
	public List<Corso> getByChefAndCategoria(Chef chef, Categoria categoria) 
	{
		String query = "SELECT * FROM corso WHERE id_chef = ? AND id_categoria = ? ";
		List<Corso> corsi = new ArrayList<>();
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, chef.getId());
			pstmt.setInt(2, categoria.getId());
			try (ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next()) {corsi.add(createCorsoFromResultSetAndChef(rs, chef));}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return corsi;
	}
	
	public boolean addCorso(Corso corso) 
	{
		String query = "INSERT INTO corso (data_inizio, numero_sessioni, id_frequenza, id_categoria, id_chef) VALUES (?, ?, ?, ?, ?)";
		boolean added = false;
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) 
		{
			pstmt.setDate(1, Date.valueOf(corso.getDataInizio()));
			pstmt.setInt(2, corso.getNumeroSessioni());
			pstmt.setInt(3, corso.getFrequenzaSessioni().getId());
			pstmt.setInt(4, corso.getCategoria().getId());
			pstmt.setInt(5, corso.getChef().getId());
			
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows > 0) 
			{
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) 
				{
					if (generatedKeys.next()) 
					{
						corso.setId(generatedKeys.getInt(1));
						added = true;
					}
				}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return added;
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
