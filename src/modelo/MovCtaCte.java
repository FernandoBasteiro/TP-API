package modelo;

import controlador.MovCtaCteView;

public class MovCtaCte {
	private float monto;
	private String concepto;
	private Venta venta;
	
	public MovCtaCte(Venta venta, float monto, String concepto) {
		super();
		this.monto = monto;
		this.concepto = concepto;
		this.venta = venta;
	}
	
	public MovCtaCteView getMovCtaCteView(){
		return (new MovCtaCteView(this.monto, this.concepto));
	}
	
}
