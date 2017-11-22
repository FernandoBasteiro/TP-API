package vista;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controlador.AdmUsuarios;
import controlador.MovCtaCteView;
import controlador.PublicacionView;
import controlador.SistPublicaciones;
import controlador.SistemaVentas;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class VistaVerCtaCorriente extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8055099091938307831L;
	
	private JPanel contentPane;
	static private VistaVerCtaCorriente instancia;
	static private String buscado;
	private ArrayList<MovCtaCteView> movimientos;
	private JTextField textField;
	
	private JTable tablaMovimientos;
	private DefaultTableModel tableModel;

	public static VistaVerCtaCorriente getInstancia(String stringBuscado) {
		buscado = stringBuscado;
		if (instancia == null) {
			instancia = new VistaVerCtaCorriente();
		}
		return instancia;
	}
	
	private VistaVerCtaCorriente() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				cargarCtaCorriente(buscado);
			}
		});
		setResizable(false);
		setBounds(100, 100, 445, 326);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDiasDeMovimientos = new JLabel("Dias de movimientos:");
		lblDiasDeMovimientos.setBounds(6, 7, 191, 20);
		contentPane.add(lblDiasDeMovimientos);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setText("15");
		textField.setColumns(10);
		textField.setBounds(209, 7, 62, 20);
		contentPane.add(textField);
		
		String[] cols = {"Concepto", "Monto"};
		tableModel = new DefaultTableModel(cols, 0){
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
		tablaMovimientos.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		
		JScrollPane scrollPane = new JScrollPane(tablaMovimientos);
		scrollPane.setVisible(true);
	    scrollPane.setBounds(6, 39, 433, 259);
	    contentPane.add(scrollPane);
		
		JButton button = new JButton("Buscar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (! textField.getText().isEmpty()) {
					buscado = textField.getText();
					cargarCtaCorriente(buscado);
				}
				else {
					//TODO Mostrar error.
				}
			}
		});
		button.setBounds(350, 7, 89, 23);
		contentPane.add(button);
	}
	
	private void cargarCtaCorriente (String buscado) {
		movimientos = AdmUsuarios.getInstancia().getMovsCtaCteView();
		textField.setText(buscado);
		tableModel.setRowCount(0);
		if (movimientos != null && movimientos.size() > 0) {
			for (int i = 0; i < movimientos.size(); i++) {
				Object[] rowData = {
						movimientos.get(i).getConcepto(),
						String.format("%.2f", movimientos.get(i).getMonto())
						};
				tableModel.addRow(rowData);
			}
		}
	}
}
