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

import controlador.PublicacionView;
import controlador.SistPublicaciones;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaVerPublicaciones extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8055099091938307831L;
	
	private JPanel contentPane;
	static private VistaVerPublicaciones instancia;
	static private String buscado;
	private ArrayList<PublicacionView> publicaciones;
	private JTextField textField;
	
	private JTable tablaPublicaciones;
	private DefaultTableModel tableModel;

	private VistaVerPublicaciones() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				cargarPublicaciones(buscado);
			}
		});
		setResizable(false);
		setBounds(100, 100, 445, 326);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("Buscar Publicacion:");
		label.setBounds(6, 7, 129, 20);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(147, 7, 129, 20);
		contentPane.add(textField);
		
		String[] cols = {"Nombre", "Precio", "Tipo"};
		tableModel = new DefaultTableModel(cols, 0){
			public boolean isCellEditable(int row, int column) {
			       return false;
			    }
		};
		tablaPublicaciones = new JTable(tableModel);
		tablaPublicaciones.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable tabla = (JTable)e.getSource();
				if (e.getClickCount() == 2) {
					VistaVerPublicacion.getInstancia(publicaciones.get(tabla.getSelectedRow())).setVisible(true);;
				}
			}
		});
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		tablaPublicaciones.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tablaPublicaciones.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		tablaPublicaciones.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		
		JScrollPane scrollPane = new JScrollPane(tablaPublicaciones);
		scrollPane.setVisible(true);
	    scrollPane.setBounds(6, 39, 433, 259);
	    contentPane.add(scrollPane);
		
		JButton button = new JButton("Buscar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (! textField.getText().isEmpty()) {
					buscado = textField.getText();
					cargarPublicaciones(buscado);
				}
				else {
					//TODO Mostrar error.
				}
			}
		});
		button.setBounds(299, 7, 89, 23);
		contentPane.add(button);
	}
	
	public static VistaVerPublicaciones getInstancia(String stringBuscado) {
		buscado = stringBuscado;
		if (instancia == null) {
			instancia = new VistaVerPublicaciones();
		}
		return instancia;
	}
	
	private void cargarPublicaciones (String buscado) {
		if (buscado.equals("")) {
			publicaciones = SistPublicaciones.getInstancia().verMisPublicaciones();
		}
		else {
			publicaciones = SistPublicaciones.getInstancia().buscarPublicaciones(buscado);
		}
		textField.setText(buscado);
		tableModel.setRowCount(0);
		if (publicaciones != null && publicaciones.size() > 0) {
			for (int i = 0; i < publicaciones.size(); i++) {
				Object[] rowData = {
						publicaciones.get(i).getNombreProducto(),
						String.format("%.2f", publicaciones.get(i).getPrecioActual()),
						publicaciones.get(i).getTipoPublicacion()
						};
				tableModel.addRow(rowData);
			}
		}
	}
}
