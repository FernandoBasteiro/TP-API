package controlador;

import modelo.Venta;

public class MovCtaCteView {
	private int numero;
	private float monto;
	private String concepto;
	private Venta venta;
	
	public MovCtaCteView(float monto, String concepto) {
		super();
		this.monto = monto;
		this.concepto = concepto;
	}
	
	public float getMonto() {
		return monto;
	}
	
	public String getConcepto() {
		return concepto;
	}
	
	

}


