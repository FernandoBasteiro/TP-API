package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.PublicacionView;
import controlador.SistPublicaciones;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class VistaVerPublicacion extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private JTextField txtPrecio;
	private JTextField txtTipoPublicacion;
	private JTextField txtFinSubasta;
	private JTextField txtStock;
	private JLabel lblCantidad;
	private JLabel lblOferta;
	private JTextField txtCantidad;
	private JTextField txtOferta;
	private JButton btnComprar;
	private JComboBox comboBox;


	public VistaVerPublicacion(PublicacionView publicacion) {
		setResizable(false);
		setBounds(100, 100, 310, 251);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombreDeProducto = new JLabel("Nombre de Producto:");
		lblNombreDeProducto.setBounds(10, 11, 125, 14);
		contentPane.add(lblNombreDeProducto);
		
		JLabel lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(10, 36, 125, 14);
		contentPane.add(lblDescripcion);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(10, 61, 125, 14);
		contentPane.add(lblPrecio);
		
		JLabel lblTipoDePublicacion = new JLabel("Tipo de publicacion:");
		lblTipoDePublicacion.setBounds(10, 86, 125, 14);
		contentPane.add(lblTipoDePublicacion);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(145, 8, 150, 20);
		txtNombre.setText(publicacion.getNombreProducto());
		txtNombre.setEditable(false);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(145, 33, 150, 20);
		txtDescripcion.setText(publicacion.getDescripcion());
		txtDescripcion.setEditable(false);
		contentPane.add(txtDescripcion);
		txtDescripcion.setColumns(10);
		
		txtPrecio = new JTextField();
		txtPrecio.setBounds(145, 58, 150, 20);
		txtPrecio.setText(String.valueOf(publicacion.getPrecioActual()));
		txtPrecio.setEditable(false);
		contentPane.add(txtPrecio);
		txtPrecio.setColumns(10);
		
		txtTipoPublicacion = new JTextField();
		txtTipoPublicacion.setBounds(145, 83, 150, 20);
		txtTipoPublicacion.setText(publicacion.getTipoPublicacion());
		txtTipoPublicacion.setEditable(false);
		contentPane.add(txtTipoPublicacion);
		txtTipoPublicacion.setColumns(10);
		
		btnComprar = new JButton("Comprar"); //TODO El texto quizas deberia cambiar segun si es subasta o compra inmediata.
		btnComprar.setBounds(10, 194, 285, 23);
		contentPane.add(btnComprar);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Efectivo", "Transferencia Bancaria", "Tarjeta de Credito (MercadoPago)"}));
		comboBox.setBounds(10, 163, 285, 20);
		contentPane.add(comboBox);

		if (publicacion.getTipoPublicacion().equals("Subasta")) {
			JLabel lblFinDeSubasta = new JLabel("Fin de Subasta");
			lblFinDeSubasta.setBounds(10, 111, 125, 14);
			contentPane.add(lblFinDeSubasta);
			
			txtFinSubasta = new JTextField();
			txtFinSubasta.setBounds(145, 108, 150, 20);
			txtFinSubasta.setText(publicacion.getFechaHasta().toString()); //TODO Formato de la fecha.
			txtFinSubasta.setEditable(false);
			contentPane.add(txtFinSubasta);
			txtFinSubasta.setColumns(10);
			
			lblOferta = new JLabel("Oferta:");
			lblOferta.setBounds(10, 136, 125, 14);
			contentPane.add(lblOferta);
			
			txtOferta = new JTextField();
			txtOferta.setBounds(145, 133, 150, 20);
			contentPane.add(txtOferta);
			txtOferta.setColumns(10);
			
			btnComprar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SistPublicaciones.getInstancia().hacerOferta(publicacion.getNumPublicacion(), Float.valueOf(txtOferta.getText()), 1, comboBox.getSelectedItem().toString());
				}
			});
		}
		else if (publicacion.getTipoPublicacion().equals("Compra Inmediata")) {
			
			JLabel lblStockDisponible = new JLabel("Stock disponible:");
			lblStockDisponible.setBounds(10, 111, 125, 14);
			contentPane.add(lblStockDisponible);
			
			txtStock = new JTextField();
			txtStock.setBounds(145, 108, 150, 20);
			txtStock.setText(String.valueOf(publicacion.getStock()));
			txtStock.setEditable(false);
			contentPane.add(txtStock);
			txtStock.setColumns(10);
			
			lblCantidad = new JLabel("Cantidad:");
			lblCantidad.setBounds(10, 136, 125, 14);
			contentPane.add(lblCantidad);
			
			txtCantidad = new JTextField();
			txtCantidad.setBounds(145, 133, 150, 20);
			contentPane.add(txtCantidad);
			txtCantidad.setColumns(10);
			
			btnComprar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SistPublicaciones.getInstancia().hacerOferta(publicacion.getNumPublicacion(), 0, Integer.valueOf(txtCantidad.getText()), comboBox.getSelectedItem().toString());
				}
			});
		}
		
		

		
	}

}
