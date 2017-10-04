package controlador;

import java.time.LocalDateTime;
import java.util.ArrayList;

import modelo.CompraInmediata;
import modelo.Publicacion;
import modelo.Subasta;

public class SistPublicaciones {
	private ArrayList<Publicacion> publicaciones;
	private ArrayList<Publicacion> publicacionesActuales;
	static private SistPublicaciones instancia;
	
	private SistPublicaciones(){
		publicaciones = new ArrayList<Publicacion>();
		publicacionesActuales = null;
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
		publicacionesActuales = new ArrayList<Publicacion>();
		for (int i = 0; i < publicaciones.size(); i++) {
			if (publicaciones.get(i).sosBuscado(publicacionBuscada)) {
				publicacionesActuales.add(publicaciones.get(i));
			}
		}
		
		ArrayList<PublicacionView> pv = new ArrayList<PublicacionView>(); 
		//TODO Crear las publicaciones View con la informacion de las publicaciones que deban mostrarse en pantalla. 
		// Esto deberia ser un metodo abstracto, dado que Subasta y CompraInmediata van a traer informaciones distintas.
		for (int i = 0; i < publicacionesActuales.size(); i++) {
			pv.add(publicacionesActuales.get(i).getPublicacionView());
		}
		
		return pv;
	}

}
