package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.TextField;

import javax.swing.JButton;

import controlador.AdmUsuarios;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class VistaLogin extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VistaLogin() {
		setResizable(false);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 210, 150);
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
		labelMensaje.setBounds(10, 102, 186, 14);
		contentPane.add(labelMensaje);
		
		TextField textNPassword = new TextField();
		textNPassword.setEchoChar('*');
		textNPassword.setBounds(106, 122, 88, 22);
		textNPassword.setVisible(false);
		contentPane.add(textNPassword);
		
		JLabel lblNuevaContrasea = new JLabel("Nueva Contrase\u00F1a");
		lblNuevaContrasea.setBounds(10, 127, 90, 14);
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
							labelMensaje.setText("Usuario logueado correctamente."); //TODO Traducir mensaje
						}
						else {
							labelMensaje.setText("Contraseņa cambiada. Fallo el login."); //TODO Traducir mensaje	
						}
					}
					else {
						labelMensaje.setText("La contraseņa no pudo ser cambiada.");
					}
				}
				else {
					labelMensaje.setText("Introduzca una nueva contraseņa.");
					textNPassword.requestFocus();
				}
				
			}
		});
		btnPwdReset.setBounds(10, 152, 184, 23);
		btnPwdReset.setVisible(false);
		contentPane.add(btnPwdReset);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textUsuario.getText().isEmpty() & !textPassword.getText().isEmpty()) {
					int resLogin = AdmUsuarios.getInstancia().login(textUsuario.getText(), textPassword.getText());
					switch (resLogin) {
						case 0:
							labelMensaje.setText("Usuario logueado correctamente."); //TODO Traducir mensaje
							//TODO Pasar a la siguiente ventana!
							break;
						case 1:
							labelMensaje.setText("Contraseņa expirada. Cambie la contaseņa.");
							setBounds(100, 100, 210, 210);
							textNPassword.setVisible(true);
							lblNuevaContrasea.setVisible(true);
							textNPassword.requestFocus();
							textUsuario.setEnabled(false);
							textPassword.setEnabled(false);
							btnPwdReset.setVisible(true);
							break;
						case 2:
							labelMensaje.setText("Contraseņa incorrecta.");
							textPassword.requestFocus();
							textPassword.selectAll();
							break;
						case 3:
							labelMensaje.setText("El usuario no existe.");
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
