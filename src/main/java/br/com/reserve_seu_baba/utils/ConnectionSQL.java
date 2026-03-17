package br.com.reserve_seu_baba.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQL {
	public ConnectionSQL() {}
	
	@SuppressWarnings("finally")
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String server = "localhost:3306";
			String db =  "reserve_seu_baba_2";
			String url = "jdbc:mysql://" + server + "/" + db;
			String user = "will";
			String password = "12345678";
			connection = DriverManager.getConnection(url, user, password);			
		} catch (SQLException e) {
			System.out.println("Não foi possível conectar com o banco: " + e);
		} finally {
			return connection;
		}
	} 
}
