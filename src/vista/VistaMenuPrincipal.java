package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;

import controlador.AdmUsuarios;
import controlador.SistPublicaciones;
import controlador.UsuarioLogueadoView;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VistaMenuPrincipal extends JFrame {

	private JPanel contentPane;
	static private VistaMenuPrincipal instancia;
	private JTextField textBuscarProducto;
	private JLabel lblUsuarioLogueado;
	private JLabel lblCalificacionesPendientes;
	private JLabel lblEstadoCuentaCorriente;

	static public VistaMenuPrincipal getInstancia() {
		if (instancia == null) {
			instancia = new VistaMenuPrincipal();
		}
		return instancia;
	}
	
	private VistaMenuPrincipal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				cargarDatosUsuario();
			}
		});
		setTitle("MercadoCautivo");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 143);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblUsuarioLogueado = new JLabel("");
		lblUsuarioLogueado.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUsuarioLogueado.setBounds(294, 11, 227, 14);
		contentPane.add(lblUsuarioLogueado);
		
		lblCalificacionesPendientes = new JLabel("");
		lblCalificacionesPendientes.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCalificacionesPendientes.setBounds(294, 36, 227, 14);
		contentPane.add(lblCalificacionesPendientes);
		
		lblEstadoCuentaCorriente = new JLabel("");
		lblEstadoCuentaCorriente.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEstadoCuentaCorriente.setBounds(294, 61, 227, 14);
		contentPane.add(lblEstadoCuentaCorriente);
		
		JButton btnVerMisPublicaciones = new JButton("Ver Mis Publicaciones");
		btnVerMisPublicaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaVerPublicaciones.getInstancia("").setVisible(true);
			}
		});
		btnVerMisPublicaciones.setBounds(10, 32, 274, 23);
		contentPane.add(btnVerMisPublicaciones);
		
		JButton btnModificarInformacion = new JButton("Modificar Informacion");
		btnModificarInformacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VistaModificarUsuario vmu = new VistaModificarUsuario();
				vmu.setVisible(true);
			}
		});
		btnModificarInformacion.setBounds(10, 7, 274, 23);
		contentPane.add(btnModificarInformacion);
		
		JButton btnCrearNuevaPublicacion = new JButton("Crear Nueva Publicacion");
		btnCrearNuevaPublicacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaCrearPublicacion.getInstancia().setVisible(true);
			}
		});
		btnCrearNuevaPublicacion.setBounds(10, 57, 274, 23);
		contentPane.add(btnCrearNuevaPublicacion);
		
		JLabel lblBuscarProducto = new JLabel("Buscar Producto:");
		lblBuscarProducto.setBounds(10, 88, 100, 14);
		contentPane.add(lblBuscarProducto);
		
		textBuscarProducto = new JTextField();
		textBuscarProducto.setBounds(98, 85, 86, 20);
		contentPane.add(textBuscarProducto);
		textBuscarProducto.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaVerPublicaciones.getInstancia(textBuscarProducto.getText()).setVisible(true);
			}
		});
		btnBuscar.setBounds(195, 84, 89, 23);
		contentPane.add(btnBuscar);
	}
	
	private void cargarDatosUsuario(){
		UsuarioLogueadoView vul = AdmUsuarios.getInstancia().getVistaUsuarioLogueado();
		lblUsuarioLogueado.setText(vul.getNombre());
		lblCalificacionesPendientes.setText("Calificaciones Pendientes (" + String.valueOf(vul.getCalificacionesPendientes()) + ")");
		lblEstadoCuentaCorriente.setText("Estado Cuenta Corriente: " + String.valueOf(vul.getEstadoCtaCte()));
	}

}
