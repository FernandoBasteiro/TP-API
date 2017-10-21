package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import controlador.SistPublicaciones;

public class VistaCrearPublicacion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4409565389168575039L;
	
	private JPanel contentPane;
	private JTextField txtNombreProducto;
	private JTextField txtDescripcion;
	private JTextField txtPrecioPublicacion;
	private JTextField txtDuracionSubasta;
	private JTextField txtImagen;
	static private VistaCrearPublicacion instancia;
	
	static public VistaCrearPublicacion getInstancia(){
		if (instancia == null) {
			instancia = new VistaCrearPublicacion();
		}
		return instancia;
	}
	
	static public void setInstancia(VistaCrearPublicacion i){
		instancia = i;
	}


	private VistaCrearPublicacion() {
		setResizable(false);
		setTitle("Crear nueva publicacion");
		setBounds(100, 100, 508, 326);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombreDelProducto = new JLabel("Nombre del producto:");
		lblNombreDelProducto.setBounds(10, 11, 198, 14);
		contentPane.add(lblNombreDelProducto);
		
		JLabel lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(10, 36, 198, 14);
		contentPane.add(lblDescripcion);
		
		JLabel lblPrecioDePublicacion = new JLabel("Precio de publicacion:");
		lblPrecioDePublicacion.setBounds(10, 86, 198, 14);
		contentPane.add(lblPrecioDePublicacion);
		
		JLabel lblTipoDePublicacion = new JLabel("Tipo de publicacion:");
		lblTipoDePublicacion.setBounds(10, 61, 198, 14);
		contentPane.add(lblTipoDePublicacion);
		
		txtNombreProducto = new JTextField();
		txtNombreProducto.setBounds(218, 8, 275, 20);
		contentPane.add(txtNombreProducto);
		txtNombreProducto.setColumns(10);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(218, 33, 275, 20);
		contentPane.add(txtDescripcion);
		txtDescripcion.setColumns(10);
		
		txtPrecioPublicacion = new JTextField();
		txtPrecioPublicacion.setBounds(218, 83, 275, 20);
		contentPane.add(txtPrecioPublicacion);
		txtPrecioPublicacion.setColumns(10);
		
		JLabel lblDuracionSubastadas = new JLabel("Duracion Subasta (D\u00EDas):");
		lblDuracionSubastadas.setBounds(10, 111, 198, 14);
		contentPane.add(lblDuracionSubastadas);
		
		lblDuracionSubastadas.setVisible(false);
		
		txtDuracionSubasta = new JTextField();
		txtDuracionSubasta.setBounds(218, 108, 275, 20);
		contentPane.add(txtDuracionSubasta);
		txtDuracionSubasta.setColumns(10);
		
		txtDuracionSubasta.setVisible(false);
		
		JLabel lblStockDisponible = new JLabel("Stock disponible:");
		lblStockDisponible.setBounds(10, 111, 198, 14);
		contentPane.add(lblStockDisponible);
		SpinnerNumberModel modelo = new SpinnerNumberModel(1, -1, 999, 1);
		
		JSpinner spinnerStock = new JSpinner(modelo);
		spinnerStock.setBounds(218, 108, 275, 20);
		contentPane.add(spinnerStock);
		
		JLabel lblImagenesDelProducto = new JLabel("Imagenes del producto (URL):");
		lblImagenesDelProducto.setBounds(10, 136, 198, 14);
		contentPane.add(lblImagenesDelProducto);
		
		txtImagen = new JTextField();
		txtImagen.setBounds(218, 133, 176, 20);
		contentPane.add(txtImagen);
		txtImagen.setColumns(10);
		
		ArrayList<String> imagenes = new ArrayList<String>();
		DefaultListModel<String> modelImagenes = new DefaultListModel<>();
		JList list = new JList(modelImagenes);
		list.setBounds(10, 161, 483, 95);
		contentPane.add(list);
		//TODO Agregar alguna funcion para eliminar imagenes de la lista.
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imagenes.add(txtImagen.getText()); //TODO Buscar una forma de reducir el ListModel y el AarrayList a un solo objeto.
				modelImagenes.addElement(txtImagen.getText());
				txtImagen.setText("");
			}
		});
		btnAgregar.setBounds(404, 132, 89, 23);
		contentPane.add(btnAgregar);
		
		JComboBox comboBoxTipodePublicacion = new JComboBox();
		comboBoxTipodePublicacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBoxTipodePublicacion.getSelectedItem().equals("Subasta")) {
					txtDuracionSubasta.setVisible(true);
					lblDuracionSubastadas.setVisible(true);
					lblStockDisponible.setVisible(false);
					spinnerStock.setVisible(false);
				}
				else {
					txtDuracionSubasta.setVisible(false);
					lblDuracionSubastadas.setVisible(false);
					lblStockDisponible.setVisible(true);
					spinnerStock.setVisible(true);
				}
			}
		});
		comboBoxTipodePublicacion.setModel(new DefaultComboBoxModel(new String[] {"Compra Inmediata", "Subasta"}));
		comboBoxTipodePublicacion.setBounds(218, 58, 275, 20);
		contentPane.add(comboBoxTipodePublicacion);
		
		JButton btnCrearPublicacion = new JButton("Crear Publicacion");
		btnCrearPublicacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO Si todos los campos estan completos y son correctos.
				int resultado;
				//imagenes.add(txtImagen.getText()); //TODO Esto tiene que ser un loop que traiga las imagenes de la lista.
				
				if (comboBoxTipodePublicacion.getSelectedItem().equals("Subasta")) {
					resultado = SistPublicaciones.getInstancia().crearSubasta(txtNombreProducto.getText(), txtDescripcion.getText(), imagenes, Float.parseFloat(txtPrecioPublicacion.getText()), LocalDateTime.now().plusDays(Integer.parseInt(txtDuracionSubasta.getText())));
				}
				else {
					resultado = SistPublicaciones.getInstancia().crearCompraInmediata(txtNombreProducto.getText(), txtDescripcion.getText(), imagenes, Float.parseFloat(txtPrecioPublicacion.getText()), Integer.parseInt(spinnerStock.getValue().toString()));
				}
				switch (resultado) {
				case 0:
					VistaCrearPublicacion.getInstancia().setVisible(false);
					VistaCrearPublicacion.setInstancia(null);
				}
			}
		});
		btnCrearPublicacion.setBounds(10, 267, 483, 23);
		contentPane.add(btnCrearPublicacion);
	}
}
