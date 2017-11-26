package controlador;

import java.time.LocalDateTime;

import modelo.Venta;

public class MovCtaCteView {
//	private int numero;
	private float monto;
	private String concepto;
	private Venta venta;
	
	public MovCtaCteView(float monto, String concepto, Venta venta) {
		super();
		this.monto = monto;
		this.concepto = concepto;
		this.venta = venta;
	}
	
	public float getMonto() {
		return monto;
	}
	
	public String getConcepto() {
		return concepto;
	}
	
	public LocalDateTime getFechaCompra() {
		if(venta!=null)
			return venta.getFechaDeCompra();
		return null;
	}
	
	public String getNombreProducto() {
		if(venta!=null && venta.getPublicacion()!=null)
			return venta.getPublicacion().getNombre();
		return null;
	}

	public int getCantidad() {
		if(venta!=null)
			return venta.getCantidad();
		return 0;
	}

}


