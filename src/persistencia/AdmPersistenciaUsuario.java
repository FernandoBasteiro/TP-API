package persistencia;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import modelo.Admin;
import modelo.Calificacion;
import modelo.CtaCte;
import modelo.Password;
import modelo.Usuario;
import modelo.UsuarioRegular;

public class AdmPersistenciaUsuario {
	static private AdmPersistenciaUsuario instancia;
	
	private AdmPersistenciaUsuario() {
	}
	
	static public AdmPersistenciaUsuario getInstancia(){
		if (instancia == null) {
			instancia = new AdmPersistenciaUsuario();
		}
		return instancia;
	}
	
	public Usuario buscarUsuario(String usuario) {
		Usuario u = null;
		try {
			Connection con = PoolConnection.getPoolConnection().getConnection();
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
				PoolConnection.getPoolConnection().realeaseConnection(con);
				CtaCte c = new CtaCte(saldoCtaCte);
				Password p = new Password(passwordString, passwordModif);
				Boolean activo = rs.getBoolean("activo");
				Boolean admin = rs.getBoolean("administrador");
				/*
				ArrayList<Calificacion> cc = AdmPersistenciaCalificacion(usuario, "Comprador");
				ArrayList<Calificacion> cv = AdmPersistenciaCalificacion(usuario, "Vendedor");
				*/
				ArrayList<Calificacion> cc = null;
				ArrayList<Calificacion> cv = null;
				if (admin) {
					u = new Admin(nombreDeUsuario, p, fechaCreacion, activo);
				}
				else {
					u = new UsuarioRegular(nombre, domicilio, mail, nombreDeUsuario, p, c, fechaCreacion, cv, cc, activo);
				}
			}
			return u;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return null;
		}
	}
	
	public int insertUsuario(UsuarioRegular u) {
		try {
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("INSERT INTO usuarios VALUES (?,?,?,?,?,?,?,?,?,?)");
			s.setString(1, u.getNombreDeUsuario());
			s.setString(2, u.getNombre());
			s.setString(3, u.getDomicilio());
			s.setString(4, u.getMail());
			s.setDate(5, java.sql.Date.valueOf(u.getFechaCreacion().toString()));
			s.setString(6, u.getPassword().getPasswordString());
			s.setDate(7, java.sql.Date.valueOf(u.getPassword().getUltimaModificacion().toString()));
//			s.setTimestamp(7, Timestamp.valueOf(u.getPassword().getUltimaModificacion()));
			s.setFloat(8, u.getCtacte().getSaldoTotal());
			s.setBoolean(9, u.estaActivo());
			s.setBoolean(10, false);
			//System.out.println(s.toString());
			s.execute();
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return 0;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return -1;
		}
	}
	public int insertUsuario(Admin u) {
		try {
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("INSERT INTO usuarios VALUES (?,?,?,?,?,?,?,?,?,?)");
			s.setString(1, u.getNombreDeUsuario());
			s.setString(2, null);
			s.setString(3, null);
			s.setString(4, null);
			s.setDate(5, java.sql.Date.valueOf(u.getFechaCreacion().toString()));
//			s.setTimestamp(5, Timestamp.valueOf(u.getFechaCreacion()));
			s.setString(6, u.getPassword().getPasswordString());
			s.setDate(7, java.sql.Date.valueOf(u.getPassword().getUltimaModificacion().toString()));
//			s.setTimestamp(7, Timestamp.valueOf(u.getPassword().getUltimaModificacion()));
			s.setNull(8, java.sql.Types.FLOAT);
			s.setBoolean(9, u.estaActivo());
			s.setBoolean(10, true);
			//System.out.println(s.toString());
			s.execute();
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return 0;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return -1;
		}
	}
	
	public int updateUsuario(Usuario usuario) {
		try {
			Connection con = PoolConnection.getPoolConnection().getConnection();
			if (usuario instanceof UsuarioRegular) {
				UsuarioRegular u = (UsuarioRegular) usuario;
				PreparedStatement s = con.prepareStatement("UPDATE usuarios SET " + 
						"nombre = ?, " +
						"domicilio = ?, " +
						"mail = ?, " +
						"passwordString = ?, " +
						"passwordModif = ?, " +
						"saldoCtaCte = ?, " +
						"activo = ? " + 
						"WHERE nombreDeUsuario = ?"
						);
				s.setString(1, u.getNombre());
				s.setString(2, u.getDomicilio());
				s.setString(3, u.getMail());
				s.setString(4, u.getPassword().getPasswordString());
				s.setDate(5, java.sql.Date.valueOf(u.getPassword().getUltimaModificacion().toString()));
//				s.setTimestamp(5, Timestamp.valueOf(u.getPassword().getUltimaModificacion()));
				s.setFloat(6, u.getCtacte().getSaldoTotal());
				s.setBoolean(7, u.estaActivo());
				s.setString(8, u.getNombreDeUsuario());
				System.out.println(s.toString());
				s.execute();
				PoolConnection.getPoolConnection().realeaseConnection(con);
				return 0;
			}
			else if (usuario instanceof Admin) {
				Admin u = (Admin) usuario;
				PreparedStatement s = con.prepareStatement("UPDATE usuarios SET " + 
						"passwordString = ?, " +
						"passwordModif = ?, " +
						"activo = ? " +
						"WHERE nombreDeUsuario = ?"
						);
				s.setString(1, u.getPassword().getPasswordString());
				s.setDate(2, java.sql.Date.valueOf(u.getPassword().getUltimaModificacion().toString()));
//				s.setTimestamp(2, Timestamp.valueOf(u.getPassword().getUltimaModificacion()));
				s.setBoolean(3, u.estaActivo());
				s.setString(4, u.getNombreDeUsuario());
				//System.out.println(s.toString());
				s.execute();
				PoolConnection.getPoolConnection().realeaseConnection(con);
				return 0;
			}
			else {
				return 2;
			}
			
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return 1;
		}
	}
}
