//Update for GIT

package vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MenuVistas extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9144541504916134590L;
	
	private JPanel contentPane;

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
		btnModificarUsuario.setBounds(10, 45, 125, 23);
		contentPane.add(btnModificarUsuario);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(10, 79, 125, 23);
		contentPane.add(btnLogin);
		
		JButton btnCargarMovimiento = new JButton("Cargar Movimiento");
		btnCargarMovimiento.setBounds(10, 113, 125, 23);
		contentPane.add(btnCargarMovimiento);
		
		JButton btnVerMovimientos = new JButton("Ver Movimientos");
		btnVerMovimientos.setBounds(10, 147, 125, 23);
		contentPane.add(btnVerMovimientos);
	}
}
