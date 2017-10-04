package controlador;

import java.time.LocalDateTime;
import java.util.ArrayList;

import modelo.CompraInmediata;
import modelo.Publicacion;
import modelo.Subasta;
import modelo.Usuario;

public class SistPublicaciones {
	private ArrayList<Publicacion> publicaciones;
	private Publicacion publicacionActual;
	static private SistPublicaciones instancia;
	
	private SistPublicaciones(){
		publicaciones = new ArrayList<Publicacion>();
		publicacionActual = null;
	}
	
	static public SistPublicaciones getInstancia() {
		if (instancia == null) {
			instancia = new SistPublicaciones();
		}
		return instancia;
	}
	
	public int crearSubasta(String nombreDeProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, LocalDateTime fechaHasta) {
		Subasta s = new Subasta(nombreDeProducto, descripcion, imagenes, precioPublicado, fechaHasta);
		publicaciones.add(s);
		AdmUsuarios.getInstancia().agregarPublicacionAUsuario(s);
		return 0;
	}
	
	public int crearCompraInmediata(String nombreDeProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, int stock) {
		CompraInmediata ci = new CompraInmediata(nombreDeProducto, descripcion, imagenes, precioPublicado, stock);
		publicaciones.add(ci);
		AdmUsuarios.getInstancia().agregarPublicacionAUsuario(ci);
		return 0;
	}
	
	public ArrayList<PublicacionView> buscarPublicaciones(String publicacionBuscada) {

		//TODO Hacer el Select en la DB -> Traer todos los usuarios que esten vendiendo el producto buscado con sus respectivas publicaciones, etc.
		
		ArrayList<PublicacionView> pv = new ArrayList<PublicacionView>();
		for (int i = 0; i < publicaciones.size(); i++) {
			if (publicaciones.get(i).sosBuscado(publicacionBuscada)) {
				pv.add(publicaciones.get(i).getPublicacionView());
			}
		}
		return pv;
	}
	
	public ArrayList<PublicacionView> buscarPublicaciones(Usuario u) {
		if (u != null) {
			ArrayList<PublicacionView> pv = new ArrayList<PublicacionView>();
			for (int i = 0; i < u.getPublicaciones().size(); i++) {
				pv.add(u.getPublicaciones().get(i).getPublicacionView());
			}	
			return pv;
		}
		return null;
	}
	
	public Publicacion buscarPublicacion(int numeroPublicacion) {
		for (int i = 0; i < publicaciones.size(); i++) {
			if (publicaciones.get(i).sosBuscado(numeroPublicacion)) {
				return publicaciones.get(i);
			}
		}
		//TODO Buscar en base de datos la publicacion -> Depende de como queramos trabajar, puede llegar a ser al pedo hacer esto.
		return null;
	}
	
	public ArrayList<PublicacionView> verMisPublicaciones() {
		return this.buscarPublicaciones(AdmUsuarios.getInstancia().getUsuarioLogueado());
	}
	
}
