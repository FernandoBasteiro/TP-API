package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import controlador.SistemaVentas;
import modelo.Calificacion;
import modelo.Venta;

public class AdmPersistenciaCalificacion {
	static private AdmPersistenciaCalificacion instancia;
	
	private AdmPersistenciaCalificacion() {
		
	}
	
	static public AdmPersistenciaCalificacion getInstancia() {
		if (instancia == null) {
			instancia = new AdmPersistenciaCalificacion();
		}
		return instancia;
	}
	
	public int insertCalificacion(Calificacion c) {
		try {
			Connection con = PoolConnection.getPoolConnection().getConnection();
			String sql = "INSERT INTO calificaciones (comprador, vendedor, pendiente, venta) VALUES (?,?,?,?)";
			PreparedStatement s = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setString(1, c.getVenta().getComprador().getNombreDeUsuario());
			s.setString(2, c.getVenta().getPublicacion().getVendedor().getNombreDeUsuario());
			s.setBoolean(3, true);
			s.setInt(4, c.getVenta().getNroVenta());
			s.execute();
			ResultSet rs = s.getGeneratedKeys();
			rs.next();
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return rs.getInt(1);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	public int updateCalificacion(Calificacion c) {
		try {
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("UPDATE calificaciones SET puntuacion = ?, comentarios = ?, fechaCalificacion = ?, pendiente = ? WHERE numero = ?");
			s.setInt(1, c.getPuntuacion());
			s.setString(2, c.getComentario());
			s.setTimestamp(3, Timestamp.valueOf(c.getFechaCalificacion()));
			s.setBoolean(4, false);
			s.setInt(5, c.getNumero());
			s.execute();
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return 0;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
	}
	
	public ArrayList<Calificacion> buscarCalificaciones (String nombreDeUsuario) {
		try {
			ArrayList<Calificacion> calificaciones = new ArrayList<Calificacion>();
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("SELECT * FROM calificaciones WHERE vendedor = ? AND pendiente = ?");
			s.setString(1, nombreDeUsuario);
			s.setBoolean(2, false);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				int numero = rs.getInt("numero");
				int puntuacion = rs.getInt("puntuacion");
				String comentarios = rs.getString("comentarios");
				Venta v = SistemaVentas.getInstancia().buscarVenta(rs.getInt("venta"));
				LocalDateTime fechaCalificacion = rs.getTimestamp("fechaCalificacion").toLocalDateTime();
				Calificacion c = new Calificacion(puntuacion, comentarios, fechaCalificacion, numero, v);
				calificaciones.add(c);
			}
			return calificaciones;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public ArrayList<Calificacion> buscarCalificacionesPendientes (String nombreDeUsuario) {
		try {
			ArrayList<Calificacion> calificaciones = new ArrayList<Calificacion>();
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("SELECT * FROM calificaciones WHERE comprador = ? AND pendiente = ?");
			s.setString(1, nombreDeUsuario);
			s.setBoolean(2, true);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				int numero = rs.getInt("numero");
				Venta v = SistemaVentas.getInstancia().buscarVenta(rs.getInt("venta"));
				Calificacion c = new Calificacion(numero, v);
				calificaciones.add(c);
			}
			return calificaciones;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}