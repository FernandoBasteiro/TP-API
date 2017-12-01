package modelo;

import java.time.LocalDateTime;

import controlador.UsuarioView;
import persistencia.AdmPersistenciaUsuario;

public class Admin extends Usuario {
	
	private String nombre;

	public Admin(String nombreDeUsuario, Password password,
			LocalDateTime fechaCreacion, boolean activo) {
		super(nombreDeUsuario, password, fechaCreacion, activo);
	}
	
	public Admin(String nombreDeUsuario, String passwordString) {
		super(nombreDeUsuario, passwordString);
		AdmPersistenciaUsuario.getInstancia().insertUsuario(this);
	}

	public UsuarioView getUsuarioView() {
		return (new UsuarioView(this.nombreDeUsuario, this.activo));
	}


	public String getNombre() {
		return nombreDeUsuario;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



}
