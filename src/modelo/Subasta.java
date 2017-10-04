package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Subasta extends Publicacion {
	private LocalDateTime fechaHasta;
	private ArrayList<Oferta> ofertas;

	@Override
	public int ofertar(float monto, Usuario comprador, String medioDePago) {
		Oferta o = new Oferta(monto, comprador, medioDePago);
		ofertas.add(o);
		return 0;
	}

	//Cambio en la funcion subasta para matchear el constructor de Publicacion
	
	public Subasta(String nombreProducto, String descripcion, LocalDateTime fechaPublicacion, 
			float precioPublicado, LocalDateTime fechaHasta) {
		
		super(nombreProducto, descripcion, fechaPublicacion, precioPublicado);
		this.fechaHasta = fechaHasta;
		this.ofertas = new ArrayList<Oferta>();
	}
	

}
