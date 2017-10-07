package modelo;

import java.time.LocalDateTime;

import controlador.CalificacionView;

public class Calificacion {
	private int puntuacion;
	private String comentario;
	private boolean pendiente;
	private LocalDateTime fechaCalificacion;
	private Venta venta;
	private int numero;
	
	public Calificacion(Venta v) {
		this.pendiente = true;
		this.venta = v;
		// this.numero = AdmPersistenciaCalificacion.getInstancia().insertCalificacion(SistemaVenta.getInstancia().getNumeroVenta(v));
		// Revisar que no haya fallado la persistencia de la Calificacion... Si fallo poner el numero en negativo o algo para poder verlo desde el controlador.
	}
	
	public Calificacion(int numero, Venta v) {
		this.pendiente = true;
		this.venta = v;
		this.numero = numero;
		// Este constructor es para las calificaciones que vienen de la BD pero estan todavia pendientes.
	}

	public Calificacion(int puntuacion, String comentario, LocalDateTime fechaCalificacion, int numero, Venta v) {
		this.pendiente = false;
		this.venta = v;
		this.numero = numero;
		this.puntuacion = puntuacion;
		this.comentario = comentario;
		this.fechaCalificacion = fechaCalificacion;
		// Este constructor es para las calificaciones que vienen de la BD que ya fueron "calificadas" por el usuario.
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
			//int error = persistir;
			int error = 0;
			return error;
		}
		return -1;
	}
	
	public boolean sosCalificacion(int numero){
		return (this.numero == numero);
	}

}
