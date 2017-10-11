package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
			PreparedStatement ps = con.prepareStatement("INSERT INTO publicaciones (tipoPublicacion,nombreDeProducto,descripcion,fechaPublicacion,precioPublicado,estadoPublicacion,nombreDeUsuarioVendedor) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, s.getPublicacionView().getTipoPublicacion());
			ps.setString(2, s.getPublicacionView().getNombreProducto());
			ps.setString(3, s.getPublicacionView().getDescripcion());
			ps.setTimestamp(4, Timestamp.valueOf(s.getPublicacionView().getFechaPublicacion()));
			ps.setFloat(5, s.getPublicacionView().getPrecioActual());
			ps.setString(6, s.getPublicacionView().getEstadoPublicacion());
			ps.setString(7, s.getVendedor().getNombreDeUsuario());
			ps.executeUpdate();
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
			
			ResultSet rs=ps.getGeneratedKeys();
			rs.next();
			System.out.println("ID generado: " + rs.getInt(1));
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return -1;
		}
		return 0;
	}
	
	public int insertPublicacion(CompraInmediata ci) {
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO publicaciones (tipoPublicacion,nombreDeProducto,descripcion,fechaPublicacion,precioPublicado,estadoPublicacion,nombreDeUsuarioVendedor,stock) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, ci.getPublicacionView().getTipoPublicacion());
			ps.setString(2, ci.getPublicacionView().getNombreProducto());
			ps.setString(3, ci.getPublicacionView().getDescripcion());
			ps.setTimestamp(4, Timestamp.valueOf(ci.getPublicacionView().getFechaPublicacion()));
			ps.setFloat(5, ci.getPublicacionView().getPrecioActual());
			ps.setString(6, ci.getPublicacionView().getEstadoPublicacion());
			ps.setString(7, ci.getVendedor().getNombreDeUsuario());
			ps.setInt(8, ci.getPublicacionView().getStock());
			ps.executeUpdate();
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
			
			ResultSet rs=ps.getGeneratedKeys();
			rs.next();
			System.out.println("ID generado: " + rs.getInt(1));
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return -1;
		}
		return 0;
	}
}
