package persistencia;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import modelo.Calificacion;
import modelo.CtaCte;
import modelo.Password;
import modelo.Usuario;

public class AdmPersistenciaUsuarioMySQL {
	static private AdmPersistenciaUsuarioMySQL instancia;
	
	private AdmPersistenciaUsuarioMySQL() {
	}
	
	static public AdmPersistenciaUsuarioMySQL getInstancia(){
		if (instancia == null) {
			instancia = new AdmPersistenciaUsuarioMySQL();
		}
		return instancia;
	}
	
	public Usuario buscarUsuario(String usuario) {
		Usuario u = null;
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("SELECT * FROM usuarios WHERE nombreDeUsuario = ?");
			s.setString(1, usuario);
			ResultSet rs = s.executeQuery();
			while (rs.next())
			{
			String nombreDeUsuario = rs.getString("nombreDeUsuario");
			String nombre = rs.getString("nombre");
			String domicilio = rs.getString("domicilio");
			String mail = rs.getString("mail");
			LocalDateTime fechaCreacion = rs.getTimestamp("fechaCreacion").toLocalDateTime();
			String passwordString = rs.getString("passwordString");
			LocalDateTime passwordModif = rs.getTimestamp("passwordModif").toLocalDateTime();
			Float saldoCtaCte = rs.getFloat("saldoCtaCte");
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
			CtaCte c = new CtaCte(saldoCtaCte);
			Password p = new Password(passwordString, passwordModif);
			/*
			ArrayList<Calificacion> cc = AdmPersistenciaCalificacion(usuario, "Comprador");
			ArrayList<Calificacion> cv = AdmPersistenciaCalificacion(usuario, "Vendedor");
			*/
			ArrayList<Calificacion> cc = null;
			ArrayList<Calificacion> cv = null;
			u = new Usuario(nombre, domicilio, mail, nombreDeUsuario, p, c, fechaCreacion, cv, cc);
			}
			return u;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return null;
		}
	}
	
	public int insertUsuario(Usuario u) {
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("INSERT INTO usuarios VALUES (?,?,?,?,?,?,?,?)");
			s.setString(1, u.getNombreDeUsuario());
			s.setString(2, u.getNombre());
			s.setString(3, u.getDomicilio());
			s.setString(4, u.getMail());
			s.setTimestamp(5, Timestamp.valueOf(u.getFechaCreacion()));
			s.setString(6, u.getPassword().getPasswordString());
			s.setTimestamp(7, Timestamp.valueOf(u.getPassword().getUltimaModificacion()));
			s.setFloat(8, u.getCtacte().getSaldoTotal());
			//System.out.println(s.toString());
			s.execute();
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
			return 0;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return -1;
		}
	}
	
	public int updateUsuario(Usuario u) {
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("UPDATE usuarios SET " + 
					"nombre = ?, " +
					"domicilio = ?, " +
					"mail = ?, " +
					"fechaCreacion = ?, " +
					"passwordString = ?, " +
					"passwordModif = ?, " +
					"saldoCtaCte = ? " +
					"WHERE nombreDeUsuario = ?"
					);
			s.setString(1, u.getNombre());
			s.setString(2, u.getDomicilio());
			s.setString(3, u.getMail());
			s.setTimestamp(4, Timestamp.valueOf(u.getFechaCreacion()));
			s.setString(5, u.getPassword().getPasswordString());
			s.setTimestamp(6, Timestamp.valueOf(u.getPassword().getUltimaModificacion()));
			s.setFloat(7, u.getCtacte().getSaldoTotal());
			s.setString(8, u.getNombreDeUsuario());
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
