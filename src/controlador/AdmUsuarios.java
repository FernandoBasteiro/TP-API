package controlador;

import java.util.ArrayList;

import modelo.Admin;
import modelo.Publicacion;
import modelo.Usuario;
import modelo.UsuarioRegular;
import modelo.Venta;

public class AdmUsuarios {
	static private AdmUsuarios instancia;
	private ArrayList<Usuario> usuarios;
	private Usuario usuarioLogueado;
	
	private AdmUsuarios () {
		usuarios = new ArrayList<Usuario>();
		usuarioLogueado = null;
	}
	
	static public AdmUsuarios getInstancia(){
		if (instancia == null) {
			instancia = new AdmUsuarios();
		}
		return instancia;
	}
	
	public int login (String nombreDeUsuario, String passwordString) {
		Usuario usuarioAux = buscarUsuario(nombreDeUsuario);
		if (usuarioAux != null) {
			if (! usuarioAux.estaActivo()) {
				return 4; //Usuario inactivo.
			}
			if (! usuarioAux.passwordCorrecta(passwordString)) {
				return 2;  //Password incorrecta.
			}
			if (usuarioAux.passwordVencida()) {
				return 1; //Password vencida. Solicitar cambio.
			}
			usuarioLogueado = usuarioAux;
			
			
			if (usuarioAux instanceof UsuarioRegular) {	
				return 0; //Login correcto.
			}
			
			//Porque estamos dando como Login incorrecto si se loguea un usuario Admin?
			
			
			else if (usuarioAux instanceof Admin) {
				return -1; //Login correcto de un Admin!
			}
		}
		return 3; //No existe el usuario o no es de una clase conocida.
	}
	
