//Update for GIT

package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MenuVistas extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9144541504916134590L;
	
	static private MenuVistas instancia;
	private JPanel contentPane;
	private JTextField txtBuscar;

	static public MenuVistas getInstancia() {
		if (instancia == null) {
			instancia = new MenuVistas();
		}
		return instancia;
	}
	
	/**
	 * Create the frame.
	 */
	private MenuVistas() {
		setTitle("Sistema de compras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 671, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnUsuarios = new JMenu("Usuarios");
		menuBar.add(mnUsuarios);
		
		JMenuItem mntmModificarInformacion = new JMenuItem("Modificar informacion");
		mntmModificarInformacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaModificarUsuario vmu = new VistaModificarUsuario();
				vmu.setVisible(true);
			}
		});
		mnUsuarios.add(mntmModificarInformacion);
		
		JMenu mnPublicaciones = new JMenu("Publicaciones");
		menuBar.add(mnPublicaciones);
		
		JMenuItem mntmCreaPublicacion = new JMenuItem("Crear publicacion");
		mntmCreaPublicacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaCrearPublicacion.getInstancia().setVisible(true);
			}
		});
		mnPublicaciones.add(mntmCreaPublicacion);
		
		JMenuItem mntmVerMisPublicaciones = new JMenuItem("Ver mis publicaciones");
		mntmVerMisPublicaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaVerPublicaciones.getInstancia("").setVisible(true);
			}
		});
		mnPublicaciones.add(mntmVerMisPublicaciones);
		
		JMenuItem mntmBuscarPublicaciones = new JMenuItem("Buscar publicaciones");
		mntmBuscarPublicaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnPublicaciones.add(mntmBuscarPublicaciones);
		
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
				if (! txtBuscar.getText().isEmpty()) {
					VistaVerPublicaciones.getInstancia(txtBuscar.getText()).setVisible(true);
				}
				else {
					//TODO Mostrar error.
				}
				
			}
		});
		btnBuscar.setBounds(335, 11, 89, 23);
		contentPane.add(btnBuscar);
	}
}
