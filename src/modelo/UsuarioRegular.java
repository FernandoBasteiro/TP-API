package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controlador.CalificacionView;
import controlador.MovCtaCteView;
import controlador.UsuarioView;
import persistencia.AdmPersistenciaCalificacion;
import persistencia.AdmPersistenciaUsuario;

public class UsuarioRegular extends Usuario {
	private String nombre;
	private String domicilio;
	private String mail;
	private CtaCte ctacte;
	private ArrayList<Publicacion> publicaciones;
	private ArrayList<Calificacion> calificacionesRecibidas;
	private ArrayList<Calificacion> calificacionesPendientes;
	
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
		return publicaciones;
	}
	public void setPublicaciones(ArrayList<Publicacion> publicaciones) {
		this.publicaciones = publicaciones;
	}
	
	public UsuarioRegular(String nombre, String domicilio, String mail,
			String nombreDeUsuario, String passwordString) {
		super(nombreDeUsuario, passwordString);
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.mail = mail;
		this.ctacte = new CtaCte();
		this.publicaciones = null;
		this.calificacionesRecibidas = new ArrayList<Calificacion>();
		this.calificacionesPendientes = new ArrayList<Calificacion>();
		AdmPersistenciaUsuario.getInstancia().insertUsuario(this);
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
	

	
	//Todo lo que tiene que ver con calificaciones empieza aca.
	public ArrayList<CalificacionView> getCalificacionesView() {
		calificacionesRecibidas = Calificacion.buscarCalificaciones(this.nombreDeUsuario);
		ArrayList<CalificacionView> cp = new ArrayList<CalificacionView>();
		if(calificacionesRecibidas!=null)
			for (int i = 0; i < calificacionesRecibidas.size();i++) {
					cp.add(calificacionesRecibidas.get(i).getView());
			}
		return cp;
	}
	
	public ArrayList<CalificacionView> getCalificacionesPendientesView() {
		calificacionesPendientes = Calificacion.buscarCalificacionesPendientes(this.nombreDeUsuario);
		ArrayList<CalificacionView> cp = new ArrayList<CalificacionView>();
		if(calificacionesPendientes!=null)
			for (int i = 0; i < calificacionesPendientes.size();i++) {
				cp.add(calificacionesPendientes.get(i).getView());
			}
		return cp;
	}
	
	private Calificacion buscarCalificacionPendiente(int nroCalificacion) {
		for (int i = 0; i < calificacionesPendientes.size(); i++){
			if (calificacionesPendientes.get(i).getNumero() == nroCalificacion) {
				return calificacionesPendientes.get(i);
			}
		}
		return null;
	}
	
	public int crearCalificacion(Venta v) {
		Calificacion c = new Calificacion(v);
		if(calificacionesRecibidas==null)
			this.calificacionesRecibidas = new ArrayList<Calificacion>();
		calificacionesRecibidas.add(c);
		UsuarioRegular ur = (UsuarioRegular)v.getComprador();
		ur.agregarCalificacionPendiente(c);
		return 0;
	}
	
	public int setCalificacion(int nroCalificacion, int valorCalificacion, String comentarioCalificacion) {
		Calificacion c = buscarCalificacionPendiente(nroCalificacion);
		if (c != null) {
			return c.setCalificacion(valorCalificacion, comentarioCalificacion);
		}
		return 2;
	}
	
	public int agregarCalificacionPendiente(Calificacion c) {
		if(calificacionesPendientes==null)
			this.calificacionesPendientes = new ArrayList<Calificacion>();
		calificacionesPendientes.add(c);
		return  0;
	}
	//Fin de la parte de calificaciones
	
	public ArrayList<MovCtaCteView> getComisionesPagadas() {
		return ctacte.getComisionesPagadasView(nombreDeUsuario);
	}
}
