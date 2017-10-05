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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;

public class VistaVerPublicaciones extends JFrame {

	private JPanel contentPane;
	static private VistaVerPublicaciones instancia;
	private DefaultListModel<String> modelPublicaciones;
	private JList listPublicaciones;

	//TODO La lista de publicaciones no se actualiza sola, por que el Array List no se actualiza. Hay que desarrollar alguna forma para que esta vista
	// le pida al SistPublicaciones un nuevo ArrayList cada vez que se focusea esta ventana, permitiendo asi recargar la lista.
	private VistaVerPublicaciones(ArrayList<PublicacionView> publicaciones) {
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
		
		cargarPublicaciones(publicaciones);
	}
	
	public static VistaVerPublicaciones getInstancia(ArrayList<PublicacionView> publicaciones) {
		if (instancia == null) {
			instancia = new VistaVerPublicaciones(publicaciones);
		}
		return instancia;
	}
	
	private void cargarPublicaciones (ArrayList<PublicacionView> publicaciones) {
		if (publicaciones != null) {
			for (int i = 0; i < publicaciones.size(); i++) {
				modelPublicaciones.addElement(publicaciones.get(i).getNombreProducto());
			}
			listPublicaciones.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					JList list = (JList)evt.getSource();
					if (evt.getClickCount() == 2) {
						VistaVerPublicacion vvp = new VistaVerPublicacion(publicaciones.get(list.locationToIndex(evt.getPoint())));
						vvp.setVisible(true);
					}
				}
			});
		}
		else {
			modelPublicaciones.addElement("No se encontraron publicaciones");
		}
	}
}
