package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import controlador.AdmUsuarios;
import modelo.Admin;
import modelo.Calificacion;
import modelo.CtaCte;
import modelo.Oferta;
import modelo.Password;
import modelo.Usuario;
import modelo.UsuarioRegular;

public class AdmPersistenciaOfertasMySQL {
	static private AdmPersistenciaOfertasMySQL instancia;
	
	static public AdmPersistenciaOfertasMySQL getInstancia() {
		if (instancia == null) {
			instancia = new AdmPersistenciaOfertasMySQL();
		}
		return instancia;
	}
	
	private AdmPersistenciaOfertasMySQL() {
		
	}

	public Oferta getMayorOferta(int nroPublicacion) {
		Oferta o = null;
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("SELECT * FROM ofertas JOIN (SELECT Max(monto) OfertaMaxima FROM ofertas WHERE nroPublicacion = ?) maxima WHERE ofertas.monto = maxima.OfertaMaxima");
			s.setInt(1, nroPublicacion);
			ResultSet rs = s.executeQuery();
			if (rs.next()){
				Float monto = rs.getFloat("monto");
				String medioDePago = rs.getString("medioDePago");
				LocalDateTime fechaOferta = rs.getTimestamp("fechaOferta").toLocalDateTime();
				String nombreDeUsuario = rs.getString("nombreDeUsuario");
				Usuario u = AdmUsuarios.getInstancia().buscarUsuario(nombreDeUsuario);
				if (u != null) {
					o = new Oferta(monto, u, medioDePago, fechaOferta);
				}
			}
			return o;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return null;
		}
	}
	
	public ArrayList<Oferta> getOfertas(int nroPublicacion) {
		ArrayList<Oferta> ofertas = new ArrayList<Oferta>();
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("SELECT * FROM ofertas WHERE nroPublicacion = ?");
			s.setInt(1, nroPublicacion);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				Float monto = rs.getFloat("monto");
				String medioDePago = rs.getString("medioDePago");
				LocalDateTime fechaOferta = rs.getTimestamp("fechaOferta").toLocalDateTime();
				String nombreDeUsuario = rs.getString("nombreDeUsuario");
				Usuario u = AdmUsuarios.getInstancia().buscarUsuario(nombreDeUsuario);
				if (u != null) {
					Oferta o = new Oferta(monto, u, medioDePago, fechaOferta);
					ofertas.add(o);
				}
			}
			return ofertas;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return null;
		}
	}
	
	public int insertOferta(Oferta o, int nroPublicacion) {
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("INSERT INTO usuarios (monto, medioDePago, fechaOferta, nroPublicacion, nombreDeUsuario) VALUES (?,?,?,?,?)");
			s.setFloat(1, o.getMonto());
			s.setString(2, o.getMedioDePago());
			s.setTimestamp(3, Timestamp.valueOf(o.getFechaOferta()));
			s.setInt(4, nroPublicacion);
			s.setString(5, o.getComprador().getNombreDeUsuario());

			//System.out.println(s.toString());
			s.execute();
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
			return 0;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return -1;
		}
	}
	
}