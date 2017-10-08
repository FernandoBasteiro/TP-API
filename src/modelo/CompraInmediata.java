package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controlador.CompraInmediataView;
import controlador.PublicacionView;
import controlador.SubastaView;

public class CompraInmediata extends Publicacion {
	private int stock;

	public CompraInmediata(String nombreDeProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, int stock, Usuario vendedor) {
		super(nombreDeProducto, descripcion, imagenes, precioPublicado, vendedor);
		this.stock = stock;
		// this.numPublicacion = persistir! -> Puede que en realidad lo tenga que hacer el controlador, para poder revisar los errores del persistir.
	}
	
	public CompraInmediata(String nombreDeProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, int stock, int numPublicacion, LocalDateTime fechaPublicacion, Usuario vendedor) {
		super(nombreDeProducto, descripcion, imagenes, precioPublicado, numPublicacion, fechaPublicacion, vendedor);
		this.stock = stock;
	}

	@Override
	public int ofertar(float monto, int cantidad, Usuario comprador, String medioDePago) {
		
		if (this.stock >= cantidad) {
			this.stock = this.stock - cantidad;
			if (this.stock == 0) {
				this.estadoPublicacion = "Finalizada";
			}
			return 0;
		}
		else if (this.stock == -1) {
			return 0;
		}
		else {
			// Mensaje de error en caso de solicitar mayor cantidad disponible. 
			// En la vista poner lista o validacion (Mensaje Temporal)
			System.out.println("Cantidad Solicitada es mayor al stock disponible"); 
			return 1;	
		}
	}

	@Override
	public float getPrecioActual() {
		return this.precioPublicado;
	}
	
	public PublicacionView getPublicacionView() {
		CompraInmediataView civ = new CompraInmediataView("Compra Inmediata", nombreProducto, descripcion, fechaPublicacion, imagenes, this.getPrecioActual(), estadoPublicacion, numPublicacion, stock); //TODO Sera aceptable esto?
		return civ;
	}
	
	public int devolverStock(int cantidad) {
		if (this.stock != -1) {
			this.stock = this.stock + cantidad;
			if (this.stock > 0) {
				this.estadoPublicacion = "Activa";
			}
		}
		return 0;
	}
	
}
