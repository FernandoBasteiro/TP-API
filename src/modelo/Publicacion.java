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
	
	public abstract int ofertar(float monto, Usuario comprador, String medioDePago);
	
}
