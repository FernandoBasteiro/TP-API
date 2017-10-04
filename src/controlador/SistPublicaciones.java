package controlador;

import java.time.LocalDateTime;
import java.util.ArrayList;

import modelo.CompraInmediata;
import modelo.Publicacion;
import modelo.Subasta;

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
	
	public ArrayList<Publicacion> buscarPublicaciones(String publicacionBuscada) {
		//TODO Probablemente deba traer un array de publicacionesView, no las Publicaciones en si. 
		// Y las publicaciones seguramente tengan que tener algun numero de publicacion, que sea un Static para evitar duplicados.
		// La otra opcion es guardar una lista de publicaciones actuales, que son las que se le pasan a la vista en forma de publicacionView,
		//para poder encontrar la publicacion seleccionada desde la vista.
		//TODO Hacer el Select en la DB -> Traer todos los usuarios que esten vendiendo el producto buscado con sus respectivas publicaciones, etc.
		ArrayList<Publicacion> ps = new ArrayList<Publicacion>();
		for (int i = 0; i < publicaciones.size(); i++) {
			if (publicaciones.get(i).sosBuscado(publicacionBuscada)) {
				ps.add(publicaciones.get(i));
			}
		}
		return ps;
	}

}
