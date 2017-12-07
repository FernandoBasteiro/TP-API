package controlador;

import java.time.LocalDateTime;

import modelo.Venta;

public class CalificacionView {
	private int numero;
	private int puntuacion;
	private String comentario;
	private boolean pendiente;
	private LocalDateTime fechaCalificacion;
	private String productoComprado;
	
	public CalificacionView(int numero, int puntuacion, String comentario,
			boolean pendiente, LocalDateTime fechaCalificacion,
			Venta v) {
		this.numero = numero;
		this.puntuacion = puntuacion;
		this.comentario = comentario;
		this.pendiente = pendiente;
		this.fechaCalificacion = fechaCalificacion;
		this.productoComprado = v.getPublicacion().getNombre();
	}

	public int getNumero() {
		return numero;
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

	public String getProductoComprado() {
		return productoComprado;
	}
	
}
