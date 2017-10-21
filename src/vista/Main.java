package vista;

import java.awt.EventQueue;

import controlador.AdmUsuarios;
import controlador.SistemaVentas;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SistemaVentas.getInstancia().cargarPorcentajeComision();
					AdmUsuarios.getInstancia().cargarExpiracionPass();
					VistaLogin.getInstancia().setVisible(true);
//					MenuVistas.getInstancia().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
