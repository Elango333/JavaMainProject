package com.loginPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	 private static DatabaseConnection instance;
	    private Connection connection;

	    private DatabaseConnection() {
	        try {
	        	Class.forName("com.mysql.cj.jdbc.Driver");
	            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Courier", "elango-zstk340", "elango33");
	        } catch (Exception e) {
	            System.out.println("Error");	        }
	    }

	    public static DatabaseConnection getInstance() {
	        if (instance == null) {
	            instance = new DatabaseConnection();
	        }
	        return instance;
	    }

	
	    public Connection getConnection() {
	        return connection;
	    }

	
	    public void closeConnection() {
	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	        	   System.out.println("Error");
	        }
	    }
}
