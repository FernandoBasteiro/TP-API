package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controlador.AdmUsuarios;
import controlador.CalificacionView;

public class VistaVerCalificacionesPend extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8055099091938307831L;
	
	private JPanel contentPane;
	static private VistaVerCalificacionesPend instancia;
	static private String buscado;
	private ArrayList<CalificacionView> calificaciones;
	private JTextField textField;
	private JSpinner spnCalificacion;
	
	private JTable tablaCalificaciones;
	private DefaultTableModel tableModel;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private VistaVerCalificacionesPend() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				cargarCalificaciones();
			}
		});
		setResizable(false);
		setBounds(100, 100, 445, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblComentario = new JLabel("Comentario");
		lblComentario.setBounds(6, 309, 129, 20);
		contentPane.add(lblComentario);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(6, 341, 433, 69);
		contentPane.add(textField);
		
		String[] cols = {"Numero", "Fecha", "Vendedor","Producto"};
		tableModel = new DefaultTableModel(cols, 0){
			public boolean isCellEditable(int row, int column) {
			       return false;
			    }
		};
		tablaCalificaciones = new JTable(tableModel);
//		tablaPublicaciones.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				JTable tabla = (JTable)e.getSource();
//				if (e.getClickCount() == 2) {
//					VistaVerCalificacionesPend.getInstancia(calificaciones.get(tabla.getSelectedRow())).setVisible(true);;
//				}
//			}
//		});
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		tablaCalificaciones.getColumnModel().getColumn(0).setWidth(0);
		tablaCalificaciones.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		tablaCalificaciones.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tablaCalificaciones.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		
		JScrollPane scrollPane = new JScrollPane(tablaCalificaciones);
		scrollPane.setVisible(true);
	    scrollPane.setBounds(6, 38, 433, 259);
	    contentPane.add(scrollPane);
		
		JButton btnCalificar = new JButton("Calificar");
		btnCalificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				if (! textField.getText().isEmpty()) {
				String comentario;
				if(VistaVerCalificacionesPend.this.textField.getText().compareTo("")==0)
					comentario=" ";
				else
					comentario=VistaVerCalificacionesPend.this.textField.getText();
					int cal=(int) spnCalificacion.getValue();
					AdmUsuarios.getInstancia().setCalificacion(calificaciones.get(tablaCalificaciones.getSelectedRow()).getNumero(),cal, comentario);
					cargarCalificaciones();
//				}
//				else {
//					//TODO Mostrar error.
//				}
			}
		});
		btnCalificar.setBounds(350, 309, 89, 23);
		contentPane.add(btnCalificar);
		
		JLabel lblCalificacionesPendientes = new JLabel("Calificaciones pendientes");
		lblCalificacionesPendientes.setBounds(6, 6, 170, 20);
		contentPane.add(lblCalificacionesPendientes);
		
		JLabel lblCalificacion = new JLabel("Calificación");
		lblCalificacion.setBounds(192, 311, 89, 16);
		contentPane.add(lblCalificacion);
		
//	    SpinnerNumberModel m_numberSpinnerModel;
//	    Double current = new Double(5);
//	    Double min = new Double(1);
//	    Double max = new Double(10);
//	    Double step = new Double(1);
//	    SpinnerNumberModel m_numberSpinnerModel = new SpinnerNumberModel(current, min, max, step);
	    SpinnerNumberModel m_numberSpinnerModel = new SpinnerNumberModel(5, 1, 10, 1);
	    spnCalificacion = new JSpinner(m_numberSpinnerModel);
		spnCalificacion.setBounds(293, 306, 53, 26);

		contentPane.add(spnCalificacion);
	}
	
	public static VistaVerCalificacionesPend getInstancia(String stringBuscado) {
		buscado = stringBuscado;
		if (instancia == null) {
			instancia = new VistaVerCalificacionesPend();
		}
		return instancia;
	}
	
	private void cargarCalificaciones () {
//		if (buscado.equals("")) {
			calificaciones = AdmUsuarios.getInstancia().getCalificacionesPendientesView();
//		}
//		else {
//			publicaciones = SistPublicaciones.getInstancia().buscarPublicaciones(buscado);
//		}
		tableModel.setRowCount(0);
		if (calificaciones != null && calificaciones.size() > 0) {
			for (int i = 0; i < calificaciones.size(); i++) {
				Object[] rowData = {
						calificaciones.get(i).getNumero(),
						calificaciones.get(i).getFechaVenta().format(formatter),
						calificaciones.get(i).getNombreVendedor(),
						calificaciones.get(i).getProductoComprado()
						};
				tableModel.addRow(rowData);
			}
		}
	}
}
