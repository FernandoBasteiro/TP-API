package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.AdmUsuarios;
import controlador.SistemaVentas;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaParametrosGrl extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5850130168373878777L;
	
	private JPanel contentPane;
	private JTextField txtVencPass;
	private JTextField txtComision;
	
	static private VistaParametrosGrl instancia;

	static public VistaParametrosGrl getInstancia() {
		if (instancia == null) {
			instancia = new VistaParametrosGrl();
		}
		return instancia;
	}

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VistaParametrosGrl frame = new VistaParametrosGrl();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	private VistaParametrosGrl() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 334, 182);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblVencPass = new JLabel("VENCIMIENTO DE PASSWORD");
		lblVencPass.setBounds(6, 6, 196, 26);
		contentPane.add(lblVencPass);
		
		txtVencPass = new JTextField();
		txtVencPass.setBounds(198, 6, 130, 26);
		contentPane.add(txtVencPass);
		txtVencPass.setColumns(10);
		txtVencPass.setText(AdmUsuarios.getInstancia().cargarExpiracionPass());
		
		JLabel lblComision = new JLabel("COMISION");
		lblComision.setBounds(6, 44, 180, 26);
		contentPane.add(lblComision);
		
		txtComision = new JTextField();
		txtComision.setColumns(10);
		txtComision.setBounds(198, 44, 130, 26);
		contentPane.add(txtComision);
		txtComision.setText(SistemaVentas.getInstancia().cargarPorcentajeComision());
		
		JLabel lblMensaje = new JLabel(" ");
		lblMensaje.setBounds(6, 82, 322, 26);
		contentPane.add(lblMensaje);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int error1 = AdmUsuarios.getInstancia().guardarExpiracionPass(txtVencPass.getText());
				int error2 = SistemaVentas.getInstancia().guardarPorcentajeComision(txtComision.getText());
				if (error1+error2==0)
					lblMensaje.setText("Modificacion correcta.");
				else
					lblMensaje.setText("Error al modifica.");
			}
		});
		btnModificar.setBounds(6, 120, 117, 29);
		contentPane.add(btnModificar);
	}
}
