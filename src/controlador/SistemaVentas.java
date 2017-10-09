package controlador;

import java.util.ArrayList;

import modelo.Efectivo;
import modelo.MercadoPago;
import modelo.Publicacion;
import modelo.TransfBancaria;
import modelo.Usuario;
import modelo.UsuarioRegular;
import modelo.Venta;

public class SistemaVentas {
	private ArrayList<Venta> ventas;
	static private SistemaVentas instancia;
	
	private SistemaVentas() {
		ventas = new ArrayList<Venta>();
	}
	
	static public SistemaVentas getInstancia(){
		if (instancia == null) {
			instancia = new SistemaVentas();
		}
		return instancia;
	}
	
	public String getNombrePublicacion(Venta v) {
		return SistPublicaciones.getInstancia().getNombrePublicacion(v.getPublicacion());
	}
	
	public int generarVentaEfectivo(int nroPublicacion, int cantidad, float monto) {
		Publicacion p = SistPublicaciones.getInstancia().buscarPublicacion(nroPublicacion);
		UsuarioRegular u = (UsuarioRegular)AdmUsuarios.getInstancia().getUsuarioLogueado();
		if (p == null) {
			return 1;
		}
		if (u == null) {
			return 2;
		}
		Efectivo e = new Efectivo(p, u, cantidad, monto);
		if (e.getNroVenta() == 0) {
			return 3;
		}
		else {
			ventas.add(e);
			return 0;
		}
		
	}

	public int generarVentaMercadoPago(int nroPublicacion, int cantidad, float monto, String nroTarjeta) {
		Publicacion p = SistPublicaciones.getInstancia().buscarPublicacion(nroPublicacion);
		UsuarioRegular u = (UsuarioRegular)AdmUsuarios.getInstancia().getUsuarioLogueado();
		if (p == null) {
			return 1;
		}
		if (u == null) {
			return 2;
		}
		MercadoPago m = new MercadoPago(p, u, cantidad, monto, nroTarjeta);
		if (m.getNroVenta() == 0) {
			return 3;
		}
		else {
			ventas.add(m);
			return 0;
		}
		
	}
	
	public int generarVentaTransfBancaria(int nroPublicacion, int cantidad, float monto, String CBU) {
		Publicacion p = SistPublicaciones.getInstancia().buscarPublicacion(nroPublicacion);
		UsuarioRegular u = (UsuarioRegular)AdmUsuarios.getInstancia().getUsuarioLogueado();
		if (p == null) {
			return 1;
		}
		if (u == null) {
			return 2;
		}
		TransfBancaria t = new TransfBancaria(p, u, cantidad, monto, CBU);
		if (t.getNroVenta() == 0) {
			return 3;
		}
		else {
			ventas.add(t);
			return 0;
		}
		
	}
	
	public Venta buscarVenta(int nroVenta) {
		for (int i = 0; i < ventas.size(); i++) {
			if (ventas.get(i).getNroVenta() == nroVenta) {
				return ventas.get(i);
				//TODO Set nueva fecha de ultima modificacion de la venta (Cosa que nunca tuve en cuenta).
			}
		}
		/* TODO Persistencia Ventas
		Venta ventaAux = Venta.buscarVentaDB(nroVenta);
		if (ventaAux != null) {
			ventas.add(ventaAux);
		}
		return ventaAux; 
		*/
		return null;
	}
}
