package main;

import persistencia.PoolConnection;

public class TestConnection {

	public static void main(String[] args) {
		PoolConnection con=PoolConnection.getPoolConnection();
		con.testConnection();
	}

}
