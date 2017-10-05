package controlador;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class PublicacionView {
	protected String tipoPublicacion;
	protected String nombreProducto;
	protected String descripcion;
	protected LocalDateTime fechaPublicacion;
	protected ArrayList<String> imagenes;
	protected float precioActual;
	protected String estadoPublicacion;
	protected int numPublicacion;
	
	public PublicacionView(String tipoPublicacion, String nombreProducto,
			String descripcion, LocalDateTime fechaPublicacion,
			ArrayList<String> imagenes, float precioActual,
			String estadoPublicacion, int numPublicacion) {
		this.tipoPublicacion = tipoPublicacion;
		this.nombreProducto = nombreProducto;
		this.descripcion = descripcion;
		this.fechaPublicacion = fechaPublicacion;
		this.imagenes = imagenes;
		this.precioActual = precioActual;
		this.estadoPublicacion = estadoPublicacion;
		this.numPublicacion = numPublicacion;
	}
	public String getTipoPublicacion() {
		return tipoPublicacion;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public LocalDateTime getFechaPublicacion() {
		return fechaPublicacion;
	}
	public ArrayList<String> getImagenes() {
		return imagenes;
	}
	public float getPrecioActual() {
		return precioActual;
	}
	public String getEstadoPublicacion() {
		return estadoPublicacion;
	}
	public int getNumPublicacion() {
		return numPublicacion;
	}
	
	public abstract int getStock();
	
	public abstract LocalDateTime getFechaHasta();
}
