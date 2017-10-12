package persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import modelo.Usuario;

public class AdmPersistenciaUsuarioSQLS {
	static private AdmPersistenciaUsuarioSQLS instancia;
	
	private AdmPersistenciaUsuarioSQLS () {
		
	}
	
	public static AdmPersistenciaUsuarioSQLS getInstancia() {
		if (instancia == null) {
			instancia = new AdmPersistenciaUsuarioSQLS();
		}
		return instancia;
	}
	
	public Usuario buscarUsuario(String nombreDeUsuario) {
		return null;
		// Trae el usuario, con password, cuenta corriente, calificaciones, etc.
	}
	
	public int insertUsuario(Usuario usuario) {
		return 0;
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
