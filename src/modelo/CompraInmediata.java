package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controlador.AdmUsuarios;
import controlador.CompraInmediataView;
import controlador.PublicacionView;
import persistencia.AdmPersistenciaPublicacion;

public class CompraInmediata extends Publicacion {
	private int stock;

	public CompraInmediata(String nombreDeProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, int stock, UsuarioRegular vendedor) {
		super(nombreDeProducto, descripcion, imagenes, precioPublicado, vendedor);
		this.stock = stock;
		// this.numPublicacion = persistir! -> Puede que en realidad lo tenga que hacer el controlador, para poder revisar los errores del persistir.
		this.nroPublicacion=AdmPersistenciaPublicacion.getInstancia().insertPublicacion(this);
		System.out.println("Nombre: " + nombreProducto + " - NumeroP: " + nroPublicacion);
	}
	
	public CompraInmediata(String nombreDeProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, int stock, int numPublicacion, LocalDateTime fechaPublicacion, UsuarioRegular vendedor) {
		super(nombreDeProducto, descripcion, imagenes, precioPublicado, numPublicacion, fechaPublicacion, vendedor);
		this.stock = stock;
	}

	public int getStock() {
		return stock;
	}

	@Override
	public int ofertar(float monto, int cantidad, Usuario comprador, String medioDePago) {
		
		if (this.stock >= cantidad) {
			this.stock = this.stock - cantidad;
			if (this.stock == 0) {
				this.estadoPublicacion = "Finalizada";
			}
			int error = AdmPersistenciaPublicacion.getInstancia().updateStockPublicacion(this); //TODO LOW Si falla habria que volver todo atras.
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
		PublicacionView civ = new CompraInmediataView("Compra Inmediata", nombreProducto, descripcion, fechaPublicacion, imagenes, this.getPrecioActual(), estadoPublicacion, nroPublicacion, stock, this.vendedor==AdmUsuarios.getInstancia().getUsuarioLogueado());
		return civ;
	}
	
	public int devolverStock(int cantidad) {
		if (this.stock != -1) {
			this.stock = this.stock + cantidad;
			if (this.stock > 0) {
				this.estadoPublicacion = "Activa";
			}
			int error = AdmPersistenciaPublicacion.getInstancia().updateStockPublicacion(this); //TODO LOW Si falla habria que volver todo atras.
		}
		return 0;
	}
	
	public Subasta transformarEnSubasta(float precioMinimo, LocalDateTime fechaHasta){
		if (this.stock > 0 || this.stock == -1) {
			if (this.stock > 0) {
				if (--this.stock == 0){
					this.estadoPublicacion = "Finalizada";
				}
				int error = AdmPersistenciaPublicacion.getInstancia().updateStockPublicacion(this); //TODO LOW Si falla habria que volver todo atras.
			}
			Subasta s = new Subasta(this.nombreProducto, this.descripcion, this.imagenes, precioMinimo, fechaHasta, this.vendedor);
			return s;
		}
		return null;
	}
}
