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
		if (AdmUsuarios.getInstancia().getUsuarioLogueado() != null) {
			Subasta s = new Subasta(nombreDeProducto, descripcion, imagenes, precioPublicado, fechaHasta, AdmUsuarios.getInstancia().getUsuarioLogueado());
			publicaciones.add(s);
			AdmUsuarios.getInstancia().agregarPublicacionAUsuario(s);
			return 0;
		}
		return 1;
	}
	
	public int crearCompraInmediata(String nombreDeProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, int stock) {
		if (AdmUsuarios.getInstancia().getUsuarioLogueado() != null) {
			CompraInmediata ci = new CompraInmediata(nombreDeProducto, descripcion, imagenes, precioPublicado, stock, AdmUsuarios.getInstancia().getUsuarioLogueado());
			publicaciones.add(ci);
			AdmUsuarios.getInstancia().agregarPublicacionAUsuario(ci);
			return 0;
		}
		return 1;
	}
	
	public ArrayList<PublicacionView> buscarPublicaciones(String publicacionBuscada) {

		//TODO Hacer el Select en la DB -> Traer todos los usuarios que esten vendiendo el producto buscado con sus respectivas publicaciones, etc.
		
		//TODO Esto ahora es Case sensitive. Arreglarlo para que ignore el case.
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
			if (AdmUsuarios.getInstancia().getPublicacionesUsuario(u) != null) {
				ArrayList<PublicacionView> pv = new ArrayList<PublicacionView>();
				for (int i = 0; i < AdmUsuarios.getInstancia().getPublicacionesUsuario(u).size(); i++) {
					pv.add(AdmUsuarios.getInstancia().getPublicacionesUsuario(u).get(i).getPublicacionView());
				}
				return pv;
			}
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
	
	public int hacerOferta(int nroPublicacion, float monto, int cantidad, String medioDePago) {
		//COMENTARIOS PARA FER: Verifica si la instancia es una subasta o una compra inmediata y en base a eso se llama a los metodos especificos de cada clase
		if (AdmUsuarios.getInstancia().getUsuarioLogueado() != null) {
			Publicacion pub = buscarPublicacion(nroPublicacion);
			int resultado = -1;
			if(pub instanceof Subasta) {
				resultado = ((Subasta) pub).ofertar(monto, AdmUsuarios.getInstancia().getUsuarioLogueado(), medioDePago);
			}
			else {
				resultado =  ((CompraInmediata) pub).comprar(cantidad, AdmUsuarios.getInstancia().getUsuarioLogueado(), medioDePago);
			}
						
			if (resultado == 0) {
				//TODO Realizar venta
			}
			return resultado;
		}
		return -1;
	}
	
}
