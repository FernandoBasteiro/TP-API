package modelo;

import java.time.LocalDateTime;

import controlador.UsuarioView;
import persistencia.AdmPersistenciaUsuario;

public abstract class Usuario {
	protected String nombreDeUsuario;
	protected LocalDateTime ultimaModificacion; //Este dato no se persiste. Es solo en memoria para evaluar si se debe mantener o si se puede liberar.
	protected Password password;
	protected LocalDateTime fechaCreacion;
	protected boolean activo;  // Estado del usuario
	

	public boolean estaActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public Password getPassword() {
		return password;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public Usuario(String nombreDeUsuario, String passwordString) {
		//Constructor de un usuario nuevo
		super();
		this.nombreDeUsuario = nombreDeUsuario;
		this.ultimaModificacion = LocalDateTime.now();
		this.password = new Password(passwordString);
		this.fechaCreacion = LocalDateTime.now();
		this.activo = true; // Estado activo en cuanto se crea.
	}
	
	public Usuario(String nombreDeUsuario, Password password, 
			LocalDateTime fechaCreacion, boolean activo) {
		//Constructor para usuarios de la DB.
		this.nombreDeUsuario = nombreDeUsuario;
		this.ultimaModificacion = LocalDateTime.now();
		this.password = password;
		this.fechaCreacion = fechaCreacion;
		this.activo = activo;
	}
	
	public boolean sosUsuario (String nombreDeUsuario) {
		return (this.nombreDeUsuario.equals(nombreDeUsuario));
	}
	
	public boolean passwordCorrecta (String passwordString) {
		return (this.password.passwordCorrecta(passwordString));
	}
	
	public boolean passwordVencida () {
		return (this.password.estaVencida());
	}

	public void cambiarPassword(String passwordString) {
		this.password.setPasswordString(passwordString);
	}

	public String getNombreDeUsuario() {
		return nombreDeUsuario;
	}
	
	public abstract UsuarioView getUsuarioView();
	
	static public Usuario buscarUsuarioDB(String nombreDeUsuario) {
		return AdmPersistenciaUsuario.getInstancia().buscarUsuario(nombreDeUsuario);
	}
		
	static public int updateUsuarioDB(Usuario u) {
		return AdmPersistenciaUsuario.getInstancia().updateUsuario(u);
	}
}
