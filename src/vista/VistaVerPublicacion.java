package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.PublicacionView;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VistaVerPublicacion extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private JTextField txtPrecio;
	private JTextField txtTipoPublicacion;
	private JTextField txtFinSubasta;
	private JTextField txtStock;


	public VistaVerPublicacion(PublicacionView publicacion) {
		setResizable(false);
		setBounds(100, 100, 310, 165);
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
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(145, 33, 150, 20);
		txtDescripcion.setText(publicacion.getDescripcion());
		contentPane.add(txtDescripcion);
		txtDescripcion.setColumns(10);
		
		txtPrecio = new JTextField();
		txtPrecio.setBounds(145, 58, 150, 20);
		txtPrecio.setText(String.valueOf(publicacion.getPrecioActual()));
		contentPane.add(txtPrecio);
		txtPrecio.setColumns(10);
		
		txtTipoPublicacion = new JTextField();
		txtTipoPublicacion.setBounds(145, 83, 150, 20);
		txtTipoPublicacion.setText(publicacion.getTipoPublicacion());
		contentPane.add(txtTipoPublicacion);
		txtTipoPublicacion.setColumns(10);
		
		if (publicacion.getTipoPublicacion().equals("Subasta")) {
			JLabel lblFinDeSubasta = new JLabel("Fin de Subasta");
			lblFinDeSubasta.setBounds(10, 111, 125, 14);
			contentPane.add(lblFinDeSubasta);
			
			txtFinSubasta = new JTextField();
			txtFinSubasta.setBounds(145, 108, 150, 20);
			txtFinSubasta.setText(publicacion.getFechaHasta().toString());
			contentPane.add(txtFinSubasta);
			txtFinSubasta.setColumns(10);
		}
		else if (publicacion.getTipoPublicacion().equals("Compra Inmediata")) {
			JLabel lblStockDisponible = new JLabel("Stock disponible:");
			lblStockDisponible.setBounds(10, 111, 125, 14);
			contentPane.add(lblStockDisponible);
			
			txtStock = new JTextField();
			txtStock.setBounds(145, 108, 150, 20);
			txtStock.setText(String.valueOf(publicacion.getStock()));
			contentPane.add(txtStock);
			txtStock.setColumns(10);
		}
		
		
		
	}

}
