package net.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ortak {
	static String jdbcUrl="jdbc:postgresql://localhost:5432/Hospital";
	static String username="postgres";
	static String password="admin";	
	
	static int getCounter(String type) {
		int personelCounter=0,patientCounter=0;
		try {			
			Connection connection=DriverManager.getConnection(jdbcUrl,username,password);
			String sql="SELECT * FROM COUNTERS";
			java.sql.Statement statement=connection.createStatement();
			ResultSet result=statement.executeQuery(sql);	
			
			while(result.next()) {				
				personelCounter=result.getInt("personelCounter");
				patientCounter=result.getInt("patientCounter");
			}			
			connection.close();
		}catch(SQLException x) {
			System.out.println(x);
		}
		if(type.equals("personel")) {
			try {			
				Connection connection=DriverManager.getConnection(jdbcUrl,username,password);
				String sql="UPDATE COUNTERS SET personelCounter=personelCounter+1";
				java.sql.Statement statement=connection.createStatement();
				statement.executeUpdate(sql);									
				connection.close();
			}catch(SQLException x) {
				System.out.println(x);
			}
			return personelCounter;
		}
			
		else {
			try {			
				Connection connection=DriverManager.getConnection(jdbcUrl,username,password);
				String sql="UPDATE COUNTERS SET patientCounter=patientCounter+1";
				java.sql.Statement statement=connection.createStatement();
				statement.executeUpdate(sql);									
				connection.close();
			}catch(SQLException x) {
				System.out.println(x);
			}
			return patientCounter;
		}						
	}

}
