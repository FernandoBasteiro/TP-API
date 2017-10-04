package modelo;

import java.util.ArrayList;

public class CompraInmediata extends Publicacion {
	private int stock;

	public CompraInmediata(String nombreDeProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, int stock) {
		super(nombreDeProducto, descripcion, imagenes, precioPublicado);
		this.stock = stock;
	}

	@Override
	public int ofertar(float monto, Usuario comprador, String medioDePago) {
		//TODO Realziar venta.
		if (this.stock > 0) {
			if (--this.stock == 0) {
				this.estadoPublicacion = "Finalizada";
			}
		}
		return 0;
	}

	@Override
	public float getPrecioActual() {
		return this.precioPublicado;
	}
	
	
	
}
