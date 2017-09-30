package modelo;

import java.time.LocalDateTime;

public class Password {
	static private int caducidad;
	private LocalDateTime ultimaModificacion;
	private String passwordString;
	
	public Password(String passwordString) {
		super();
		this.passwordString = passwordString;
		this.ultimaModificacion = LocalDateTime.now();
	}

	public static void setCaducidad(int caducidad) {
		Password.caducidad = caducidad;
	}
	
	public boolean estaVencida() {
		return (this.ultimaModificacion.plusDays(Password.caducidad).compareTo(LocalDateTime.now()) > 0);
	}
	
	public boolean passwordCorrecta(String passwordString) {
		return (this.passwordString.equals(passwordString));
	}
	
}
