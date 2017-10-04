package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Publicacion {
	protected static float comision = 5;
	protected String nombreProducto;
	protected String descripcion;
	protected LocalDateTime fechaPublicacion;
	protected ArrayList<String> imagenes;
	protected float precioPublicado;
	protected String estadoPublicacion;
	protected ArrayList<Venta> ventas;
	
	
	// Se ha agregado el Constructor ya que otras clases heredan Publicacion.
	
	public Publicacion(String nombreProducto, String descripcion, LocalDateTime fechaPublicacion, float precioPublicado){
		
		this.nombreProducto = nombreProducto;
		this.descripcion = descripcion;
		this.fechaPublicacion = fechaPublicacion;
		this.precioPublicado = precioPublicado;
		
	}
	
	public abstract int ofertar(float monto, Usuario comprador, String medioDePago);
	
}
