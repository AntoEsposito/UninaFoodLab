package main;

import connection.*;

public class start 
{
	public static void main(String[] args) throws Exception 
	{
		DatabaseConnection db = DatabaseConnection.getInstance();
		db.openConnection();
		db.closeConnection();
	}
}
