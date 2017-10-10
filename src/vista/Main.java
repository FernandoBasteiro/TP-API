package vista;

import java.awt.EventQueue;

import controlador.AdmUsuarios;
import controlador.SistemaVentas;

public class Main {

	public static void main(String[] args) {
		SistemaVentas.getInstancia().cargarPorcentajeComision();
		AdmUsuarios.getInstancia().cargarExpiracionPass();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaLogin.getInstancia().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
