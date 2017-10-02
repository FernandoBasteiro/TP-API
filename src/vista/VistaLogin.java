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
		setBounds(100, 100, 211, 152);
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
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textUsuario.getText().isEmpty() & !textPassword.getText().isEmpty()) {
					int resLogin = AdmUsuarios.getInstance().login(textUsuario.getText(), textPassword.getText());
					switch (resLogin) {
						case 0:
							labelMensaje.setText("Usuario logueado correctamente."); //TODO Traducir mensaje
							//TODO Pasar a la siguiente ventana!
							break;
						case 1:
							//TODO Solicitar cambio de contraseña.
							break;
						case 2:
							labelMensaje.setText("Contraseña incorrecta.");
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
