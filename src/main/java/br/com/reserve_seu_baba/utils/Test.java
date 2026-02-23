package br.com.reserve_seu_baba.utils;

import java.sql.Connection;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = new ConnectionSQL().getConnection();
		System.out.println(conn);
	}

}
