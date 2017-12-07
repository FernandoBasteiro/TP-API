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
	private ArrayList<Calificacion> calificacionesVendedor;
	private ArrayList<Calificacion> calificacionesComprador;
	
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
		//TODO LOW Cargar publicaciones de la DB! (Ver si se usa)
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
		this.calificacionesVendedor = null;
		this.calificacionesComprador = null;
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
	

	
	//TODO MID Todo lo que tiene que ver con calificaciones empieza aca. No hay nada codeado.
	public ArrayList<CalificacionView> getCalificacionesPendientesCompradorView() {
		calificacionesComprador = Calificacion.buscarCalificacionesComprador(this.nombreDeUsuario);
		ArrayList<CalificacionView> cp = new ArrayList<CalificacionView>();
		for (int i = 0; i < calificacionesComprador.size();i++) {
			if (calificacionesComprador.get(i).isPendiente()) {
				cp.add(calificacionesComprador.get(i).getView());
			}
		}
		return cp;
	}
	
	public ArrayList<CalificacionView> getCalificacionesPendientesVendedorView() {
		calificacionesVendedor = Calificacion.buscarCalificacionesVendedor(this.nombreDeUsuario);
		ArrayList<CalificacionView> cp = new ArrayList<CalificacionView>();
		for (int i = 0; i < calificacionesVendedor.size();i++) {
			if (calificacionesVendedor.get(i).isPendiente()) {
				cp.add(calificacionesVendedor.get(i).getView());
			}
		}
		return cp;
	}
	
	public ArrayList<CalificacionView> getCalificacionesCompletasCompradorView() {
		calificacionesComprador = Calificacion.buscarCalificacionesComprador(this.nombreDeUsuario);
		ArrayList<CalificacionView> cp = new ArrayList<CalificacionView>();
		for (int i = 0; i < calificacionesComprador.size();i++) {
			if (! calificacionesComprador.get(i).isPendiente()) {
				cp.add(calificacionesComprador.get(i).getView());
			}
		}
		return cp;
	}
	
	public ArrayList<CalificacionView> getCalificacionesCompletasVendedorView() {
		calificacionesVendedor = Calificacion.buscarCalificacionesVendedor(this.nombreDeUsuario);
		ArrayList<CalificacionView> cp = new ArrayList<CalificacionView>();
		for (int i = 0; i < calificacionesVendedor.size();i++) {
			if (! calificacionesVendedor.get(i).isPendiente()) {
				cp.add(calificacionesVendedor.get(i).getView());
			}
		}
		return cp;
	}
	
	private Calificacion buscarCalificacion(int nroCalificacion) {
		for (int i = 0; i < calificacionesVendedor.size(); i++){
			if (calificacionesVendedor.get(i).getNumero() == nroCalificacion) {
				return calificacionesVendedor.get(i);
			}
		}
		for (int i = 0; i < calificacionesComprador.size(); i++){
			if (calificacionesComprador.get(i).getNumero() == nroCalificacion) {
				return calificacionesComprador.get(i);
			}
		}
		return null;
	}
	
	public int crearCalificacion(Venta v, boolean esVendedor) {
		Calificacion c = new Calificacion(v, esVendedor);
		if (esVendedor) {
			calificacionesVendedor.add(c);
		}
		else {
			calificacionesComprador.add(c);
		}
		return 0;
	}
	
	public int setCalificacion(int nroCalificacion, int valorCalificacion, String comentarioCalificacion) {
		Calificacion c = buscarCalificacion(nroCalificacion);
		if (c != null) {
			return c.setCalificacion(valorCalificacion, comentarioCalificacion);
		}
		return -1;
	}
	//Fin de la parte de calificaciones
}
