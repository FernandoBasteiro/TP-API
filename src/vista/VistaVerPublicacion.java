package vista;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controlador.PublicacionView;
import controlador.SistPublicaciones;

import javax.swing.JSpinner;
import javax.swing.SwingConstants;

public class VistaVerPublicacion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8944599040814726836L;
	
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private JTextField txtPrecio;
	private JTextField txtTipoPublicacion;
	private JButton btnComprar;
	private JComboBox comboBox;
	
	//Objetos especificos de las subastas:
	private JLabel lblFinDeSubasta;
	private JTextField txtFinSubasta;
	private JLabel lblOferta;
	private JTextField txtOferta;
	
	//Objetos especificos de las compras inmediatas:
	private JLabel lblStockDisponible;
	private JTextField txtStock;
	private JLabel lblCantidad;
	private JTextField txtCantidad;
	
	static private VistaVerPublicacion instancia;
	private JLabel lblCbu;
	private JTextField txtCBU;
	private JLabel lblNumeroDeTarjeta;
	private JTextField txtNroTarjeta;
	private JButton btnConvertirASubasta;
	
	private JLabel lblImagen;
	private JSpinner spinner;
	private JButton btnVerCalificacion;
	
	private SpinnerNumberModel modelo;
	
	private String nombreDeVendedor;
	
	static public VistaVerPublicacion getInstancia(PublicacionView publicacion) {
		instancia = new VistaVerPublicacion(); //TODO Dado que no es mas un singleton, habria que dejar de tratarlo como tal.
		instancia.cargarDatos(publicacion);
		return instancia;
	}
	
	static public void setInstancia(VistaVerPublicacion i) {
		instancia = i;
	}
	

	private VistaVerPublicacion() {
		setResizable(false);
		setBounds(100, 100, 600, 348);
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
		txtNombre.setEditable(false);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(145, 33, 150, 20);
		txtDescripcion.setEditable(false);
		contentPane.add(txtDescripcion);
		txtDescripcion.setColumns(10);
		
		txtPrecio = new JTextField();
		txtPrecio.setBounds(145, 58, 150, 20);
		txtPrecio.setEditable(false);
		contentPane.add(txtPrecio);
		txtPrecio.setColumns(10);
		
		txtTipoPublicacion = new JTextField();
		txtTipoPublicacion.setBounds(145, 83, 150, 20);
		txtTipoPublicacion.setEditable(false);
		contentPane.add(txtTipoPublicacion);
		txtTipoPublicacion.setColumns(10);
		
		btnComprar = new JButton("Comprar"); //TODO El texto quizas deberia cambiar segun si es subasta o compra inmediata.
		btnComprar.setBounds(10, 217, 285, 23);
		contentPane.add(btnComprar);
		
		//Objetos de la subasta:
		lblFinDeSubasta = new JLabel("Fin de Subasta");
		lblFinDeSubasta.setBounds(10, 111, 125, 14);
		contentPane.add(lblFinDeSubasta);
		
		txtFinSubasta = new JTextField();
		txtFinSubasta.setBounds(145, 108, 150, 20);
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
		
		//Objetos de la compra Inmediata:
		lblStockDisponible = new JLabel("Stock disponible:");
		lblStockDisponible.setBounds(10, 111, 125, 14);
		contentPane.add(lblStockDisponible);
		
		txtStock = new JTextField();
		txtStock.setBounds(145, 108, 150, 20);
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
		
		lblCbu = new JLabel("CBU:");
		lblCbu.setBounds(10, 192, 125, 14);
		lblCbu.setVisible(false);
		contentPane.add(lblCbu);
		
		txtCBU = new JTextField();
		txtCBU.setBounds(145, 189, 150, 20);
		txtCBU.setVisible(false);
		contentPane.add(txtCBU);
		txtCBU.setColumns(10);
		
		lblNumeroDeTarjeta = new JLabel("Numero de Tarjeta:");
		lblNumeroDeTarjeta.setBounds(10, 194, 125, 14);
		lblNumeroDeTarjeta.setVisible(false);
		contentPane.add(lblNumeroDeTarjeta);
		
		txtNroTarjeta = new JTextField();
		txtNroTarjeta.setBounds(145, 189, 150, 20);
		txtNroTarjeta.setVisible(false);
		contentPane.add(txtNroTarjeta);
		txtNroTarjeta.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Efectivo", "Transferencia Bancaria", "MercadoPago"}));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBox.getSelectedItem().equals("MercadoPago")){
					lblCbu.setVisible(false);
					txtCBU.setVisible(false);
					lblNumeroDeTarjeta.setVisible(true);
					txtNroTarjeta.setVisible(true);
				}
				else if(comboBox.getSelectedItem().equals("Transferencia Bancaria")) {
					lblCbu.setVisible(true);
					txtCBU.setVisible(true);
					lblNumeroDeTarjeta.setVisible(false);
					txtNroTarjeta.setVisible(false);
				}
				else {
					lblCbu.setVisible(false);
					txtCBU.setVisible(false);
					lblNumeroDeTarjeta.setVisible(false);
					txtNroTarjeta.setVisible(false);
				}
			}
		});
		comboBox.setBounds(10, 161, 285, 20);
		contentPane.add(comboBox);
		
		btnConvertirASubasta = new JButton("Convertir a Subasta");
		btnConvertirASubasta.setBounds(10, 251, 285, 23);
		contentPane.add(btnConvertirASubasta);
		
		lblImagen = new JLabel();
		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagen.setBounds(305, 33, 275, 275);
		contentPane.add(lblImagen);
		
		JLabel lblImagenes = new JLabel("Imagenes:");
		lblImagenes.setHorizontalAlignment(SwingConstants.TRAILING);
		lblImagenes.setBounds(305, 11, 100, 14);
		contentPane.add(lblImagenes);
		
		btnVerCalificacion = new JButton("Ver Calificaciones Vendedor");
		btnVerCalificacion.setBounds(10, 285, 285, 23);
		contentPane.add(btnVerCalificacion);
		
		
	}
	
	private Image buscarImagen(String path) {
		try {
			URL url = new URL(path);
			BufferedImage imagenOriginal = ImageIO.read(url);

			
			int alto = imagenOriginal.getHeight();
			int ancho = imagenOriginal.getWidth();
			int nuevoAlto;
			int nuevoAncho;
			if (alto > ancho) {
				nuevoAlto = 275;
				nuevoAncho = nuevoAlto * ancho / alto;
			}
			else {
				nuevoAncho = 275;
				nuevoAlto = nuevoAncho * alto / ancho;
				
			}
			Image image = imagenOriginal.getScaledInstance(nuevoAncho, nuevoAlto, java.awt.Image.SCALE_SMOOTH);
			
			return image;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public void cargarDatos(PublicacionView publicacion){
		if (publicacion.getImagenes().size() > 0) {
			modelo = new SpinnerNumberModel(1, 1, publicacion.getImagenes().size(), 1);
			spinner = new JSpinner(modelo);
			spinner.setBounds(415, 8, 130, 20);
			spinner.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					try {
						Image imagen = buscarImagen(publicacion.getImagenes().get((Integer) spinner.getValue() -1));
						lblImagen.setIcon(new ImageIcon(imagen));
					}
					catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
			});
			contentPane.add(spinner);
		}
		try {
			Image imagen = buscarImagen(publicacion.getImagenes().get(0));
			lblImagen.setIcon(new ImageIcon(imagen));
		}
		catch (Exception e) {
			try {
				lblImagen.setIcon(new ImageIcon("nodisponible.png"));
			}
			catch (Exception e2) {
				System.err.println(e.getMessage());
				System.err.println(e2.getMessage());
			}
		}
		
		nombreDeVendedor = publicacion.getNombreVendedor();
		txtNombre.setText(publicacion.getNombreProducto());
		txtDescripcion.setText(publicacion.getDescripcion());
		txtPrecio.setText(String.valueOf(publicacion.getPrecioActual()));
		txtTipoPublicacion.setText(publicacion.getTipoPublicacion());
		if (publicacion.getSoyDueno() | publicacion.getEstadoPublicacion().equals("Finalizada") | publicacion.getStock() == 0) {
			btnComprar.setEnabled(false);
		}
		else {
			btnComprar.setEnabled(true);
		}
		if (publicacion.getTipoPublicacion().equals("Subasta")) {
			lblFinDeSubasta.setVisible(true);
			txtFinSubasta.setVisible(true);
			lblOferta.setVisible(true);
			txtOferta.setVisible(true);
			lblStockDisponible.setVisible(false);
			txtStock.setVisible(false);
			lblCantidad.setVisible(false);
			txtCantidad.setVisible(false);
			txtOferta.setText("");
			txtCantidad.setText("");
			btnConvertirASubasta.setEnabled(false);
			
			txtFinSubasta.setText(publicacion.getFechaHasta().toString()); //TODO Formato de la fecha.
			for (ActionListener al : btnComprar.getActionListeners()){
				btnComprar.removeActionListener(al);
			}
			btnComprar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String CBU = null;
					String nroTarjeta = null;
					if (comboBox.getSelectedItem().toString().equals("MercadoPago")) {
						nroTarjeta = txtNroTarjeta.getText();
					}
					if (comboBox.getSelectedItem().toString().equals("Transferencia Bancaria")) {
						CBU = txtCBU.getText();
					}
					int resultado = SistPublicaciones.getInstancia().hacerOferta(publicacion.getNumPublicacion(), Float.valueOf(txtOferta.getText()), 1, comboBox.getSelectedItem().toString(), nroTarjeta, CBU);
					instancia.setVisible(false);
					MenuVistas.getInstancia().cargarPublicaciones();
					VistaVerPublicacion.setInstancia(null);
				}
			});
		}
		else if (publicacion.getTipoPublicacion().equals("Compra Inmediata")) {
			lblFinDeSubasta.setVisible(false);
			txtFinSubasta.setVisible(false);
			lblOferta.setVisible(false);
			txtOferta.setVisible(false);
			lblStockDisponible.setVisible(true);
			txtStock.setVisible(true);
			lblCantidad.setVisible(true);
			txtCantidad.setVisible(true);
			txtOferta.setText("");
			txtCantidad.setText("");
			if (publicacion.getSoyDueno()) {
				btnConvertirASubasta.setEnabled(true);
			}
			else {
				btnConvertirASubasta.setEnabled(false);
			}
			
			txtStock.setText(String.valueOf(publicacion.getStock()));
			for (ActionListener al : btnComprar.getActionListeners()){
				btnComprar.removeActionListener(al);
			}
			
			btnConvertirASubasta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SistPublicaciones.getInstancia().transformarEnSubasta(publicacion.getNumPublicacion(), publicacion.getPrecioActual(), LocalDateTime.now().plusDays(10)); //TODO ESTO TIENE QUE ELEGIRLO EL USUARIO!!!!
				}
			});

			btnComprar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String CBU = null;
					String nroTarjeta = null;
					if (comboBox.getSelectedItem().toString().equals("MercadoPago")) {
						nroTarjeta = txtNroTarjeta.getText();
					}
					if (comboBox.getSelectedItem().toString().equals("Transferencia Bancaria")) {
						CBU = txtCBU.getText();
					}
					int resultado = SistPublicaciones.getInstancia().hacerOferta(publicacion.getNumPublicacion(), publicacion.getPrecioActual(), Integer.valueOf(txtCantidad.getText()), comboBox.getSelectedItem().toString(), nroTarjeta, CBU);
					instancia.setVisible(false);
					MenuVistas.getInstancia().cargarPublicaciones();
					VistaVerPublicacion.setInstancia(null);
				}
			});
		}	
		btnVerCalificacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaCalificacionesCompletas vcc = new VistaCalificacionesCompletas(nombreDeVendedor);
				vcc.setVisible(true);
			}
		});
	}
}
