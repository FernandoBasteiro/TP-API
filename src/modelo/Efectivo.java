package modelo;

import java.time.LocalDateTime;

import controlador.AdmUsuarios;
import persistencia.AdmPersistenciaVenta;

public class Efectivo extends Venta{
	public Efectivo(int nroVenta, Publicacion p, Usuario c, int cantidad, float montoUnitario,
			float montoComision, String estadoPago, LocalDateTime fechaDeCompra) {
		//Constructor para Ventas cargadas desde la DB
		super(nroVenta, p, c, cantidad, montoUnitario, montoComision, estadoPago, fechaDeCompra);
	}
	
	public Efectivo(Publicacion p, UsuarioRegular c, int cantidad, float montoUnitario) {
		//Constructor para nuevas Ventas
		super(p, c, cantidad, montoUnitario);
		this.nroVenta = AdmPersistenciaVenta.getInstancia().insertarVenta(this);
		
		AdmUsuarios.getInstancia().cargarMovCtaCte(c, -(montoUnitario * cantidad), "COMPRA", this);
		confirmarPago();
	}
}
