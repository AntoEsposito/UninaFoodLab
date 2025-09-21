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

    public void openConnection() 
    {
    	try 
        {
            if (connection == null || connection.isClosed()) 
            {
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connection established!");
            }
        } 
        catch (SQLException e) 
        {
            System.out.println("Connection Failed! " + e.getMessage());
        }
    }

    public void closeConnection() 
    {
        try 
        {
            if (connection != null && !connection.isClosed()) 
            {
                connection.close();
                System.out.println("Connection closed!");
            }
        } 
        catch (SQLException e) 
        {
            System.out.println("Failed to close connection! " + e.getMessage());
        }
    }
    
    public Connection getConnection() 
    {
        return connection;
    }
}
