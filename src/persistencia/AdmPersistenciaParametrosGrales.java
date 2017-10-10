package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

import controlador.AdmUsuarios;
import modelo.Oferta;
import modelo.Usuario;

public class AdmPersistenciaParametrosGrales {
	static private AdmPersistenciaParametrosGrales instancia;
	
	static public AdmPersistenciaParametrosGrales getInstancia(){
		if (instancia == null) {
			instancia = new AdmPersistenciaParametrosGrales();
		}
		return instancia;
	}
	
	private AdmPersistenciaParametrosGrales() {
		
	}
	
	public String getParametro(String tabla, String clave) {
		String valor = null;
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("SELECT * FROM parametros WHERE tabla = ? AND clave = ?");
			s.setString(1, tabla);
			s.setString(2, clave);
			ResultSet rs = s.executeQuery();
			if (rs.next()){
				valor = rs.getString("valor1");
			}
			return valor;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return null;
		}
	}
}
