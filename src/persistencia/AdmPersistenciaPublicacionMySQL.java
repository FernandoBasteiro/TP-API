package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;

import modelo.CompraInmediata;
import modelo.Publicacion;
import modelo.Subasta;

public class AdmPersistenciaPublicacionMySQL {
	static private AdmPersistenciaPublicacionMySQL instancia;
	
	static public AdmPersistenciaPublicacionMySQL getInstancia(){
		if (instancia == null) {
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
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO publicaciones (tipoPublicacion,nombreDeProducto,descripcion,fechaPublicacion,precioPublicado,estadoPublicacion,nombreDeUsuarioVendedor) VALUES ('s',?,?,?,?,?,?)");
			ps.setString(1, s.getPublicacionView().getNombreProducto());
			ps.setString(2, s.getPublicacionView().getDescripcion());
			ps.setTimestamp(3, Timestamp.valueOf(s.getPublicacionView().getFechaPublicacion()));
			ps.setFloat(4, s.getPublicacionView().getPrecioActual());
			ps.setString(5, s.getPublicacionView().getEstadoPublicacion());
			ps.setString(6, s.getVendedor().getNombreDeUsuario());
			ps.execute();
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return -1;
		}
		return 0;
	}
	
	public int insertPublicacion(CompraInmediata ci) {
		return 0;
	}
}
