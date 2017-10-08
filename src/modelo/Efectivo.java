package modelo;

import java.time.LocalDateTime;

import controlador.AdmUsuarios;
import controlador.SistemaVentas;

public class Efectivo extends Venta{
	public Efectivo(int nroVenta, Publicacion p, Usuario c, int cantidad, float montoPagado,
			float montoComision, String estadoPago, LocalDateTime fechaDeCompra) {
		//Constructor para Ventas cargadas desde la DB
		super(nroVenta, p, c, cantidad, montoPagado, montoComision, estadoPago, fechaDeCompra);
	}
	
	public Efectivo(Publicacion p, Usuario c, int cantidad, float montoUnitario) {
		//Constructor para nuevas Ventas
		super(p, c, cantidad, montoUnitario);
		
		//Con capacidad de descubierto en ctacte:
		AdmUsuarios.getInstancia().cargarMovCtaCte(c, -(montoUnitario * cantidad), "Compra de " + p.getNombre(), this);
		confirmarPago();
		
		/* Si no se puede quedar en descubierto en la ctacte:
		if (c.getSaldoCtaCte() >= montoUnitario * cantidad) {
			AdmUsuarios.getInstancia().cargarMovCtaCte(c, -(montoUnitario * cantidad), "Compra de " + p.getNombre(), this);
			confirmarPago();
		}
		else {
			rechazarPago();
		}
		*/
		System.out.println(this.estadoPago);
	}
}
