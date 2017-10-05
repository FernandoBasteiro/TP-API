package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controlador.PublicacionView;
import controlador.SubastaView;

public class Subasta extends Publicacion {
	private LocalDateTime fechaHasta;
	private ArrayList<Oferta> ofertas;

	@Override
	public int ofertar(float monto, Usuario comprador, String medioDePago) {
		Oferta o = new Oferta(monto, comprador, medioDePago);
		ofertas.add(o);
		return 1;
	}

	public Subasta(String nombreDeProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, LocalDateTime fechaHasta, Usuario vendedor) {
		super(nombreDeProducto, descripcion, imagenes, precioPublicado, vendedor);
		this.fechaHasta = fechaHasta;
		this.ofertas = new ArrayList<Oferta>();
		Oferta ofertaVacia = new Oferta(precioPublicado, null, null);
		ofertas.add(ofertaVacia);
	}
	
	public Subasta(String nombreDeProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, LocalDateTime fechaHasta, int numPublicacion, LocalDateTime fechaPublicacion, Oferta ultimaOferta, Usuario vendedor) {
		super(nombreDeProducto, descripcion, imagenes, precioPublicado, numPublicacion, fechaPublicacion, vendedor);
		this.fechaHasta = fechaHasta;
		this.ofertas = new ArrayList<Oferta>();
		ofertas.add(ultimaOferta);
	}

	@Override
	public float getPrecioActual() {
		float mayorOferta = 0;
		for (int i = 0; i < ofertas.size(); i++) {
			if (ofertas.get(i).getMonto() > mayorOferta) {
				mayorOferta = ofertas.get(i).getMonto();
			}
		}
		return mayorOferta;
	}

	@Override
	public PublicacionView getPublicacionView() {
		SubastaView sv = new SubastaView("Subasta", nombreProducto, descripcion, fechaPublicacion, imagenes, this.getPrecioActual(), estadoPublicacion, numPublicacion, fechaHasta);  //TODO Sera aceptable esto?
		return sv;
	}
	
	
	

}
