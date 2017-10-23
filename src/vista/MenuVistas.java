//Update for GIT

package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.AdmUsuarios;
import controlador.PublicacionView;
import controlador.SistPublicaciones;
import controlador.UsuarioLogueadoView;

public class MenuVistas extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9144541504916134590L;
	
	static private MenuVistas instancia;
	private JPanel contentPane;
	private JTextField txtBuscar;
	private ObjetoTablaPublicaciones TablaBuscarPublic;
	private JTable table;
	private JLabel lblUsuarioLogueado;
	private JLabel lblCalificacionesPendientes;
	private JLabel lblEstadoCuentaCorriente;

	static public MenuVistas getInstancia() {
		if (instancia == null) {
			instancia = new MenuVistas();
		}
		return instancia;
	}
	
	/**
	 * Create the frame.
	 */
	private MenuVistas() {
		TablaBuscarPublic=new ObjetoTablaPublicaciones();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				cargarDatosUsuario();
			}
		});
		
		setTitle("Sistema de compras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 671, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnUsuarios = new JMenu("Usuarios");
		menuBar.add(mnUsuarios);
		
		JMenuItem mntmModificarInformacion = new JMenuItem("Modificar informacion");
		mntmModificarInformacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaModificarUsuario vmu = new VistaModificarUsuario();
				vmu.setVisible(true);
			}
		});
		mnUsuarios.add(mntmModificarInformacion);
		
		JMenu mnPublicaciones = new JMenu("Publicaciones");
		menuBar.add(mnPublicaciones);
		
		JMenuItem mntmCreaPublicacion = new JMenuItem("Crear publicacion");
		mntmCreaPublicacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaCrearPublicacion.getInstancia().setVisible(true);
			}
		});
		mnPublicaciones.add(mntmCreaPublicacion);
		
		JMenuItem mntmVerMisPublicaciones = new JMenuItem("Ver mis publicaciones");
		mntmVerMisPublicaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaVerPublicaciones.getInstancia("").setVisible(true);
			}
		});
		mnPublicaciones.add(mntmVerMisPublicaciones);
		
		JLabel lblBuscarPublicacion = new JLabel("Buscar Publicacion:");
		lblBuscarPublicacion.setBounds(6, 10, 138, 14);
		contentPane.add(lblBuscarPublicacion);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(156, 7, 99, 20);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuVistas.this.actualizarTablaPublicaciones(MenuVistas.this.txtBuscar.getText());
			}
		});
		btnBuscar.setBounds(266, 6, 89, 23);
		contentPane.add(btnBuscar);
		
	    table = new JTable(TablaBuscarPublic);
	    table.getColumnModel().getColumn(5).setWidth(0);
	    table.getColumnModel().getColumn(5).setMinWidth(0);
	    table.getColumnModel().getColumn(5).setMaxWidth(0);
	    table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				try {
					if (evt.getClickCount() == 2) {
						JTable tabla = (JTable)evt.getSource();
						VistaVerPublicacion.getInstancia(SistPublicaciones.getInstancia().buscarPublicacion(Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 5).toString())).getPublicacionView()).setVisible(true);
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(6, 36, 659, 388);
		contentPane.add(scrollPane);
		
		lblUsuarioLogueado = new JLabel("");
		lblUsuarioLogueado.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsuarioLogueado.setBounds(6, 436, 199, 20);
		contentPane.add(lblUsuarioLogueado);
		
		lblCalificacionesPendientes = new JLabel("");
		lblCalificacionesPendientes.setHorizontalAlignment(SwingConstants.LEFT);
		lblCalificacionesPendientes.setBounds(227, 436, 199, 20);
		contentPane.add(lblCalificacionesPendientes);
		
		lblEstadoCuentaCorriente = new JLabel("");
		lblEstadoCuentaCorriente.setHorizontalAlignment(SwingConstants.LEFT);
		lblEstadoCuentaCorriente.setBounds(438, 436, 227, 20);
		contentPane.add(lblEstadoCuentaCorriente);
		
		JButton btnTodo = new JButton("Buscar todo");
		btnTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuVistas.this.actualizarTablaPublicaciones("%");
			}
		});
		btnTodo.setBounds(367, 4, 117, 29);
		contentPane.add(btnTodo);
	}

	private void cargarDatosUsuario(){
		UsuarioLogueadoView vul = AdmUsuarios.getInstancia().getVistaUsuarioLogueado();
		lblUsuarioLogueado.setText(vul.getNombre());
		lblCalificacionesPendientes.setText("Calificaciones Pendientes (" + String.valueOf(vul.getCalificacionesPendientes()) + ")");
		lblEstadoCuentaCorriente.setText("Estado Cuenta Corriente: " + String.valueOf(vul.getEstadoCtaCte()));
	}

	private void actualizarTablaPublicaciones(String buscado){
		this.TablaBuscarPublic.remAll();
		if(buscado.compareTo("")!=0) {
			ArrayList<PublicacionView> pubPersistidas= SistPublicaciones.getInstancia().buscarPublicaciones(buscado);
			if(pubPersistidas.size()>0)
				for (PublicacionView publicacion : pubPersistidas) {
					this.TablaBuscarPublic.addRow(publicacion.getNumPublicacion(),
												 publicacion.getTipoPublicacion(),
												 publicacion.getNombreProducto(),
												 publicacion.getDescripcion(),
												 publicacion.getPrecioActual(),
												 publicacion.getEstadoPublicacion()
													);
			this.repaint();
			}
		}
	}
}
