package persistencia;

import java.util.ArrayList;

import modelo.CompraInmediata;
import modelo.Publicacion;
import modelo.Subasta;

public class AdmPersistenciaPublicacionMySQL {
	static private AdmPersistenciaPublicacionMySQL instancia;
	
	static public AdmPersistenciaPublicacionMySQL getInstancia(){
		if (instancia != null) {
			instancia = new AdmPersistenciaPublicacionMySQL();
		}
		return instancia;
	}
	
	private AdmPersistenciaPublicacionMySQL() {
		
	}
	
	public ArrayList<Publicacion> buscarPublicacionesProducto(String nombreDeProducto) {
		return null;
	}
	
	public Publicacion buscarPublicacion(int nroPublicacion) {
		return null;
	}
	
	public ArrayList<Publicacion> buscarPublicacionesUsuario(String nombreDeUsuario) {
		return null;
	}
	
	public int updatePublicacion(int nroPublicacion) {
		return 0;
	}
	
	public int insertPublicacion(Subasta s) {
		//Las imagenes van en la tabla imagenesPublicaciones
		return 0;
	}
	
	public int insertPublicacion(CompraInmediata ci) {
		return 0;
	}
}
