package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import controlador.PublicacionView;
import controlador.SistPublicaciones;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;

public class VistaVerPublicaciones extends JFrame {

	private JPanel contentPane;
	static private VistaVerPublicaciones instancia;
	private DefaultListModel<String> modelPublicaciones;
	private JList listPublicaciones;
	static private String buscado;
	private ArrayList<PublicacionView> publicaciones;

	//TODO La lista de publicaciones no se actualiza sola, por que el Array List no se actualiza. Hay que desarrollar alguna forma para que esta vista
	// le pida al SistPublicaciones un nuevo ArrayList cada vez que se focusea esta ventana, permitiendo asi recargar la lista.
	private VistaVerPublicaciones() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				cargarPublicaciones(buscado);
			}
		});
		setResizable(false);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//TODO Reducir el ArrayList y el ListModel a un solo objeto.
		// El ListModel deberia ser algo custom que muestre el nombre, el tipo de publicacion y el precio, por ejemplo.
		modelPublicaciones = new DefaultListModel<>();
		listPublicaciones = new JList(modelPublicaciones);
		listPublicaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPublicaciones.setBounds(10, 11, 424, 249);
		contentPane.add(listPublicaciones);
		
		//cargarPublicaciones(buscado);
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
		modelPublicaciones.clear();
		if (publicaciones != null) {
			for (int i = 0; i < publicaciones.size(); i++) {
				modelPublicaciones.addElement(publicaciones.get(i).getNombreProducto());
			}
			listPublicaciones.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					JList list = (JList)evt.getSource();
					if (evt.getClickCount() == 2) {
						VistaVerPublicacion.getInstancia(publicaciones.get(list.locationToIndex(evt.getPoint()))).setVisible(true);;
					}
				}
			});
		}
		else {
			modelPublicaciones.addElement("No se encontraron publicaciones");
		}
	}

}
