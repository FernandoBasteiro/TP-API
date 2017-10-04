package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controlador.PublicacionView;

public abstract class Publicacion {
	protected static float comision = 5; //TODO
	protected String nombreProducto;
	protected String descripcion;
	protected LocalDateTime fechaPublicacion;
	protected ArrayList<String> imagenes;
	protected float precioPublicado;
	protected String estadoPublicacion;
	protected ArrayList<Venta> ventas;
	
	
	
	public Publicacion(String nombreProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado) {
		super();
		this.nombreProducto = nombreProducto;
		this.descripcion = descripcion;
		this.fechaPublicacion = LocalDateTime.now();
		this.imagenes = imagenes;
		this.precioPublicado = precioPublicado;
		this.estadoPublicacion = "Activa";
		this.ventas = new ArrayList<Venta>();
	}

	public abstract int ofertar(float monto, Usuario comprador, String medioDePago);
	
	public abstract float getPrecioActual();
	
	public boolean sosBuscado(String buscado) {
		if (nombreProducto.contains(buscado)) {
			return true;
		}
		return false;
	}
	
	public PublicacionView getPublicacionView() {
		
		return null;
	}
	
}
