package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import controlador.SistemaVentas;
import modelo.MovCtaCte;
import modelo.Venta;

public class AdmPersistenciaMovCtaCte {
	static private AdmPersistenciaMovCtaCte instancia;
	
	private AdmPersistenciaMovCtaCte() {
		
	}
	
	public static AdmPersistenciaMovCtaCte getInstancia(){
		if (instancia == null) {
			instancia = new AdmPersistenciaMovCtaCte();
		}
		return instancia;
	}
	
	public int insert(String nombreDeUsuario, MovCtaCte m) {
		try {
			Connection con = PoolConnection.getPoolConnection().getConnection();
			String sql = "INSERT INTO movCtaCte (nombreDeUsuario, monto, concepto, nroVenta, fechaMovimiento)VALUES (?,?,?,?,?)";
			PreparedStatement s = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setString(1, nombreDeUsuario);
			s.setFloat(2, m.getMonto());
			s.setString(3, m.getConcepto());
			s.setInt(4, m.getVenta().getNroVenta());
			s.setTimestamp(5, Timestamp.valueOf(m.getFechaMovimiento()));
			s.execute();
			
			ResultSet rs = s.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return 0;
		}
	}
	
	public ArrayList<MovCtaCte> buscarMovimientos(String nombreDeUsuario) {
		ArrayList<MovCtaCte> movimientos = new ArrayList<MovCtaCte>();
		try {
			Connection con = PoolConnection.getPoolConnection().getConnection();
			String sql = "SELECT * FROM movCtaCte WHERE nombreDeUsuario = ?)";
			PreparedStatement s = con.prepareStatement(sql);
			s.setString(1, nombreDeUsuario);
			ResultSet rs = s.executeQuery();
			MovCtaCte m;
			while (rs.next()){
				int nroMovimiento = rs.getInt("nroMovimiento");
				float monto = rs.getFloat("monto");
				String concepto = rs.getString("concepto");
				int nroVenta = rs.getInt("nroVenta");
				Venta v = SistemaVentas.getInstancia().buscarVenta(nroVenta);
				LocalDateTime fechaMovimiento = rs.getTimestamp("fechaMovimiento").toLocalDateTime();
				m = new MovCtaCte(nroMovimiento, v, monto, concepto, fechaMovimiento);
				movimientos.add(m);
			}
			return movimientos;

		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return null;
		}
	}
	
}
