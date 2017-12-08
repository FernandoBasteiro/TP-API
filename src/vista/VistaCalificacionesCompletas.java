package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.AdmUsuarios;
import controlador.CalificacionView;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VistaCalificacionesCompletas extends JFrame {

	private JPanel contentPane;
	private JTextField txtProm;


	public VistaCalificacionesCompletas(String nombreDeUsuario) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 460, 447);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ArrayList<CalificacionView> cv;
		if (nombreDeUsuario.equals("")) {
			cv = AdmUsuarios.getInstancia().getCalificacionesView();
		}
		else {
			cv = AdmUsuarios.getInstancia().getCalificacionesView(nombreDeUsuario);
		}
		
		String[] cols = {"Fecha", "Producto", "Puntuacion"};
		DefaultTableModel tableModel = new DefaultTableModel(cols, 0){
			public boolean isCellEditable(int row, int column) {
			       return false;
			    }
		};
		
		JTextArea txtrAsd = new JTextArea();
		txtrAsd.setEditable(false);
	    txtrAsd.setBounds(10, 334, 433, 73);
	    contentPane.add(txtrAsd);
	    
		JTable tablaCalficaciones  = new JTable(tableModel);
		tablaCalficaciones.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				txtrAsd.setText(cv.get(tablaCalficaciones.getSelectedRow()).getComentario());
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(tablaCalficaciones);
		scrollPane.setVisible(true);
	    scrollPane.setBounds(10, 11, 433, 259);
	    contentPane.add(scrollPane);
	    
	    JLabel lblComentarios = new JLabel("Comentarios:");
	    lblComentarios.setBounds(10, 309, 433, 14);
	    contentPane.add(lblComentarios);
	    
	    JLabel lblPromedio = new JLabel("Promedio:");
	    lblPromedio.setBounds(10, 284, 120, 14);
	    contentPane.add(lblPromedio);
	    
	    txtProm = new JTextField();
	    txtProm.setEditable(false);
	    txtProm.setBounds(140, 281, 45, 20);
	    txtProm.setText("0");
	    contentPane.add(txtProm);
	    txtProm.setColumns(10);
	    
	    
		
		int suma = 0;
		if (cv != null) {
			for (int i = 0; i < cv.size(); i++) {
				suma = suma + cv.get(i).getPuntuacion();
				Object[] rowData = {
							cv.get(i).getFechaCalificacion(),
							cv.get(i).getProductoComprado(),
							cv.get(i).getPuntuacion()
						};
				tableModel.addRow(rowData);
			}
			if (cv.size() > 0) {
				txtProm.setText(String.format("%.2g%n",(float) suma / cv.size()));
			}
		}
	}
}
