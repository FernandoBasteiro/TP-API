package controlador;

import java.util.ArrayList;

import modelo.Usuario;

public class AdmUsuarios {
	static private AdmUsuarios instancia;
	private ArrayList<Usuario> usuarios;
	private Usuario usuarioLogueado;
	
	private AdmUsuarios () {
		usuarios = new ArrayList<Usuario>();
		usuarioLogueado = null;
	}
	
	public AdmUsuarios getInstance(){
		if (instancia == null) {
			instancia = new AdmUsuarios();
		}
		return instancia;
	}
	
	public int login (String nombreDeUsuario, String passwordString) {
		Usuario usuarioAux = buscarUsuario(nombreDeUsuario);
		if (usuarioAux != null) {
			if (usuarioAux.passwordCorrecta(passwordString)) {
				if (! usuarioAux.passwordVencida()) {
					usuarioLogueado = usuarioAux;
					return 0; //Login correcto.
				}
				return 1; //Password vencida. Solicitar cambio.
			}
			return 2; //Password incorrecta.
		}
		return 3; //No existe el usuario.
	}
	
	private Usuario buscarUsuario(String nombreDeUsuario) {
		for (int i = 0; i < usuarios.size(); i++) {
			if (nombreDeUsuario.equals(usuarios.get(i).getNombreDeUsuario())) {
				return usuarios.get(i);
			}
		}
		Usuario usuarioAux = Usuario.buscarUsuarioDB(nombreDeUsuario);
		if (usuarioAux != null) {
			usuarios.add(usuarioAux);
		}
		return usuarioAux;
	}
	
	public int cambiarPassword (String passwordString) {
		if (usuarioLogueado != null) {
			usuarioLogueado.cambiarPassword(passwordString);
			return 0; //Cambio correcto.
		}
		return 1; //Usuario no logueado.
	}
	
	public int crearUsuario (String nombre, String domicilio, String mail,
			String nombreDeUsuario, String passwordString){
		if (buscarUsuario(nombreDeUsuario) == null) {
			Usuario usuarioAux = new Usuario (nombre, domicilio, mail, nombreDeUsuario, passwordString);
			usuarios.add(usuarioAux);
			return 0;
		}
		return 1;
	}
	
}
