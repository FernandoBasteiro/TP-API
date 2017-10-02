package controlador;

public class UsuarioView {
	private String nombre;
	private String domicilio;
	private String mail;
	private String nombreDeUsuario;
	public UsuarioView(String nombre, String direccion, String mail,
			String nombreDeUsuario) {
		super();
		this.nombre = nombre;
		this.domicilio = direccion;
		this.mail = mail;
		this.nombreDeUsuario = nombreDeUsuario;
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
	
	
}
