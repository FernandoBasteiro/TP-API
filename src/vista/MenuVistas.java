//Update for GIT

package vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.SistPublicaciones;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MenuVistas extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9144541504916134590L;
	
	private JPanel contentPane;
	private JTextField txtBuscar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuVistas frame = new MenuVistas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuVistas() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaAltaUsuario vau = new VistaAltaUsuario();
				vau.setVisible(true);
			}
		});
		btnCrearUsuario.setBounds(10, 11, 125, 23);
		contentPane.add(btnCrearUsuario);
		
		JButton btnModificarUsuario = new JButton("Modificar Usuario");
		btnModificarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaModificarUsuario vmu = new VistaModificarUsuario();
				vmu.setVisible(true);
			}
		});btnModificarUsuario.setBounds(10, 45, 125, 23);
		contentPane.add(btnModificarUsuario);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaLogin vl = new VistaLogin();
				vl.setVisible(true);
			}
		});
		btnLogin.setBounds(10, 79, 125, 23);
		contentPane.add(btnLogin);
		
		JButton btnCrearPublicacion = new JButton("Crear Publicacion");
		btnCrearPublicacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaCrearPublicacion vcp = new VistaCrearPublicacion();
				vcp.setVisible(true);
			}
		});
		btnCrearPublicacion.setBounds(10, 113, 125, 23);
		contentPane.add(btnCrearPublicacion);
		
		JButton btnVerMisPublicaciones = new JButton("Ver Mis Publicaciones");
		btnVerMisPublicaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaVerPublicaciones vvp = new VistaVerPublicaciones(SistPublicaciones.getInstancia().verMisPublicaciones());
				vvp.setVisible(true);
			}
		});
		btnVerMisPublicaciones.setBounds(10, 147, 125, 23);
		contentPane.add(btnVerMisPublicaciones);
		
		JLabel lblBuscarPublicacion = new JLabel("Buscar Publicacion:");
		lblBuscarPublicacion.setBounds(145, 15, 85, 14);
		contentPane.add(lblBuscarPublicacion);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(240, 12, 86, 20);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaVerPublicaciones vvp = new VistaVerPublicaciones(SistPublicaciones.getInstancia().buscarPublicaciones(txtBuscar.getText()));
				vvp.setVisible(true);
			}
		});
		btnBuscar.setBounds(335, 11, 89, 23);
		contentPane.add(btnBuscar);
	}
}
