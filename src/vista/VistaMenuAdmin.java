package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class VistaMenuAdmin extends JFrame {

	private JPanel contentPane;
	private static VistaMenuAdmin instancia;
	
	public static VistaMenuAdmin getInstancia() {
		if (instancia == null) {
			instancia = new VistaMenuAdmin();
		}
		return instancia;
	}

	public VistaMenuAdmin() {
		setTitle("Admin View");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 186, 121);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.setBounds(10, 11, 150, 23);
		contentPane.add(btnCrearUsuario);
		
		JButton btnModificarUsuario = new JButton("Modificar Usuario");
		btnModificarUsuario.setBounds(10, 45, 150, 23);
		contentPane.add(btnModificarUsuario);
	}

}
