/*package com.organizen.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/managementdb";
    private static final String USER = "root"; // Nutzername root standard
    private static final String PASSWORD = ""; // Muss leer sein, da kein Passwort definiert
    
    public static Connection getConnection() throws SQLException {
    
    	    try {
    	        Class.forName("com.mysql.cj.jdbc.Driver");
    	    } catch (ClassNotFoundException e) {
    	        e.printStackTrace();
    	    }


        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}*/
