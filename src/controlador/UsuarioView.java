package controlador;

public class UsuarioView {
	private String nombre;
	private String domicilio;
	private String mail;
	private String nombreDeUsuario;
	private boolean activo;
	private boolean admin;
	
	public UsuarioView(String nombre, String direccion, String mail,
			String nombreDeUsuario, boolean activo) {
		super();
		this.nombre = nombre;
		this.domicilio = direccion;
		this.mail = mail;
		this.nombreDeUsuario = nombreDeUsuario;
		this.activo = activo;
		this.admin = false;
	}
	
	public UsuarioView(String nombreDeUsuario, boolean activo) {
		super();
		this.nombreDeUsuario = nombreDeUsuario;
		this.activo = activo;
		this.admin = true;
	}
	
	public String getNombre() {
		return nombre;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public String getMail() {
		return mail;
	}
	public String getNombreDeUsuario() {
		return nombreDeUsuario;
	}
	public boolean estaActivo() {
		return activo;
	}
	public boolean esAdmin() {
		return admin;
	}
	
	
}
