package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Usuario {
	private String nombre;
	private String domicilio;
	private String mail;
	private String nombreDeUsuario;
	private LocalDateTime ultimaModificacion;
	private CtaCte ctacte;
	private Password password;
	private ArrayList<Calificacion> calificacionesComprador;
	private ArrayList<Calificacion> calificacionesVendedor;	
	
	public Usuario(String nombre, String domicilio, String mail,
			String nombreDeUsuario, String passwordString) {
		super();
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.mail = mail;
		this.nombreDeUsuario = nombreDeUsuario;
		this.ultimaModificacion = LocalDateTime.now();
		this.ctacte = new CtaCte();
		this.password = new Password(passwordString);
		this.calificacionesComprador = new ArrayList<Calificacion>();
		this.calificacionesVendedor = new ArrayList<Calificacion>();
	}
	

	
	
}
