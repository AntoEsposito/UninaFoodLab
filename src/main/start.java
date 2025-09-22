package main;

import connection.*;

public class Start 
{
	public static void main(String[] args) throws Exception 
	{
		DatabaseConnection db = DatabaseConnection.getInstance();
		db.openConnection();
		db.closeConnection();
		db.openConnection();
		db.closeConnection();
	}
}
