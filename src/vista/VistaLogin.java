package vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.AdmUsuarios;

public class VistaLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7091613278470266613L;
	private JPanel contentPane;
	static private VistaLogin instancia;

	public static void main(String[] args) {
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

	static public VistaLogin getInstancia() {
		if (instancia == null) {
			instancia = new VistaLogin();
		}
		return instancia;
	}
	
	private VistaLogin() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 210, 184);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(10, 11, 90, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(10, 36, 90, 14);
		contentPane.add(lblContrasea);
		
		TextField textPassword = new TextField();
		textPassword.setEchoChar('*');
		textPassword.setBounds(106, 36, 90, 22);
		contentPane.add(textPassword);
		
		TextField textUsuario = new TextField();
		textUsuario.setBounds(106, 3, 90, 22);
		contentPane.add(textUsuario);
		
		JLabel labelMensaje = new JLabel("");
		labelMensaje.setFont(new Font("Tahoma", Font.PLAIN, 11));
		labelMensaje.setBounds(10, 127, 186, 14);
		contentPane.add(labelMensaje);
		
		TextField textNPassword = new TextField();
		textNPassword.setEchoChar('*');
		textNPassword.setBounds(108, 147, 88, 22);
		textNPassword.setVisible(false);
		contentPane.add(textNPassword);
		
		JLabel lblNuevaContrasea = new JLabel("Nueva Contrase\u00F1a");
		lblNuevaContrasea.setBounds(10, 152, 90, 14);
		lblNuevaContrasea.setVisible(false);
		contentPane.add(lblNuevaContrasea);
		
		JButton btnPwdReset = new JButton("Cambiar Contrase\u00F1a");
		btnPwdReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textNPassword.getText().isEmpty()) {
					int resPwdReset = AdmUsuarios.getInstancia().cambiarPassword(textUsuario.getText(), textPassword.getText(), textNPassword.getText());
					if (resPwdReset == 0) {
						int resLogin = AdmUsuarios.getInstancia().login(textUsuario.getText(), textNPassword.getText());
						if (resLogin == 0) {
							VistaMenuPrincipal.getInstancia().setVisible(true);
							VistaLogin.getInstancia().setVisible(false);
						}
						else {
							labelMensaje.setText("Contrase�a cambiada. Fallo el login."); //TODO Traducir mensaje	
						}
					}
					else {
						labelMensaje.setText("La contrase�a no pudo ser cambiada.");
					}
				}
				else {
					labelMensaje.setText("Introduzca una nueva contrase�a.");
					textNPassword.requestFocus();
				}
				
			}
		});
		btnPwdReset.setBounds(10, 175, 184, 23);
		btnPwdReset.setVisible(false);
		contentPane.add(btnPwdReset);
		
		
		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VistaAltaUsuario.getInstancia().setVisible(true);
			}
		});
		btnCrearUsuario.setBounds(10, 93, 186, 23);
		contentPane.add(btnCrearUsuario);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textUsuario.getText().isEmpty() & !textPassword.getText().isEmpty()) {
					int resLogin = AdmUsuarios.getInstancia().login(textUsuario.getText(), textPassword.getText());
					switch (resLogin) {
						case -1:
							VistaMenuAdmin.getInstancia().setVisible(true);
							VistaLogin.getInstancia().setVisible(false);
							break;
						case 0:
							VistaMenuPrincipal.getInstancia().setVisible(true);
							VistaLogin.getInstancia().setVisible(false);
							break;
						case 1:
							labelMensaje.setText("Contrase�a expirada. Cambie la contase�a.");
							setBounds(100, 100, 210, 235);
							textNPassword.setVisible(true);
							lblNuevaContrasea.setVisible(true);
							textNPassword.requestFocus();
							textUsuario.setEnabled(false);
							textPassword.setEnabled(false);
							btnPwdReset.setVisible(true);
							btnCrearUsuario.setVisible(false);
							break;
						case 2:
							labelMensaje.setText("Contrase�a incorrecta.");
							textPassword.requestFocus();
							textPassword.selectAll();
							break;
						case 3:
							labelMensaje.setText("El usuario no existe.");
							textUsuario.requestFocus();
							textUsuario.selectAll();
							break;
						case 4:
							labelMensaje.setText("El usuario esta inactivo.");
							textUsuario.requestFocus();
							textUsuario.selectAll();
							break;
						default:
							labelMensaje.setText("Ocurrio un error desconocido.");
							textUsuario.requestFocus();
							textUsuario.selectAll();
							break;
					}
				}
				else {
					labelMensaje.setText("Todos los campos deben ser completados.");
				}
			}
		});
		btnLogin.setBounds(10, 68, 186, 23);
		contentPane.add(btnLogin);
	
		
		
		

		
		
	}
}
