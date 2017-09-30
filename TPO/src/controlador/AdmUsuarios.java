package controlador;

import java.util.ArrayList;

import modelo.Usuario;

public class AdmUsuarios {
	static private AdmUsuarios instancia;
	private ArrayList<Usuario> usuarios;
	private Usuario usuarioActual;
	
	private AdmUsuarios () {
		usuarios = new ArrayList<Usuario>();
		usuarioActual = null;
	}
	
	public AdmUsuarios getInstance(){
		if (instancia == null) {
			instancia = new AdmUsuarios();
		}
		return instancia;
	}
	
	public int login (String nombreDeUsuario, String passwordString) {
		return 0;
	}
	
	private Usuario buscarUsuario(String nombreDeUsuario) {
		return null;
	}
	
	public int cambiarPassword (String passwordString) {
		return 0;
	}
	
}
