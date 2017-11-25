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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

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
	static private String buscado;
	private JPanel contentPane;
	private JTextField txtBuscar;
	private JLabel lblUsuarioLogueado;
	private JLabel lblCalificacionesPendientes;
	
	private ArrayList<PublicacionView> publicaciones;
	private JTable tablaPublicaciones;
	private DefaultTableModel tableModel;
	

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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				cargarDatosUsuario();
			}
		});
		
		setTitle("Sistema de compras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 528, 508);
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
		
		JMenu mnCuenta = new JMenu("Cuenta");
		menuBar.add(mnCuenta);
		
		JMenuItem mntmVerCuentaCorriente = new JMenuItem("Ver Cuenta Corrientes");
		mntmVerCuentaCorriente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaVerCtaCorriente.getInstancia().setVisible(true);
			}
		});
		mnCuenta.add(mntmVerCuentaCorriente);
		
		JLabel lblBuscarPublicacion = new JLabel("Buscar Publicacion:");
		lblBuscarPublicacion.setBounds(6, 10, 138, 14);
		contentPane.add(lblBuscarPublicacion);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(156, 7, 283, 20);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		String[] cols = {"Nombre", "Precio", "Tipo"};
		tableModel = new DefaultTableModel(cols, 0){
			/**
			 * 
			 */
			private static final long serialVersionUID = 7277948369760956569L;

			public boolean isCellEditable(int row, int column) {
			       return false;
			    }
		};
		tablaPublicaciones = new JTable(tableModel);
		tablaPublicaciones.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable tabla = (JTable)e.getSource();
				if (e.getClickCount() == 2) {
					VistaVerPublicacion.getInstancia(publicaciones.get(tabla.getSelectedRow())).setVisible(true);
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
		
		JScrollPane scrollPane2 = new JScrollPane(tablaPublicaciones);
		scrollPane2.setVisible(true);
	    scrollPane2.setBounds(6, 36, 433, 388);
	    contentPane.add(scrollPane2);
		
		
		
		lblUsuarioLogueado = new JLabel("");
		lblUsuarioLogueado.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsuarioLogueado.setBounds(6, 436, 199, 20);
		contentPane.add(lblUsuarioLogueado);
		
		lblCalificacionesPendientes = new JLabel("");
		lblCalificacionesPendientes.setHorizontalAlignment(SwingConstants.LEFT);
		lblCalificacionesPendientes.setBounds(227, 436, 199, 20);
		contentPane.add(lblCalificacionesPendientes);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (! txtBuscar.getText().isEmpty()) {
					buscado = txtBuscar.getText();
					cargarPublicaciones(buscado);
				}
				else {
					//TODO Mostrar error.
				}
			}
		});
		btnBuscar.setBounds(438, 7, 89, 23);
		contentPane.add(btnBuscar);
	}

	private void cargarDatosUsuario(){
		UsuarioLogueadoView vul = AdmUsuarios.getInstancia().getVistaUsuarioLogueado();
		lblUsuarioLogueado.setText(vul.getNombre());
		lblCalificacionesPendientes.setText("Calificaciones Pendientes (" + String.valueOf(vul.getCalificacionesPendientes()) + ")");
	}

	private void cargarPublicaciones (String buscado) {
		if (buscado.equals("")) {
			publicaciones = SistPublicaciones.getInstancia().verMisPublicaciones();
		}
		else {
			publicaciones = SistPublicaciones.getInstancia().buscarPublicaciones(buscado);
		}
		txtBuscar.setText(buscado);
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
