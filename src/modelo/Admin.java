package modelo;

import java.time.LocalDateTime;

import persistencia.AdmPersistenciaUsuarioMySQL;
import controlador.UsuarioView;

public class Admin extends Usuario {

	public Admin(String nombreDeUsuario, Password password,
			LocalDateTime fechaCreacion, boolean activo) {
		super(nombreDeUsuario, password, fechaCreacion, activo);
	}
	
	public Admin(String nombreDeUsuario, String passwordString) {
		super(nombreDeUsuario, passwordString);
		AdmPersistenciaUsuarioMySQL.getInstancia().insertUsuario(this);
	}

	public UsuarioView getUsuarioView() {
		return (new UsuarioView(this.nombreDeUsuario, this.activo));
	}
}
