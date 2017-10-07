package modelo;

import java.time.LocalDateTime;

public abstract class Venta {
	protected Publicacion publicacion;
	protected Usuario comprador;
	protected float montoPagado;
	protected float montoComision;
	protected String estadoPago;
	protected LocalDateTime fechaDeCompra;
	static private float porcentajeComision;
	
	public Venta(Publicacion p, Usuario c, float montoPagado, String estadoPago) {
		this.publicacion = p;
		this.comprador = c;
		this.montoPagado = montoPagado;
		this.montoComision = montoPagado * (Venta.getPorcentajeComision() / 100);
		this.estadoPago = estadoPago;
		this.fechaDeCompra = LocalDateTime.now();
		//AdmPersistenciaVentas.insertVenta(this);
	}
	
	public Venta(Publicacion p, Usuario c, float montoPagado, float montoComision, String estadoPago, LocalDateTime fechaDeCompra) {
		this.publicacion = p;
		this.comprador = c;
		this.montoPagado = montoPagado;
		this.montoComision = montoComision;
		this.estadoPago = estadoPago;
		this.fechaDeCompra = fechaDeCompra;
	}
	
	public Publicacion getPublicacion() {
		return publicacion;
	}
	
	public static float getPorcentajeComision(){
		return porcentajeComision;
	}
}
