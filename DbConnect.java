package com.nnbp.application.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnect {
	
	
	//NNBP DATABASE
	//private static String DATABASE_URL = "jdbc:oracle:thin:@ENS048-ORA1TEST:1521/test"; // green
	//private static String DATABASE_URL = "jdbc:sqlserver://ENS048-SQL:1433/NNBP"; // green
	private static String DATABASE_URL = "jdbc:sqlserver://ENS048-SQL:1433;DatabaseName=NNBP_test"; // green
	private static String DATABASE_USER = "nnbp_sys_test";
	private static String DATABASE_PASS = "8adjpP72";
	
	
	public static Connection getConnection() {
		try {
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASS);
			
			return connection;
			
		} catch (Exception ex) {
			System.out.println("Database.getConnection() Error -->" + ex.getMessage());
			return null;
		}
	}

	public static void close(Connection con) {
		try {
			con.close();
		} catch (Exception ex) {
		}
	}
	
	

		
	
	
}
