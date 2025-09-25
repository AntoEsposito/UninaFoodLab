package dao;

import entities.Corso;
import entities.FrequenzaSessioni;
import entities.Categoria;
import entities.Chef;

import java.sql.*;
import connection.DatabaseConnection;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;


public class CorsoDAO 
{
	public CorsoDAO() {} // empty constructor (no attributes to initialize)
	
	
	public Corso getCorsoById(int id)
	{
		Corso corso = null;
		String query = "SELECT * FROM corso WHERE id_corso = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) 
			{
				if (rs.next())
				{
					LocalDate dataInizio = rs.getDate("data_inizio").toLocalDate();
					int numeroSessioni = rs.getInt("numero_sessioni");
					
					FrequenzaSessioniDAO frequenzaDAO = new FrequenzaSessioniDAO();
					FrequenzaSessioni frequenza = frequenzaDAO.getFrequenzaById(rs.getInt("id_frequenza"));
					
					CategoriaDAO categoriaDAO = new CategoriaDAO();
					Categoria categoria = categoriaDAO.getCategoriaById(rs.getInt("id_categoria"));

					ChefDAO chefDAO = new ChefDAO();
					Chef chef = chefDAO.getChefById(rs.getInt("id_chef"));
					
					corso = new Corso(id, dataInizio, numeroSessioni, frequenza, categoria, chef);
				}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return corso;
	}
	
	public List<Corso> getCorsoByChef(Chef chef)
	{
		List<Corso> corsi = new ArrayList<Corso>();
		String query = "SELECT * FROM corso WHERE id_chef = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setInt(1, chef.getId());
			
			try (ResultSet rs = pstmt.executeQuery()) 
			{
				while (rs.next())
				{
					int id = rs.getInt("id_corso");
					LocalDate dataInizio = rs.getDate("data_inizio").toLocalDate();
					int numeroSessioni = rs.getInt("numero_sessioni");
					
					FrequenzaSessioniDAO frequenzaDAO = new FrequenzaSessioniDAO();
					FrequenzaSessioni frequenza = frequenzaDAO.getFrequenzaById(rs.getInt("id_frequenza"));
					
					CategoriaDAO categoriaDAO = new CategoriaDAO();
					Categoria categoria = categoriaDAO.getCategoriaById(rs.getInt("id_categoria"));
					
					Corso corso = new Corso(id, dataInizio, numeroSessioni, frequenza, categoria, chef);
					corsi.add(corso);
				}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return corsi; // null if not found
	}
	
	public boolean addCorso(Corso corso) 
	{
		boolean success = false;
		String query = "INSERT INTO corso (data_inizio, numero_sessioni, id_frequenza, id_categoria, id_chef) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setDate(1, Date.valueOf(corso.getDataInizio()));
			pstmt.setInt(2, corso.getNumeroSessioni());
			pstmt.setInt(3, corso.getFrequenza().getId());
			pstmt.setInt(4, corso.getCategoria().getId());
			pstmt.setInt(5, corso.getChef().getId());
			
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows > 0) success = true;
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return success;
	}
	
	
}