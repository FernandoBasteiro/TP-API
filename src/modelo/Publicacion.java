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
	protected int numPublicacion;
	protected Usuario vendedor;
	
	
	
	public Publicacion(String nombreProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, Usuario vendedor) {
		super();
		this.nombreProducto = nombreProducto;
		this.descripcion = descripcion;
		this.fechaPublicacion = LocalDateTime.now();
		this.imagenes = imagenes;
		this.precioPublicado = precioPublicado;
		this.estadoPublicacion = "Activa";
		this.vendedor = vendedor;
		this.ventas = new ArrayList<Venta>();
	}
	
	public Publicacion(String nombreProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, int numPublicacion, LocalDateTime fechaPublicacion, Usuario vendedor) {
		super();
		this.nombreProducto = nombreProducto;
		this.descripcion = descripcion;
		this.fechaPublicacion = fechaPublicacion;
		this.imagenes = imagenes;
		this.precioPublicado = precioPublicado;
		this.estadoPublicacion = "Activa";
		this.ventas = new ArrayList<Venta>();
		this.numPublicacion = numPublicacion;
		this.vendedor = vendedor;
	}

	public abstract int ofertar(float monto, Usuario comprador, String medioDePago);
	
	public abstract float getPrecioActual();
	
	public abstract PublicacionView getPublicacionView();
	
	public boolean sosBuscado(String buscado) {
		if (nombreProducto.contains(buscado)) {
			return true;
		}
		return false;
	}
	
	public boolean sosBuscado(int buscado) {
		if (numPublicacion == buscado) {
			return true;
		}
		return false;
	}
	
	
}
