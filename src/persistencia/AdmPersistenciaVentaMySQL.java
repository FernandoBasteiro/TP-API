package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import controlador.AdmUsuarios;
import controlador.SistPublicaciones;
import modelo.Efectivo;
import modelo.MercadoPago;
import modelo.Publicacion;
import modelo.TransfBancaria;
import modelo.Usuario;
import modelo.Venta;

public class AdmPersistenciaVentaMySQL {
	static private AdmPersistenciaVentaMySQL instancia;
	
	static public AdmPersistenciaVentaMySQL getInstancia(){
		if (instancia == null) {
			instancia = new AdmPersistenciaVentaMySQL();
		}
		return instancia;
	}
	
	private AdmPersistenciaVentaMySQL() {
	}
	
	public ArrayList<Venta> buscarCompras(String nombreDeUsuario) {
		ArrayList<Venta> ventas = new ArrayList<Venta>();
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("SELECT * FROM ventas WHERE nombreDeUsuarioComprador = ?");
			s.setString(1, nombreDeUsuario);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				int nroVenta = rs.getInt("nroVenta");
				String medioDePago = rs.getString("medioDePago");
				int cantidad = rs.getInt("cantidad");
				int nroPublicacion = rs.getInt("nroPublicacion");
				float montoUnitario = rs.getFloat("montoUnitario");
				float montoComision = rs.getFloat("montoComision");
				String estadoPago = rs.getString("estadoPago");
				LocalDateTime fechaDeCompra = rs.getTimestamp("fechaDeCompra").toLocalDateTime();
				
				Publicacion p = SistPublicaciones.getInstancia().buscarPublicacion(nroPublicacion);
				Usuario u = AdmUsuarios.getInstancia().buscarUsuario(nombreDeUsuario);
				Venta v = null;
				if (medioDePago.equals("Efectivo")) {
					v = new Efectivo(nroVenta, p, u, cantidad, montoUnitario, montoComision, estadoPago, fechaDeCompra);
				}
				else if(medioDePago.equals("MercadoPago")){
					String nroTarjeta = rs.getString("nroTarjeta");
					v = new MercadoPago(nroVenta, p, u, cantidad, montoUnitario, montoComision, estadoPago, fechaDeCompra, nroTarjeta);
					
				}
				else if(medioDePago.equals("TransfBancaria")) {
					String CBU = rs.getString("CBU");
					v = new TransfBancaria(nroVenta, p, u, cantidad, montoUnitario, montoComision, estadoPago, fechaDeCompra, CBU);
				}
				ventas.add(v);
			}
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
			return ventas;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return null;
		}
	}
	
	public ArrayList<Venta> buscarVentas(int nroPublicacion) {
		ArrayList<Venta> ventas = new ArrayList<Venta>();
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("SELECT * FROM ventas WHERE nroPublicacion = ?");
			s.setInt(1, nroPublicacion);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				int nroVenta = rs.getInt("nroVenta");
				String medioDePago = rs.getString("medioDePago");
				int cantidad = rs.getInt("cantidad");
				String nombreDeUsuario = rs.getString("nombreDeUsuarioComprador");
				float montoUnitario = rs.getFloat("montoUnitario");
				float montoComision = rs.getFloat("montoComision");
				String estadoPago = rs.getString("estadoPago");
				LocalDateTime fechaDeCompra = rs.getTimestamp("fechaDeCompra").toLocalDateTime();
				
				Publicacion p = SistPublicaciones.getInstancia().buscarPublicacion(nroPublicacion);
				Usuario u = AdmUsuarios.getInstancia().buscarUsuario(nombreDeUsuario);
				Venta v = null;
				if (medioDePago.equals("Efectivo")) {
					v = new Efectivo(nroVenta, p, u, cantidad, montoUnitario, montoComision, estadoPago, fechaDeCompra);
				}
				else if(medioDePago.equals("MercadoPago")){
					String nroTarjeta = rs.getString("nroTarjeta");
					v = new MercadoPago(nroVenta, p, u, cantidad, montoUnitario, montoComision, estadoPago, fechaDeCompra, nroTarjeta);
					
				}
				else if(medioDePago.equals("TransfBancaria")) {
					String CBU = rs.getString("CBU");
					v = new TransfBancaria(nroVenta, p, u, cantidad, montoUnitario, montoComision, estadoPago, fechaDeCompra, CBU);
				}
				ventas.add(v);
			}
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
			return ventas;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return null;
		}
	}
	
	public Venta buscarVenta(int nroVenta) {
		Venta v = null;
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("SELECT * FROM ventas WHERE nroVenta = ?");
			s.setInt(1, nroVenta);
			ResultSet rs = s.executeQuery();
			if (rs.next()){
				int nroPublicacion = rs.getInt("nroPublicacion");
				String medioDePago = rs.getString("medioDePago");
				int cantidad = rs.getInt("cantidad");
				String nombreDeUsuario = rs.getString("nombreDeUsuarioComprador");
				float montoUnitario = rs.getFloat("montoUnitario");
				float montoComision = rs.getFloat("montoComision");
				String estadoPago = rs.getString("estadoPago");
				LocalDateTime fechaDeCompra = rs.getTimestamp("fechaDeCompra").toLocalDateTime();
				
				Publicacion p = SistPublicaciones.getInstancia().buscarPublicacion(nroPublicacion);
				Usuario u = AdmUsuarios.getInstancia().buscarUsuario(nombreDeUsuario);
				if (medioDePago.equals("Efectivo")) {
					v = new Efectivo(nroVenta, p, u, cantidad, montoUnitario, montoComision, estadoPago, fechaDeCompra);
				}
				else if(medioDePago.equals("MercadoPago")){
					String nroTarjeta = rs.getString("nroTarjeta");
					v = new MercadoPago(nroVenta, p, u, cantidad, montoUnitario, montoComision, estadoPago, fechaDeCompra, nroTarjeta);
					
				}
				else if(medioDePago.equals("TransfBancaria")) {
					String CBU = rs.getString("CBU");
					v = new TransfBancaria(nroVenta, p, u, cantidad, montoUnitario, montoComision, estadoPago, fechaDeCompra, CBU);
				}
			}
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
			return v;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return null;
		}
	}
	
	public int insertarVenta(Efectivo v) {
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			String sql = "INSERT INTO ventas (medioDePago, cantidad, nroPublicacion, nombreDeUsuarioComprador, montoUnitario, montoComision, estadoPago, fechaDeCompra) VALUES (?,?,?,?,?,?,?,?)";
			PreparedStatement s = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setString(1, "Efectivo");
			s.setInt(2, v.getCantidad());
			s.setInt(3, v.getPublicacion().getNroPublicacion());
			s.setString(4, v.getComprador().getNombreDeUsuario());
			s.setFloat(5, v.getMontoUnitario());
			s.setFloat(6, v.getMontoComision());
			s.setString(7, v.getEstadoPago());
			s.setTimestamp(8, Timestamp.valueOf(v.getFechaDeCompra()));
			s.execute();
			
			ResultSet rs = s.getGeneratedKeys();
			rs.next();
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
			return rs.getInt(1);
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return 0;
		}
	}
	
	public int insertarVenta(MercadoPago v) {
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			String sql = "INSERT INTO ventas (medioDePago, cantidad, nroPublicacion, nombreDeUsuarioComprador, montoUnitario, montoComision, estadoPago, fechaDeCompra, nroTarjeta) VALUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement s = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setString(1, "MercadoPago");
			s.setInt(2, v.getCantidad());
			s.setInt(3, v.getPublicacion().getNroPublicacion());
			s.setString(4, v.getComprador().getNombreDeUsuario());
			s.setFloat(5, v.getMontoUnitario());
			s.setFloat(6, v.getMontoComision());
			s.setString(7, v.getEstadoPago());
			s.setTimestamp(8, Timestamp.valueOf(v.getFechaDeCompra()));
			s.setString(9, v.getNroTarjeta());
			s.execute();
			
			ResultSet rs = s.getGeneratedKeys();
			rs.next();
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
			return rs.getInt(1);
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return 0;
		}
	}

	public int insertarVenta(TransfBancaria v) {
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			String sql = "INSERT INTO ventas (medioDePago, cantidad, nroPublicacion, nombreDeUsuarioComprador, montoUnitario, montoComision, estadoPago, fechaDeCompra, CBU) VALUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement s = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setString(1, "TransfBancaria");
			s.setInt(2, v.getCantidad());
			s.setInt(3, v.getPublicacion().getNroPublicacion());
			s.setString(4, v.getComprador().getNombreDeUsuario());
			s.setFloat(5, v.getMontoUnitario());
			s.setFloat(6, v.getMontoComision());
			s.setString(7, v.getEstadoPago());
			s.setTimestamp(8, Timestamp.valueOf(v.getFechaDeCompra()));
			s.setString(9, v.getCBU());
			s.execute();
			
			ResultSet rs = s.getGeneratedKeys();
			rs.next();
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
			return rs.getInt(1);
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return 0;
		}
	}
	
	public int updateEstadoVenta(Venta v) {
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("UPDATE ventas SET estadoPago = ? WHERE nroVenta = ?");
			s.setString(1, v.getEstadoPago());
			s.setInt(2, v.getNroVenta());
			s.execute();
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
			return 0;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return 1;
		}
	}
}
