package vista;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.AdmUsuarios;
import controlador.UsuarioView;

public class VistaModificarUsuario extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -228100595448307234L;
	
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VistaModificarUsuario() {
		setTitle("Modificar Usuario");
		setResizable(false);
		setBounds(100, 100, 352, 210);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		UsuarioView uv = AdmUsuarios.getInstancia().getLoggedUserView();
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(10, 11, 83, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 36, 83, 14);
		contentPane.add(lblNombre);
		
		JLabel lblDomicilio = new JLabel("Domicilio:");
		lblDomicilio.setBounds(10, 61, 83, 14);
		contentPane.add(lblDomicilio);
		
		JLabel lblEmail = new JLabel("E-Mail:");
		lblEmail.setBounds(10, 86, 83, 14);
		contentPane.add(lblEmail);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(10, 111, 83, 14);
		contentPane.add(lblContrasea);
		
		TextField textUsuario = new TextField();
		textUsuario.setBounds(95, 3, 247, 22);
		contentPane.add(textUsuario);
		
		TextField textNombre = new TextField();
		textNombre.setBounds(95, 28, 247, 22);
		contentPane.add(textNombre);
		
		TextField textDomicilio = new TextField();
		textDomicilio.setBounds(95, 53, 247, 22);
		contentPane.add(textDomicilio);
		
		TextField textMail = new TextField();
		textMail.setBounds(95, 78, 247, 22);
		contentPane.add(textMail);
		
		TextField textPassword = new TextField();
		textPassword.setBounds(95, 103, 247, 22);
		contentPane.add(textPassword);
		
		if (uv != null) {
			textUsuario.setText(uv.getNombreDeUsuario());
			textUsuario.setEnabled(false);
			textNombre.setText(uv.getNombre());
			textDomicilio.setText(uv.getDomicilio());
			textMail.setText(uv.getMail());
		}
		else {

		}
		
		JLabel lblMensaje = new JLabel("");
		lblMensaje.setBounds(10, 162, 332, 22);
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
						lblMensaje.setText("Info modificada. Contrase�a no.");
					}
					else if(resModificar > 0 & resPassRst == 0) {
						lblMensaje.setText("Info no modificada. Contrase�a si.");
					}
					else if(resModificar > 0 & resPassRst == -1) {
						lblMensaje.setText("No pudo modificarse la info.");
					}
					else if(resModificar > 0 & resPassRst > 0) {
						lblMensaje.setText("No pudo modificarse la info ni la contrase�a.");
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
