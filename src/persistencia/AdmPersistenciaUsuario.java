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
		try
		{ //TODO cosas hardcodeadas y getters no se sabe si esta bien
			Connection con = PoolConnection.getPoolConnection().getConnection();
			
			String sentencia="insert into API.dbo.Usuario (usuid, nombre, domicilio, mail, usuario, password, passmodif, activo) values"
					+ " (?,?,?,?,?,?,?,?)";
			
			PreparedStatement s = con.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
			s.setInt(1, 2); //TODO hay que autoincrementar la tabla sql
			s.setString(2, usuario.getNombre());
			s.setString(3, usuario.getDomicilio());
			s.setString(4, usuario.getMail());
			s.setString(5, usuario.getNombreDeUsuario());
			s.setString(6, usuario.getPassword().getPasswordString()); //esto esta bien
			s.setString(7, "2017-10-06 21:24:18.713");
			s.setInt(8, 1);
			
			ResultSet rs = s.executeQuery();

			PoolConnection.getPoolConnection().realeaseConnection(con);
			return 2; //aca hay que obtener el id del usuario recien creado en la base de datos
		}
		catch (Exception e)
		{
			System.out.println("Error Query: " + e.getMessage());
		}
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
