package controlador;

public class UsuarioView {
	private String nombre;
	private String direccion;
	private String mail;
	private String nombreDeUsuario;
	public UsuarioView(String nombre, String direccion, String mail,
			String nombreDeUsuario) {
		super();
		this.nombre = nombre;
		this.direccion = direccion;
		this.mail = mail;
		this.nombreDeUsuario = nombreDeUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public String getMail() {
		return mail;
	}
	public String getNombreDeUsuario() {
		return nombreDeUsuario;
	}
	
	
}