	public Usuario buscarUsuario(String nombreDeUsuario) {
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
	
	public UsuarioRegular buscarUsuarioRegular(String nombreDeUsuario) {
		for (int i = 0; i < usuarios.size(); i++) {
			if (nombreDeUsuario.equals(usuarios.get(i).getNombreDeUsuario())) {
				return (UsuarioRegular)usuarios.get(i);
			}
		}
		Usuario usuarioAux = Usuario.buscarUsuarioDB(nombreDeUsuario);
		if (usuarioAux != null) {
			usuarios.add(usuarioAux);
		}
		return (UsuarioRegular)usuarioAux;
	}
	
	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public int cambiarPassword (String passwordString) {
		if (usuarioLogueado != null) {
			usuarioLogueado.cambiarPassword(passwordString);
			return Usuario.updateUsuarioDB(usuarioLogueado); //Cambio correcto.
		}
		return 1; //Fallo el cambio.
	}
	
	public int cambiarPassword (String nombreDeUsuario, String passwordString, String nPasswordString) {
		Usuario usuarioAux = buscarUsuario(nombreDeUsuario);
		if (usuarioAux != null) {
			if (usuarioAux.passwordCorrecta(passwordString)) {
				usuarioAux.cambiarPassword(nPasswordString);
				return Usuario.updateUsuarioDB(usuarioAux); //Cambio correcto.
			}
		}
		return 1; //Fallo el cambio.
	}
	
	public int crearUsuario (String nombre, String domicilio, String mail,
			String nombreDeUsuario, String passwordString){
		if (buscarUsuario(nombreDeUsuario) == null) {
			UsuarioRegular usuarioAux = new UsuarioRegular (nombre, domicilio, mail, nombreDeUsuario, passwordString);
			usuarios.add(usuarioAux);
			return 0; //Usuario Creado
		}
		return 1; //Usuario ya existe.
	}
	
	
	public int crearAdmin (String nombreDeUsuario, String passwordString){
		if (buscarUsuario(nombreDeUsuario) == null) {
			Admin adminAux = new Admin (nombreDeUsuario, passwordString);
			usuarios.add(adminAux);
			return 0; //Usuario Creado
		}
		return 1; //Usuario ya existe.
	}
	
	

	
	public int cargarMovCtaCte(UsuarioRegular u, float monto, String concepto, Venta venta) {
		u.cargarMovimiento(venta, monto, concepto);
		return 0;
	}
	
	public ArrayList<MovCtaCteView> getMovsCtaCteView(){
		if (usuarioLogueado != null) {
			if (usuarioLogueado instanceof UsuarioRegular) {
				UsuarioRegular u = (UsuarioRegular)usuarioLogueado;
				return u.getMovimientos();
			}
		}
		return null;
	}
	
	public ArrayList<MovCtaCteView> getComisionesPagadasView(){
		if (usuarioLogueado != null) {
			if (usuarioLogueado instanceof UsuarioRegular) {
				UsuarioRegular u = (UsuarioRegular)usuarioLogueado;
				return u.getComisionesPagadas();
			}
		}
		return null;
	}
	
	public UsuarioView getLoggedUserView(){
		if (usuarioLogueado != null) {
			return this.usuarioLogueado.getUsuarioView();
		}
		return null;
	}
	
	public int modificarLoggedUser(String nombre, String mail, String domicilio) {
		if (usuarioLogueado != null) {
			UsuarioRegular u = (UsuarioRegular)usuarioLogueado;
			u.setNombre(nombre);
			u.setMail(mail);
			u.setDomicilio(domicilio);
			Usuario.updateUsuarioDB(usuarioLogueado);
			return 0;
		}
		return 1;
	}
	
	public int agregarPublicacionAUsuario(Publicacion p) {
		if (usuarioLogueado != null) {
			UsuarioRegular u = (UsuarioRegular)usuarioLogueado;
			u.agregarPublicacion(p);
			return 0;
		}
		return 1;
	}
	
	public ArrayList<Publicacion> getPublicacionesUsuario(UsuarioRegular u) {
		return u.getPublicaciones();
	}
	
	public String cargarExpiracionPass() {
		return Usuario.setCaducidadPass();
	}
	
	public int guardarExpiracionPass(String caducidad) {
		return Usuario.setCaducidadPass(caducidad);
	}
	

	
	public ArrayList<CalificacionView> getCalificacionesPendientesView() {
		if (usuarioLogueado != null) {
			if (usuarioLogueado instanceof UsuarioRegular) {
				return ((UsuarioRegular) usuarioLogueado).getCalificacionesPendientesView();
			}
		}
		return null;
	}
	
	public ArrayList<CalificacionView> getCalificacionesView() {
		if (usuarioLogueado != null) {
			if (usuarioLogueado instanceof UsuarioRegular) {
				return ((UsuarioRegular) usuarioLogueado).getCalificacionesView();
			}
		}
		return null;
	}
	
	public ArrayList<CalificacionView> getCalificacionesView(String nombreDeUsuario) {
		Usuario u = buscarUsuario(nombreDeUsuario);
		if (u != null) {
			if (u instanceof UsuarioRegular) {
				return ((UsuarioRegular) u).getCalificacionesView();
			}
		}
		return null;
	}
	
	public int setCalificacion(int nroCalificacion, int puntuacion, String comentarios) {
		if (usuarioLogueado != null) {
			if (usuarioLogueado instanceof UsuarioRegular) {
				return ((UsuarioRegular) usuarioLogueado).setCalificacion(nroCalificacion, puntuacion, comentarios);
			}
		}
		return 1;
	}
	
	public ArrayList<VentaView> obtenerListadoCompras() {
		if (usuarioLogueado != null) {
			return SistemaVentas.getInstancia().buscarCompras(usuarioLogueado.getNombreDeUsuario());
		}
		return null;
	}
	
	public ArrayList<VentaView> obtenerListadoVentas() {
		if (usuarioLogueado != null) {
			return SistemaVentas.getInstancia().buscarVentas(usuarioLogueado.getNombreDeUsuario());
		}
		return null;
	}
}
