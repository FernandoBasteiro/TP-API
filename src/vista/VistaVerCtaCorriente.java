package vista;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controlador.AdmUsuarios;
import controlador.MovCtaCteView;

public class VistaVerCtaCorriente extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8055099091938307831L;
	
	private JPanel contentPane;
	static private VistaVerCtaCorriente instancia;
	private ArrayList<MovCtaCteView> movimientos;
	private JLabel lblEstadoCuentaCorriente;
	
	private JTable tablaMovimientos;
	private DefaultTableModel tableModel;

	public static VistaVerCtaCorriente getInstancia() {
		if (instancia == null) {
			instancia = new VistaVerCtaCorriente();
		}
		return instancia;
	}
	
	private VistaVerCtaCorriente() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				cargarCtaCorriente();
				//cargarDatosUsuario();
			}
		});
		setResizable(false);
		setBounds(100, 100, 445, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] cols = {"Fecha","Concepto","Producto","Cantidad","Monto"};
		tableModel = new DefaultTableModel(cols, 0){
			/**
			 * 
			 */
			private static final long serialVersionUID = 9203474116659865484L;

			public boolean isCellEditable(int row, int column) {
			       return false;
			    }
		};
		tablaMovimientos = new JTable(tableModel);
		tablaMovimientos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable tabla = (JTable)e.getSource();
				if (e.getClickCount() == 2) {
//					VistaVerPublicacion.getInstancia(movimientos.get(tabla.getSelectedRow())).setVisible(true);
				}
			}
		});
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		tablaMovimientos.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
//		tablaMovimientos.getColumnModel().getColumn(1).setCellRenderer();
//		tablaMovimientos.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tablaMovimientos.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tablaMovimientos.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		
		JScrollPane scrollPane = new JScrollPane(tablaMovimientos);
		scrollPane.setVisible(true);
	    scrollPane.setBounds(6, 6, 433, 259);
	    contentPane.add(scrollPane);
		
		lblEstadoCuentaCorriente = new JLabel("");
		lblEstadoCuentaCorriente.setHorizontalAlignment(SwingConstants.LEFT);
		lblEstadoCuentaCorriente.setBounds(6, 277, 227, 20);
		contentPane.add(lblEstadoCuentaCorriente);
	}
	/*
	private void cargarDatosUsuario(){
		UsuarioLogueadoView vul = AdmUsuarios.getInstancia().getVistaUsuarioLogueado();
		//lblEstadoCuentaCorriente.setText("Estado Cuenta Corriente: " + String.valueOf(vul.getEstadoCtaCte()));
	}
	*/
	private void cargarCtaCorriente () {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String gFecha;
		String gConcepto;
		String gProducto;
		String gCantidad;
		String gMonto;
		float totalCtaCte = 0;
		
		movimientos = AdmUsuarios.getInstancia().getMovsCtaCteView();
		tableModel.setRowCount(0);
		if (movimientos != null && movimientos.size() > 0) {
			for (int i = 0; i < movimientos.size(); i++) {
				if (movimientos.get(i).getFechaCompra()==null)
					gFecha=" ";
				else
					gFecha=movimientos.get(i).getFechaCompra().format(formatter);
				gConcepto=movimientos.get(i).getConcepto();
				gProducto=movimientos.get(i).getNombreProducto();
				gCantidad=String.format("%3d", movimientos.get(i).getCantidad());
				totalCtaCte = totalCtaCte + movimientos.get(i).getMonto();
				gMonto=String.format("%.2f", movimientos.get(i).getMonto());
				Object[] rowData = {
						gFecha,
						gConcepto,
						gProducto,
						gCantidad,
						gMonto
						};
				tableModel.addRow(rowData);
			}
		}
		lblEstadoCuentaCorriente.setText("Estado Cuenta Corriente: " + totalCtaCte);
		
	}
}
