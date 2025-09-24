package connection;

import java.sql.*;

// Singleton class that handles the connection
public class DatabaseConnection 
{
    // unique class instance
    private static DatabaseConnection instance;
    
    private Connection connection;
    
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
    // opens connection and returns it
    public Connection getConnection() throws SQLException 
    {
        if (connection == null || connection.isClosed()) 
        {
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    public void closeConnection() throws SQLException 
    {
        if (connection != null && !connection.isClosed()) connection.close();
    }
}
