package controlador;

import java.time.LocalDateTime;
import java.util.ArrayList;

import modelo.CompraInmediata;
import modelo.Publicacion;
import modelo.Subasta;
import modelo.UsuarioRegular;
import persistencia.AdmPersistenciaPublicacionMySQL;

public class SistPublicaciones {
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

		//TODO Hacer el Select en la DB -> Traer todos los usuarios que esten vendiendo el producto buscado con sus respectivas publicaciones, etc.
		
		//TODO Esto ahora es Case sensitive. Arreglarlo para que ignore el case.
		ArrayList<PublicacionView> pv = new ArrayList<PublicacionView>();
		for (int i = 0; i < publicaciones.size(); i++) {
			if (publicaciones.get(i).sosBuscado(publicacionBuscada)) {
				pv.add(publicaciones.get(i).getPublicacionView());
			}
		}
		return pv;
	}
	
	public ArrayList<PublicacionView> buscarPublicaciones(UsuarioRegular u) {
		if (u != null) {
			ArrayList<PublicacionView> pv=null;
			if (AdmUsuarios.getInstancia().getPublicacionesUsuario(u) != null) {
				pv = new ArrayList<PublicacionView>();
				for (int i = 0; i < AdmUsuarios.getInstancia().getPublicacionesUsuario(u).size(); i++) {
					pv.add(AdmUsuarios.getInstancia().getPublicacionesUsuario(u).get(i).getPublicacionView());
				}
				ArrayList<PublicacionView> pvPersistido = AdmPersistenciaPublicacionMySQL.getInstancia().buscarPublicacionesUsuario(u.getNombreDeUsuario());
				for (PublicacionView publicacionPersistida : pvPersistido) {
					boolean agregar=true;
					for (PublicacionView publicacionEnMemoria : pv) {
						if(publicacionPersistida.getNumPublicacion()==publicacionEnMemoria.getNumPublicacion()) {
							agregar=false;
							break;
						}
						if (agregar)
							pv.add(publicacionPersistida);
					}
				}
			}
			else {
				pv = AdmPersistenciaPublicacionMySQL.getInstancia().buscarPublicacionesUsuario(u.getNombreDeUsuario());
			}
			return pv;
		}
		return null;
	}
	
	public Publicacion buscarPublicacion(int numeroPublicacion) {
		for (int i = 0; i < publicaciones.size(); i++) {
			if (publicaciones.get(i).sosBuscado(numeroPublicacion)) {
				return publicaciones.get(i);
			}
		}
		//TODO Buscar en base de datos la publicacion -> Depende de como queramos trabajar, puede llegar a ser al pedo hacer esto.
		return null;
	}
	
	public ArrayList<PublicacionView> verMisPublicaciones() {
		return this.buscarPublicaciones((UsuarioRegular)AdmUsuarios.getInstancia().getUsuarioLogueado());
	}
	
	//TODO hacerOferta quizas habria que dividirlo en 3 distintos segun el medio de Pago elegido.
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
					return SistemaVentas.getInstancia().generarVentaTransfBancaria(nroPublicacion, cantidad, monto, nroTarjeta);
				}
				return -2; //
			}
		}
		return -1; // 
	}
	
	public String getNombrePublicacion(Publicacion p) {
		return p.getNombre();
	}
	
	public int devolverStock(CompraInmediata c, int cantidad) {
		c.devolverStock(cantidad);
		return 0;
	}
}
