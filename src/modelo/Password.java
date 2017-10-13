package modelo;

import java.time.LocalDateTime;

import persistencia.AdmPersistenciaParametrosGrales;

public class Password {
	static private int caducidad;
	private LocalDateTime ultimaModificacion;
	private String passwordString;
	
	public Password(String passwordString) {
		super();
		this.passwordString = passwordString;
		this.ultimaModificacion = LocalDateTime.now();
	}
	
	public Password(String passwordString, LocalDateTime ultimaModificacion) {
		super();
		this.passwordString = passwordString;
		this.ultimaModificacion = ultimaModificacion;
	}
	
	public static String setCaducidad() {
		Password.caducidad = Integer.valueOf(AdmPersistenciaParametrosGrales.getInstancia().getParametro("PASSWORD", "VENC"));
		return String.valueOf(Password.caducidad);
	}
	
	public static int setCaducidad(String caducidad) {
		if (AdmPersistenciaParametrosGrales.getInstancia().setParametro("PASSWORD", "VENC", caducidad) == 0) {
			Password.caducidad = Integer.valueOf(caducidad);
			return 0;
		}
		return 1;
	}
	
	
	public boolean estaVencida() {
		return (this.ultimaModificacion.plusDays(Password.caducidad).compareTo(LocalDateTime.now()) < 0);
	}
	
	public static int getCaducidad() {
		return caducidad;
	}

	public LocalDateTime getUltimaModificacion() {
		return ultimaModificacion;
	}

	public String getPasswordString() {
		return passwordString;
	}

	public boolean passwordCorrecta(String passwordString) {
		return (this.passwordString.equals(passwordString));
	}

	public void setPasswordString(String passwordString) {
		this.passwordString = passwordString;
		this.ultimaModificacion = LocalDateTime.now();
	}

	
	
}
