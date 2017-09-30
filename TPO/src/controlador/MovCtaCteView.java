package controlador;

public class MovCtaCteView {
	private float monto;
	private String concepto;
	
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


