package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controlador.CalificacionView;
import controlador.MovCtaCteView;
import controlador.UsuarioLogueadoView;
import controlador.UsuarioView;
import persistencia.AdmPersistenciaUsuario;
import persistencia.AdmPersistenciaUsuarioMySQL;

public class Usuario {
	private String nombre;
	private String domicilio;
	private String mail;
	private String nombreDeUsuario;
	private LocalDateTime ultimaModificacion; //Este dato no se persiste. Es solo en memoria para evaluar si se debe mantener o si se puede liberar.
	private CtaCte ctacte;
	private Password password;
	private LocalDateTime fechaCreacion;
	private ArrayList<Publicacion> publicaciones;
	
	public String getNombre() {
		return nombre;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public String getMail() {
		return mail;
	}

	public CtaCte getCtacte() {
		return ctacte;
	}

	public Password getPassword() {
		return password;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public ArrayList<Calificacion> getCalificacionesVendedor() {
		return calificacionesVendedor;
	}

	public ArrayList<Calificacion> getCalificacionesComprador() {
		return calificacionesComprador;
	}

	public void setNombreDeUsuario(String nombreDeUsuario) {
		this.nombreDeUsuario = nombreDeUsuario;
	}

	public void setPublicaciones(ArrayList<Publicacion> publicaciones) {
		this.publicaciones = publicaciones;
	}

	private boolean activo;  // Estado del usuario
	private ArrayList<Calificacion> calificacionesVendedor;
	private ArrayList<Calificacion> calificacionesComprador;
	private ArrayList<Mensaje> mensajes;
	
	
	public Usuario(String nombre, String domicilio, String mail,
			String nombreDeUsuario, String passwordString) {
		super();
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.mail = mail;
		this.nombreDeUsuario = nombreDeUsuario;
		this.ultimaModificacion = LocalDateTime.now();
		this.ctacte = new CtaCte();
		this.password = new Password(passwordString);
		this.fechaCreacion = LocalDateTime.now();
		this.publicaciones = null;
		this.activo = true; // Estado activo en cuanto se crea.
		this.calificacionesVendedor = null;
		this.calificacionesComprador = null;
		this.mensajes = null;
		
		AdmPersistenciaUsuarioMySQL.getInstancia().insertUsuario(this);
	}
	
	public Usuario(String nombre, String domicilio, String mail,
			String nombreDeUsuario, Password password, CtaCte ctacte,
			LocalDateTime fechaCreacion, ArrayList<Calificacion> calificacionesVendedor, 
			ArrayList<Calificacion> calificacionesComprador) {
		super();
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.mail = mail;
		this.nombreDeUsuario = nombreDeUsuario;
		this.ultimaModificacion = LocalDateTime.now();
		this.ctacte = new CtaCte();
		this.password = password;
		this.fechaCreacion = fechaCreacion;
	}
	
	public boolean sosUsuario (String nombreDeUsuario) {
		return (this.nombreDeUsuario.equals(nombreDeUsuario));
	}
	
	public boolean passwordCorrecta (String passwordString) {
		return (this.password.passwordCorrecta(passwordString));
	}
	
	public boolean passwordVencida () {
		return (this.password.estaVencida());
	}

	public void cambiarPassword(String passwordString) {
		this.password.setPasswordString(passwordString);
	}

	public String getNombreDeUsuario() {
		return nombreDeUsuario;
	}
	
	static public Usuario buscarUsuarioDB(String nombreDeUsuario) {
		return AdmPersistenciaUsuarioMySQL.getInstancia().buscarUsuario(nombreDeUsuario);
	}
	
	public int cargarMovimiento(Venta venta, float monto, String concepto) {
		ctacte.generarMovimiento(venta, monto, concepto);
		return 0;
	}
	
	public ArrayList<MovCtaCteView> getMovimientos(){
		return ctacte.getMovsCtaCteView();
	}
	
	public UsuarioView getUsuarioView(){
		return (new UsuarioView(this.nombre, this.domicilio, this.mail, this.nombreDeUsuario));
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public ArrayList<Publicacion> getPublicaciones () {
		//TODO Cargar publicaciones de la DB!
		return this.publicaciones;
	}
	
	public int agregarPublicacion(Publicacion p) {
		if(this.publicaciones == null) {
			publicaciones = new ArrayList<Publicacion>();
		}
		publicaciones.add(p);
		return 0;
	}
	
	/* Esta funcion mostraria los mensajes no leidos solamente:
	public ArrayList<MensajeView> getMensajesNoLeidos {
		ArrayList<MensajeView> mensajes = new ArrayList<MensajeView>();
		for (Mensaje m : this.mensajes) {
			if (m.noLeido()) {
				mensajes.add(m.getView());
			}
		}
	}
	*/
	
	/* Esta funcion mostraria TODOS los mensajes del usuario:
	public ArrayList<MensajeView> getMensajes {
		---Primero el administrador de persistencia deberia cargar todos los mensajes del usuario en memoria--
		ArrayList<MensajeView> mensajes = new ArrayList<MensajeView>();
		for (Mensaje m : this.mensajes) {
			mensajes.add(m.getView());
		}
	}
	*/
	
	public int calificacionesPendientes(){
		int cantidad = 0;
		if (calificacionesVendedor != null) {
			for (Calificacion c : this.calificacionesVendedor) {
				if (c.pendiente()) {
					cantidad++;
				}
			}
		}
		if (calificacionesComprador != null) {
			for (Calificacion c : this.calificacionesComprador) {
				if (c.pendiente()) {
					cantidad++;
				}
			}
		}
		return cantidad;
	}
	
	public ArrayList<CalificacionView> getCalificacionesPendientesCompradorView() {
		ArrayList<CalificacionView> cv = new ArrayList<CalificacionView>();
		for (Calificacion c : this.calificacionesComprador) {
			if (c.pendiente()) {
				cv.add(c.getView());
			}
		}
		return cv;
	}
	
	public ArrayList<CalificacionView> getCalificacionesPendientesVendedorView() {
		ArrayList<CalificacionView> cv = new ArrayList<CalificacionView>();
		for (Calificacion c : this.calificacionesVendedor) {
			if (c.pendiente()) {
				cv.add(c.getView());
			}
		}
		return cv;
	}
	
	private Calificacion buscarCalificacion(int nroCalificacion) {
		for (Calificacion c : this.calificacionesVendedor) {
			if (c.sosCalificacion(nroCalificacion)) {
				return c;
			}
		}
		return null;
	}
	
	public int setCalificacion(int nroCalificacion, int valorCalificacion, String comentarioCalificacion) {
		Calificacion c = buscarCalificacion(nroCalificacion);
		int error = c.setCalificacion(valorCalificacion, comentarioCalificacion);
		return error;
	}
	
	static public int updateUsuarioDB(Usuario u) {
		return AdmPersistenciaUsuarioMySQL.getInstancia().updateUsuario(u);
	}
	
	public UsuarioLogueadoView getUsuarioLogueadoView() {
		return (new UsuarioLogueadoView(this.nombre, this.getCtacte().getSaldoTotal(), this.calificacionesPendientes()));
	}
}
