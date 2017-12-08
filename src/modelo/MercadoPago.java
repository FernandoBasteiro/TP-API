package modelo;

import java.time.LocalDateTime;

import persistencia.AdmPersistenciaVenta;

public class MercadoPago extends Venta{
	private String nroTarjeta;
	
	public MercadoPago(int nroVenta, Publicacion p, Usuario c, int cantidad, float montoPagado,
			float montoComision, String estadoPago, LocalDateTime fechaDeCompra, String nroTarjeta) {
		//Constructor para Ventas cargadas desde la DB
		super(nroVenta, p, c, cantidad, montoPagado, montoComision, estadoPago, fechaDeCompra);
		this.nroTarjeta = nroTarjeta;
	}
	
	public MercadoPago(Publicacion p, UsuarioRegular c, int cantidad, float montoPagado, String nroTarjeta) {
		//Constructor para nuevas Ventas
		super(p, c, cantidad, montoPagado);
		this.nroTarjeta = nroTarjeta;
		this.nroVenta = AdmPersistenciaVenta.getInstancia().insertarVenta(this);
		System.out.println("Compra generada por Tarjeta de Credito pendiente de confirmacion de Mercado Pago. NroTarjeta: " + this.nroTarjeta);
	}

	public String getNroTarjeta() {
		return nroTarjeta;
	}
	
	
}
