package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controlador.PublicacionView;

public abstract class Publicacion {
	protected String nombreProducto;
	protected String descripcion;
	protected LocalDateTime fechaPublicacion;
	protected ArrayList<String> imagenes;
	protected float precioPublicado;
	protected String estadoPublicacion;
	protected ArrayList<Venta> ventas;
	protected int nroPublicacion;
	protected UsuarioRegular vendedor;
	//protected static int proxNumPublicacion = 1; // Solo para probar hasta que haya persistencia.
	
	public int getNroPublicacion() {
		return nroPublicacion;
	}

/*	public static int getNumPub(){ //Solo para probar mientras no haya persistencia
		return proxNumPublicacion++;
	}
*/
	
	public Publicacion(String nombreProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, UsuarioRegular vendedor) {
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
	
	public UsuarioRegular getVendedor() {
		return vendedor;
	}

	public Publicacion(String nombreProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, int nroPublicacion, LocalDateTime fechaPublicacion, UsuarioRegular vendedor) {
		super();
		this.nombreProducto = nombreProducto;
		this.descripcion = descripcion;
		this.fechaPublicacion = fechaPublicacion;
		this.imagenes = imagenes;
		this.precioPublicado = precioPublicado;
		this.estadoPublicacion = "Activa";
		this.ventas = new ArrayList<Venta>();
		this.nroPublicacion = nroPublicacion;
		this.vendedor = vendedor;
	}

	public abstract int ofertar(float monto, int cantidad, Usuario comprador, String medioDePago);
	
	public abstract float getPrecioActual();
	
	public abstract PublicacionView getPublicacionView();
	
	public boolean sosBuscado(String buscado) {
		if (nombreProducto.contains(buscado)) {
			return true;
		}
		return false;
	}
	
	public boolean sosBuscado(int buscado) {
		if (nroPublicacion == buscado) {
			return true;
		}
		return false;
	}
	
	public String getNombre(){
		return nombreProducto;
	}
	
}
