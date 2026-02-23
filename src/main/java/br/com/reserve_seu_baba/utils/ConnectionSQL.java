package br.com.reserve_seu_baba.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQL {
	public ConnectionSQL() {}
	
	public static Connection getConnection() {
		Connection connection = null;
		try {
			String server = "localhost";
			String db =  "reserve_seu_baba";
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
