package controlador;

import java.time.LocalDateTime;

import modelo.Efectivo;
import modelo.MercadoPago;
import modelo.TransfBancaria;
import modelo.Venta;

public class VentaView {
	private String nombreProducto;
	private int nroVenta;
	private float precioUnidad;
	private int cantidad;
	private float comisionPagada;
	private String medioDePago;
	private LocalDateTime fechaDeCompra;
	private String estadoCompra;
	public String getNombreProducto() {
		return nombreProducto;
	}
	public int getNroVenta() {
		return nroVenta;
	}
	public float getPrecioUnidad() {
		return precioUnidad;
	}
	public int getCantidad() {
		return cantidad;
	}
	public float getComisionPagada() {
		return comisionPagada;
	}
	public String getMedioDePago() {
		return medioDePago;
	}
	public LocalDateTime getFechaDeCompra() {
		return fechaDeCompra;
	}
	public String getEstadoCompra() {
		return estadoCompra;
	}
	public VentaView(Venta v) {
		this.nombreProducto = v.getPublicacion().getNombre();
		this.nroVenta = v.getNroVenta();
		this.precioUnidad = v.getMontoUnitario();
		this.cantidad = v.getCantidad();
		this.comisionPagada = v.getMontoComision();
		if (v instanceof Efectivo) {
			this.medioDePago = "Efectivo";
		}
		else if (v instanceof TransfBancaria) {
			this.medioDePago = "Transferencia Bancaria";
		}
		else if (v instanceof MercadoPago) {
			this.medioDePago = "MercadoPago";
		}
		else {
			this.medioDePago = "Otro";
		}
		this.fechaDeCompra = v.getFechaDeCompra();
		this.estadoCompra = v.getEstadoPago();
	}	
}
