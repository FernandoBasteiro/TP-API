package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlador.AdmUsuarios;

public class VistaAltaUsuario extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3173593118178927673L;
	
	
	private JPanel contentPane;
	private JTextField textNombre;
	private JTextField textDomicilio;
	private JTextField textMail;
	private JTextField textNombreDeUsuario;
	private JTextField textPassword;
	static private VistaAltaUsuario instancia;

	

	/**
	 * Create the frame.
	 */
	static public VistaAltaUsuario getInstancia() {
		if (instancia == null) {
			instancia = new VistaAltaUsuario();
		}
		return instancia;
	}
	
	private VistaAltaUsuario() {
		setType(Type.UTILITY);
		setResizable(false);
		setTitle("Crear Usuario");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 352, 210);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 11, 65, 14);
		contentPane.add(lblNombre);
		
		JLabel lblDomicilio = new JLabel("Domicilio");
		lblDomicilio.setBounds(10, 36, 65, 14);
		contentPane.add(lblDomicilio);
		
		JLabel lblEmail = new JLabel("E-Mail");
		lblEmail.setBounds(10, 61, 65, 14);
		contentPane.add(lblEmail);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(10, 86, 65, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(10, 111, 65, 14);
		contentPane.add(lblContrasea);
		
		textNombre = new JTextField();
		textNombre.setBounds(85, 8, 250, 20);
		contentPane.add(textNombre);
		textNombre.setColumns(10);
		
		textDomicilio = new JTextField();
		textDomicilio.setBounds(85, 33, 250, 20);
		contentPane.add(textDomicilio);
		textDomicilio.setColumns(10);
		
		textMail = new JTextField();
		textMail.setBounds(85, 58, 250, 20);
		contentPane.add(textMail);
		textMail.setColumns(10);
		
		textNombreDeUsuario = new JTextField();
		textNombreDeUsuario.setBounds(85, 83, 250, 20);
		contentPane.add(textNombreDeUsuario);
		textNombreDeUsuario.setColumns(10);
		
		textPassword = new JTextField();
		textPassword.setBounds(85, 108, 250, 20);
		contentPane.add(textPassword);
		textPassword.setColumns(10);
		
		JLabel labelResultado = new JLabel("");
		labelResultado.setBounds(10, 170, 161, 18);
		contentPane.add(labelResultado);
		
		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nombre = textNombre.getText();
				String domicilio = textDomicilio.getText();
				String mail = textMail.getText();
				String nombreDeUsuario = textNombreDeUsuario.getText();
				String passwordString = textPassword.getText();
				int error = AdmUsuarios.getInstancia().crearUsuario(nombre, domicilio, mail, nombreDeUsuario, passwordString);
				switch (error) {
					case 0:
						VistaAltaUsuario.getInstancia().setVisible(false);
						break;
					case 1:
						labelResultado.setText("El nombre de usuario ya existe.");
						textNombreDeUsuario.selectAll();
						break;
				}
			}
		});
		btnCrearUsuario.setBounds(10, 136, 161, 23);
		contentPane.add(btnCrearUsuario);
		
	
	}
}
