package persistencia;

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
		return 0;
	}
}
