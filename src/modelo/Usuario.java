package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controlador.MovCtaCteView;
import controlador.UsuarioView;
import persistencia.AdmPersistenciaUsuario;

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
	private boolean activo;  // Estado del usuario
	
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
		
		AdmPersistenciaUsuario.getInstancia().insertUsuario(this);
	}
	
	public Usuario(String nombre, String domicilio, String mail,
			String nombreDeUsuario, String passwordString, LocalDateTime fechaCreacion, ArrayList<Publicacion> publicaciones) {
		super();
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.mail = mail;
		this.nombreDeUsuario = nombreDeUsuario;
		this.ultimaModificacion = LocalDateTime.now();
		this.ctacte = new CtaCte();
		this.password = new Password(passwordString);
		this.fechaCreacion = fechaCreacion;
		this.publicaciones = publicaciones;
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
		return AdmPersistenciaUsuario.getInstancia().buscarUsuario(nombreDeUsuario);
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
		return this.publicaciones;
	}
	
	public int agregarPublicacion(Publicacion p) {
		if(this.publicaciones == null) {
			publicaciones = new ArrayList<Publicacion>();
		}
		publicaciones.add(p);
		return 0;
	}
}
