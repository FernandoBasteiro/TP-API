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
	private LocalDateTime fechaVenta;
	private String nombreVendedor;
	
	public LocalDateTime getFechaVenta() {
		return fechaVenta;
	}

	public String getNombreVendedor() {
		return nombreVendedor;
	}

	public CalificacionView(int numero, int puntuacion, String comentario,
			boolean pendiente, LocalDateTime fechaCalificacion,
			Venta v) {
		this.numero = numero;
		this.puntuacion = puntuacion;
		this.comentario = comentario;
		this.pendiente = pendiente;
		this.fechaCalificacion = fechaCalificacion;
		this.productoComprado = SistemaVentas.getInstancia().getNombrePublicacion(v);
		this.fechaVenta = v.getFechaDeCompra();
		this.nombreVendedor = v.getPublicacion().getVendedor().getNombre();
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
