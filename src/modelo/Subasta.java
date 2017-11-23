package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controlador.AdmUsuarios;
import controlador.PublicacionView;
import controlador.SubastaView;
import persistencia.AdmPersistenciaOfertasMySQL;
import persistencia.AdmPersistenciaPublicacionMySQL;

public class Subasta extends Publicacion {
	private LocalDateTime fechaHasta;
	private ArrayList<Oferta> ofertas;

	@Override
	public int ofertar(float monto, int cantidad, Usuario comprador, String medioDePago) {
		Oferta o = new Oferta(monto, comprador, medioDePago);
		AdmPersistenciaOfertasMySQL.getInstancia().insertOferta(o, this.nroPublicacion);
		//TODO Comprobar que la oferta sea superior a las anteriores.
		ofertas.add(o);
		return 1;
	}

	public Subasta(String nombreDeProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, LocalDateTime fechaHasta, UsuarioRegular vendedor) {
		super(nombreDeProducto, descripcion, imagenes, precioPublicado, vendedor);
		this.fechaHasta = fechaHasta;
		this.ofertas = new ArrayList<Oferta>();
//		Oferta ofertaVacia = new Oferta(precioPublicado, null, null);
		Oferta ofertaVacia = new Oferta(precioPublicado, vendedor, "Efectivo");
		ofertas.add(ofertaVacia);
		this.nroPublicacion=AdmPersistenciaPublicacionMySQL.getInstancia().insertPublicacion(this);
		AdmPersistenciaOfertasMySQL.getInstancia().insertOferta(ofertaVacia, this.nroPublicacion);
		System.out.println("Nombre: " + nombreProducto + " - NumeroP: " + nroPublicacion);
	}
	
	public Subasta(String nombreDeProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, LocalDateTime fechaHasta, int numPublicacion, LocalDateTime fechaPublicacion, Oferta ultimaOferta, UsuarioRegular vendedor) {
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
		PublicacionView sv = new SubastaView("Subasta", nombreProducto, descripcion, fechaPublicacion, this.imagenes, this.getPrecioActual(), estadoPublicacion, nroPublicacion, fechaHasta, this.vendedor==AdmUsuarios.getInstancia().getUsuarioLogueado());  //TODO Sera aceptable esto?
		return sv;
	}
	
	
	

}
