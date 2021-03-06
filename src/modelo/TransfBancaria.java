package modelo;

import java.time.LocalDateTime;

import persistencia.AdmPersistenciaVenta;

public class TransfBancaria extends Venta{
	private String CBU;
	
	public TransfBancaria(int nroVenta, Publicacion p, Usuario c, int cantidad, float montoPagado,
			float montoComision, String estadoPago, LocalDateTime fechaDeCompra, String CBU) {
		//Constructor para Ventas cargadas desde la DB
		super(nroVenta, p, c, cantidad, montoPagado, montoComision, estadoPago, fechaDeCompra);
		this.CBU = CBU;
	}
	
	public TransfBancaria(Publicacion p, UsuarioRegular c, int cantidad, float montoPagado, String CBU) {
		//Constructor para nuevas Ventas
		super(p, c, cantidad, montoPagado);
		this.CBU = CBU;
		this.nroVenta = AdmPersistenciaVenta.getInstancia().insertarVenta(this);
		System.out.println("Compra generada via Transferencia Bancaria pendiente de confirmacion de banco. CBU: " + this.CBU);
	}

	public String getCBU() {
		return CBU;
	}
	
	
}
