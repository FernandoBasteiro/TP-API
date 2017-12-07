package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import persistencia.AdmPersistenciaCalificacion;
import controlador.CalificacionView;

public class Calificacion {
	private int puntuacion;
	private String comentario;
	private boolean pendiente;
	private LocalDateTime fechaCalificacion;
	private Venta venta;
	private int numero;
	
	public Calificacion(Venta v, boolean esVendedor) {
		//Constructor para las calificaciones nuevas generadas por una venta confirmada.
		this.pendiente = true;
		this.venta = v;
		this.numero = AdmPersistenciaCalificacion.getInstancia().insertCalificacion(this, esVendedor);
	}
	
	public Calificacion(int numero, Venta v) {
		//Constructor para las calificaciones pendientes recuperadas de la BD
		this.pendiente = true;
		this.venta = v;
		this.numero = numero;
	}

	public Calificacion(int puntuacion, String comentario, LocalDateTime fechaCalificacion, int numero, Venta v) {
		//Constructor para las calificaciones completas recuperadas de la BD
		this.pendiente = false;
		this.venta = v;
		this.numero = numero;
		this.puntuacion = puntuacion;
		this.comentario = comentario;
		this.fechaCalificacion = fechaCalificacion;
	}
	
	public boolean pendiente () {
		return pendiente;
	}
	
	public CalificacionView getView() {
		return (new CalificacionView(this.numero, this.puntuacion, this.comentario, this.pendiente, this.fechaCalificacion, this.venta));
	}
	
	public int setCalificacion(int puntuacion, String comentario) {
		if (this.pendiente) {
			this.pendiente = false;
			this.puntuacion = puntuacion;
			this.comentario = comentario;
			this.fechaCalificacion = LocalDateTime.now();
			return AdmPersistenciaCalificacion.getInstancia().updateCalificacion(this);
		}
		return -1;
	}
	
	public boolean sosCalificacion(int numero){
		return (this.numero == numero);
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public String getComentario() {
		return comentario;
	}

	public boolean isPendiente() {
		return pendiente;
	}

	public LocalDateTime getFechaCalificacion() {
		return fechaCalificacion;
	}

	public Venta getVenta() {
		return venta;
	}

	public int getNumero() {
		return numero;
	}
	
	static public ArrayList<Calificacion> buscarCalificacionesComprador(String nombreDeUsuario) {
		return AdmPersistenciaCalificacion.getInstancia().buscarCalificacionesComprador(nombreDeUsuario);
	}
	
	static public ArrayList<Calificacion> buscarCalificacionesVendedor(String nombreDeUsuario) {
		return AdmPersistenciaCalificacion.getInstancia().buscarCalificacionesVendedor(nombreDeUsuario);
	}
	
	static public ArrayList<Calificacion> buscarCalificacionesPendientesComprador(String nombreDeUsuario) {
		return AdmPersistenciaCalificacion.getInstancia().buscarCalificacionesPendientesComprador(nombreDeUsuario);
	}
	
	static public ArrayList<Calificacion> buscarCalificacionesPendientesVendedor(String nombreDeUsuario) {
		return AdmPersistenciaCalificacion.getInstancia().buscarCalificacionesPendientesVendedor(nombreDeUsuario);
	}
	
}
