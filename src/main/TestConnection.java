package main;

import persistencia.PoolConnectionMySQL;

public class TestConnection {

	public static void main(String[] args) {
		PoolConnectionMySQL con=PoolConnectionMySQL.getPoolConnection();
		con.testConnection();
	}

}
