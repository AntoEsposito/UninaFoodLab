package connection;

import java.sql.*;

// Singleton class that handles the connection
public class DatabaseConnection 
{
    // unique class instance
    private static DatabaseConnection instance;
    
    private final String url = "jdbc:postgresql://localhost:5432/UninaFoodLab";
    private final String user = "postgres";
    private final String password = "2684";

    
    // private constructor
    private DatabaseConnection() {}

    
    // getter of the unique instance
    public static DatabaseConnection getInstance() 
    {
        if (instance == null) instance = new DatabaseConnection();
        return instance;
    }
    
    // Crea e restituisce una NUOVA connessione ogni volta
    public Connection getConnection() throws SQLException 
    {
        return DriverManager.getConnection(url, user, password);
    }
}