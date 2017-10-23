package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
			return valor;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return null;
		}
	}
	
	public int setParametro(String tabla, String clave, String parametro) {
		try {
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("UPDATE parametros SET valor1 = ? WHERE tabla = ? AND clave = ?");
			
			s.setString(1, parametro);
			s.setString(2, tabla);
			s.setString(3, clave);
			s.execute();
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
			return 0;
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return 1;
		}
	}
}
