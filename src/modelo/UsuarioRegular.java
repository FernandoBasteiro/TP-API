package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controlador.CalificacionView;
import controlador.MovCtaCteView;
import controlador.UsuarioLogueadoView;
import controlador.UsuarioView;
import persistencia.AdmPersistenciaUsuarioMySQL;

public class UsuarioRegular extends Usuario {
	private String nombre;
	private String domicilio;
	private String mail;
	private CtaCte ctacte;
	private ArrayList<Publicacion> publicaciones;
	private ArrayList<Calificacion> calificacionesVendedor;
	private ArrayList<Calificacion> calificacionesComprador;
	private ArrayList<Mensaje> mensajes;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public CtaCte getCtacte() {
		return ctacte;
	}
	public void setCtacte(CtaCte ctacte) {
		this.ctacte = ctacte;
	}
	public ArrayList<Publicacion> getPublicaciones() {
		//TODO Cargar publicaciones de la DB!
		return publicaciones;
	}
	public void setPublicaciones(ArrayList<Publicacion> publicaciones) {
		this.publicaciones = publicaciones;
	}
	public ArrayList<Calificacion> getCalificacionesVendedor() {
		return calificacionesVendedor;
	}
	public void setCalificacionesVendedor(
			ArrayList<Calificacion> calificacionesVendedor) {
		this.calificacionesVendedor = calificacionesVendedor;
	}
	public ArrayList<Calificacion> getCalificacionesComprador() {
		return calificacionesComprador;
	}
	public void setCalificacionesComprador(
			ArrayList<Calificacion> calificacionesComprador) {
		this.calificacionesComprador = calificacionesComprador;
	}
	public ArrayList<Mensaje> getMensajes() {
		return mensajes;
	}
	public void setMensajes(ArrayList<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
	public UsuarioRegular(String nombre, String domicilio, String mail,
			String nombreDeUsuario, String passwordString) {
		super(nombreDeUsuario, passwordString);
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.mail = mail;
		this.ctacte = new CtaCte();
		this.publicaciones = null;
		this.calificacionesVendedor = null;
		this.calificacionesComprador = null;
		this.mensajes = null;
		AdmPersistenciaUsuarioMySQL.getInstancia().insertUsuario(this);
	}
	
	public UsuarioRegular(String nombre, String domicilio, String mail,
			String nombreDeUsuario, Password password, CtaCte ctacte,
			LocalDateTime fechaCreacion, ArrayList<Calificacion> calificacionesVendedor, 
			ArrayList<Calificacion> calificacionesComprador, boolean activo) {
		super(nombreDeUsuario, password, fechaCreacion, activo);
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.mail = mail;
		this.ctacte = ctacte;
	}
	
	public int cargarMovimiento(Venta venta, float monto, String concepto) {
		ctacte.generarMovimiento(nombreDeUsuario, venta, monto, concepto);
		updateUsuarioDB(this);
		return 0;
	}
	
	public ArrayList<MovCtaCteView> getMovimientos(){
		return ctacte.getMovsCtaCteView(nombreDeUsuario);
	}

	public UsuarioView getUsuarioView() {
		return (new UsuarioView(this.nombre, this.domicilio, this.mail, this.nombreDeUsuario, this.activo));
	}
	
	
	public int agregarPublicacion(Publicacion p) {
		if(this.publicaciones == null) {
			publicaciones = new ArrayList<Publicacion>();
		}
		publicaciones.add(p);
		return 0;
	}
	

	
	//TODO Todo lo que tiene que ver con calificaciones empieza aca. No hay nada codeado.
	public int calificacionesPendientes(){
		// Deberia revisar en todas los objetos "venta" del usuario, osea sus compras, y en todas las ventas de sus publicaciones, osea sus ventas, las calificaciones pendietnes.
		return 0;
	}
	
	public ArrayList<CalificacionView> getCalificacionesPendientesCompradorView() {
		return null;
	}
	
	public ArrayList<CalificacionView> getCalificacionesPendientesVendedorView() {
		return null;
	}
	
	private Calificacion buscarCalificacion(int nroCalificacion) {
		return null;
	}
	
	public int setCalificacion(int nroCalificacion, int valorCalificacion, String comentarioCalificacion) {
		Calificacion c = buscarCalificacion(nroCalificacion);
		if (c != null) {
			return c.setCalificacion(valorCalificacion, comentarioCalificacion);
		}
		return -1;
	}
	//Fin de la parte de calificaciones
	
	public float getSaldoCtaCte() {
		return ctacte.getSaldoTotal();
	}
	
	public UsuarioLogueadoView getUsuarioLogueadoView() {
		return (new UsuarioLogueadoView(this.nombre, this.getCtacte().getSaldoTotal(), this.calificacionesPendientes()));
	}
}
