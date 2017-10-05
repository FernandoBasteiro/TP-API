package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import modelo.Usuario;

public class AdmPersistenciaUsuario {
	static private AdmPersistenciaUsuario instancia;
	
	private AdmPersistenciaUsuario () {
		
	}
	
	public static AdmPersistenciaUsuario getInstancia() {
		if (instancia == null) {
			instancia = new AdmPersistenciaUsuario();
		}
		return instancia;
	}
	
	public Usuario buscarUsuario(String nombreDeUsuario) {
		return null;
		// Trae el usuario, con password, cuenta corriente, calificaciones, etc.
	}
	
	public int insertUsuario(Usuario usuario) {
		try {
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("Insert into dbo.usuario (usuid,nombre,domicilio,mail,usuairo,password,passmodif) values (?,?,?,?,?,?,?)");
//			s.setLong(1, usuario.get);(1, dni);
//			s.setString(2, nombre);
//			s.setString(3, domicilio);
//			s.setString(4, telefono);
//			s.execute();
			System.out.println("\nInsert ejecutado\n");
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return 0;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return 1;
		}
	}
	
	public int nuevoUsuarioId() {
		int usuid=0;
		try
		{
			Connection con = PoolConnection.getPoolConnection().getConnection();
			Statement s = con.createStatement();
			String sentencia="select (next value for seq_usuario_id) usuario_id";
			ResultSet rs = s.executeQuery(sentencia);
			while (rs.next())
			{
				usuid = rs.getInt(1);
			}
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return usuid;
		}
		catch (Exception e)
		{
			System.out.println("Error Query: " + e.getMessage());
		}
		return usuid;
	}
}
