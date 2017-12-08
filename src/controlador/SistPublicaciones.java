package controlador;

import java.time.LocalDateTime;
import java.util.ArrayList;

import modelo.CompraInmediata;
import modelo.Publicacion;
import modelo.Subasta;
import modelo.UsuarioRegular;
import observer.ObservadoMailSubasta;
import persistencia.AdmPersistenciaPublicacion;

public class SistPublicaciones extends ObservadoMailSubasta{
	private ArrayList<Publicacion> publicaciones;
	private Publicacion publicacionActual;
	static private SistPublicaciones instancia;
	
	private SistPublicaciones(){
		publicaciones = new ArrayList<Publicacion>();
		publicacionActual = null;
	}
	
	static public SistPublicaciones getInstancia() {
		if (instancia == null) {
			instancia = new SistPublicaciones();
		}
		return instancia;
	}
	
	public int crearSubasta(String nombreDeProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, LocalDateTime fechaHasta) {
		if (AdmUsuarios.getInstancia().getUsuarioLogueado() != null) {
			UsuarioRegular u = (UsuarioRegular)AdmUsuarios.getInstancia().getUsuarioLogueado();
			Subasta s = new Subasta(nombreDeProducto, descripcion, imagenes, precioPublicado, fechaHasta, u);
			publicaciones.add(s);
			AdmUsuarios.getInstancia().agregarPublicacionAUsuario(s);
			return 0;
		}
		return 1;
	}
	
	public int crearCompraInmediata(String nombreDeProducto, String descripcion, ArrayList<String> imagenes, float precioPublicado, int stock) {
		if (AdmUsuarios.getInstancia().getUsuarioLogueado() != null) {
			UsuarioRegular u = (UsuarioRegular)AdmUsuarios.getInstancia().getUsuarioLogueado();
			CompraInmediata ci = new CompraInmediata(nombreDeProducto, descripcion, imagenes, precioPublicado, stock, u);
			publicaciones.add(ci);
			AdmUsuarios.getInstancia().agregarPublicacionAUsuario(ci);
			return 0;
		}
		return 1;
	}
	
	public ArrayList<PublicacionView> buscarPublicaciones(String publicacionBuscada) {

		ArrayList<Publicacion> pubs = AdmPersistenciaPublicacion.getInstancia().buscarPublicacionesProducto(publicacionBuscada);
		boolean agregarPub=true;
		if(publicaciones.size()>0) {
			for (Publicacion pubPersistida : pubs) {
				agregarPub=true;
				for (Publicacion pubMemoria : publicaciones) {
					if(pubPersistida.getNroPublicacion()==pubMemoria.getNroPublicacion()) {
						agregarPub=false;
						break;
					}
				}
				if(agregarPub)
					publicaciones.add(pubPersistida);
			}
		}
		else {
			for (Publicacion pubPersistida : pubs) {
				publicaciones.add(pubPersistida);
			}
		}
		ArrayList<PublicacionView> pv = new ArrayList<PublicacionView>();
		for (int i = 0; i < pubs.size(); i++) {
			pv.add(pubs.get(i).getPublicacionView());
		}
		return pv;
	}
	
	public int[] buscarNroPublicaciones(String nombreDeUsuario) {
		ArrayList<Publicacion> pubs = AdmPersistenciaPublicacion.getInstancia().buscarPublicacionesUsuario(nombreDeUsuario, "");
		if (publicaciones.size() == 0) {
			publicaciones = pubs;
		}
		else {
			for (Publicacion p : pubs) {
				if (! publicacionCargada(p.getNroPublicacion())) {
					this.publicaciones.add(p);
				}
			}
		}
		int[] lista;
		if (pubs != null) {
			lista = new int[pubs.size()];
			for (int i = 0; i < pubs.size(); i++) {
				lista[1] = pubs.get(i).getNroPublicacion();
			}
			return lista;
		}
		return null;
	}
	
	public ArrayList<PublicacionView> buscarPublicaciones(UsuarioRegular u, String buscado) {
		if (u != null) {
			ArrayList<PublicacionView> pv = null;
			ArrayList<Publicacion> pubs = AdmPersistenciaPublicacion.getInstancia().buscarPublicacionesUsuario(u.getNombreDeUsuario(), buscado);
			if (pubs.size() > 0) {pv = new ArrayList<PublicacionView>();}
			for (int i = 0; i < pubs.size(); i++) {
				pv.add(pubs.get(i).getPublicacionView());
			}
			u.setPublicaciones(pubs);
			if (publicaciones.size() == 0) {
				publicaciones = pubs;
			}
			else {
				for (Publicacion p : pubs) {
					if (! publicacionCargada(p.getNroPublicacion())) {
						this.publicaciones.add(p);
					}
				}
			}
			return pv;
		}
		return null;
	}
	
	public boolean publicacionCargada(int nroPublicacion) {
		for (int i = 0; i < publicaciones.size(); i++) {
			if (publicaciones.get(i).getNroPublicacion() == nroPublicacion) {
				return true;
			}
		}
		return false;
	}
	
	
	public Publicacion buscarPublicacion(int numeroPublicacion) {
		for (int i = 0; i < publicaciones.size(); i++) {
			if (publicaciones.get(i).sosBuscado(numeroPublicacion)) {
				return publicaciones.get(i);
			}
		}
		return Publicacion.buscarPublicacionDB(numeroPublicacion);
	}
	
	public ArrayList<PublicacionView> verMisPublicaciones(String buscado) {
		return this.buscarPublicaciones((UsuarioRegular)AdmUsuarios.getInstancia().getUsuarioLogueado(),buscado);
	}
	
	public int hacerOferta(int nroPublicacion, float monto, int cantidad, String medioDePago, String nroTarjeta, String CBU) {
		if (AdmUsuarios.getInstancia().getUsuarioLogueado() != null) {
			int resultado =  buscarPublicacion(nroPublicacion).ofertar(monto, cantidad, AdmUsuarios.getInstancia().getUsuarioLogueado(), medioDePago);			
			if (resultado == 0) {
				if (medioDePago.equals("Efectivo")) {
					return SistemaVentas.getInstancia().generarVentaEfectivo(nroPublicacion, cantidad, monto);
				}
				else if (medioDePago.equals("Transferencia Bancaria")) {
					return SistemaVentas.getInstancia().generarVentaTransfBancaria(nroPublicacion, cantidad, monto, CBU);
				}
				else if (medioDePago.equals("MercadoPago")) {
					return SistemaVentas.getInstancia().generarVentaMercadoPago(nroPublicacion, cantidad, monto, nroTarjeta);
				}
				return -2; //
			}
		}
		return -1; // 
	}
	
	public int transformarEnSubasta(int nroPublicacion, float precioMinimo, LocalDateTime fechaHasta) {
		Publicacion p = buscarPublicacion(nroPublicacion);
		if (p instanceof CompraInmediata) {
			Publicacion s = ((CompraInmediata) p).transformarEnSubasta(precioMinimo, fechaHasta);
			if (s != null) {
				publicaciones.add(s);
				AdmUsuarios.getInstancia().agregarPublicacionAUsuario(s);
				return 0;
			}
			return 1; //Error Stock = 0
		}
		return 2; //Error La publicacion no era CompraInmediata.
	}
	
	public String getNombrePublicacion(Publicacion p) {
		return p.getNombre();
	}
	
	public int devolverStock(CompraInmediata c, int cantidad) {
		c.devolverStock(cantidad);
		return 0;
	}
}
