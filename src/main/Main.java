package main;

import java.awt.EventQueue;

import vista.VistaLogin;

public class Main {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaLogin frame = VistaLogin.getInstancia();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
