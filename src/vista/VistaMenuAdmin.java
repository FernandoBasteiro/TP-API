package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaMenuAdmin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2551867938017704831L;
	
	private JPanel contentPane;
	private static VistaMenuAdmin instancia;
	
	public static VistaMenuAdmin getInstancia() {
		if (instancia == null) {
			instancia = new VistaMenuAdmin();
		}
		return instancia;
	}

	public VistaMenuAdmin() {
		setTitle("Admin View");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 203, 184);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaAltaUsuario.getInstancia().setVisible(true);
			}
		});
		btnCrearUsuario.setBounds(10, 6, 187, 23);
		contentPane.add(btnCrearUsuario);
		
		
		
		JButton btnModificarUsuario = new JButton("Modificar Usuario");
		btnModificarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					VistaModificarUsuario vmu = new VistaModificarUsuario();
					vmu.setVisible(true);
				}	
			
		});
		btnModificarUsuario.setBounds(10, 83, 187, 23);
		contentPane.add(btnModificarUsuario);
		
		
		JButton btnCrearAdmin = new JButton("Crear Admin");
		btnCrearAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VistaAltaAdmin.getInstancia().setVisible(true);
			}
		});
		btnCrearAdmin.setBounds(10, 41, 187, 29);
		contentPane.add(btnCrearAdmin);
		
		JButton btnModificarParametros = new JButton("Modificar Parametros");
		btnModificarParametros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnModificarParametros.setBounds(10, 118, 187, 23);
		contentPane.add(btnModificarParametros);
	}
}
