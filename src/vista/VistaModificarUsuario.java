package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.TextField;
import java.awt.Button;

import javax.swing.JButton;

import controlador.AdmUsuarios;
import controlador.UsuarioView;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaModificarUsuario extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VistaModificarUsuario() {
		setTitle("Modificar Usuario");
		setResizable(false);
		setBounds(100, 100, 199, 224);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		UsuarioView uv = AdmUsuarios.getInstancia().getLoggedUserView();
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(10, 11, 90, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 36, 90, 14);
		contentPane.add(lblNombre);
		
		JLabel lblDomicilio = new JLabel("Domicilio:");
		lblDomicilio.setBounds(10, 61, 90, 14);
		contentPane.add(lblDomicilio);
		
		JLabel lblEmail = new JLabel("E-Mail:");
		lblEmail.setBounds(10, 86, 90, 14);
		contentPane.add(lblEmail);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(10, 111, 90, 14);
		contentPane.add(lblContrasea);
		
		TextField textUsuario = new TextField();
		textUsuario.setBounds(106, 3, 75, 22);
		contentPane.add(textUsuario);
		
		TextField textNombre = new TextField();
		textNombre.setBounds(106, 28, 75, 22);
		contentPane.add(textNombre);
		
		TextField textDomicilio = new TextField();
		textDomicilio.setBounds(106, 53, 75, 22);
		contentPane.add(textDomicilio);
		
		TextField textMail = new TextField();
		textMail.setBounds(106, 78, 75, 22);
		contentPane.add(textMail);
		
		TextField textPassword = new TextField();
		textPassword.setBounds(106, 103, 75, 22);
		contentPane.add(textPassword);
		
		if (uv != null) {
			textUsuario.setText(uv.getNombreDeUsuario());
			textUsuario.setEnabled(false);
			textNombre.setText(uv.getNombre());
			textDomicilio.setText(uv.getDomicilio());
			textMail.setText(uv.getMail());
		}
		else {
			//TODO Cerrar ventana.
		}
		
		JLabel lblMensaje = new JLabel("");
		lblMensaje.setBounds(10, 170, 171, 14);
		contentPane.add(lblMensaje);
		
		JButton btnConfirmarCambios = new JButton("Confirmar cambios");
		btnConfirmarCambios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (! textNombre.getText().isEmpty() & ! textDomicilio.getText().isEmpty() & ! textMail.getText().isEmpty()) {
					int resPassRst = -1;
					int resModificar = AdmUsuarios.getInstancia().modificarLoggedUser(textNombre.getText(), textMail.getText(), textDomicilio.getText());
					
					if (! textPassword.getText().isEmpty()) {
						resPassRst = AdmUsuarios.getInstancia().cambiarPassword(textPassword.getText());
					}
					
					if (resModificar == 0 & resPassRst <= 0) {
						lblMensaje.setText("Info modificada.");
					}
					else if(resModificar == 0 & resPassRst > 0) {
						lblMensaje.setText("Info modificada. Contraseña no.");
					}
					else if(resModificar > 0 & resPassRst == 0) {
						lblMensaje.setText("Info no modificada. Contraseña si.");
					}
					else if(resModificar > 0 & resPassRst == -1) {
						lblMensaje.setText("No pudo modificarse la info.");
					}
					else if(resModificar > 0 & resPassRst > 0) {
						lblMensaje.setText("No pudo modificarse la info ni la contraseña.");
					}
					
				}
				else {
					lblMensaje.setText("Todos los campos deben estar completos.");
				}
			}
		});
		btnConfirmarCambios.setBounds(10, 136, 171, 23);
		contentPane.add(btnConfirmarCambios);
		

	}
}
