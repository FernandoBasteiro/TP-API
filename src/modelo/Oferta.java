package modelo;

public class Oferta {
	private float monto;
	private Usuario comprador;
	private String medioDePago;
	
	public Oferta(float monto, Usuario comprador, String medioDePago) {
		super();
		this.monto = monto;
		this.comprador = comprador;
		this.medioDePago = medioDePago;
	}
	
	public float getMonto() {
		return monto;
	}
	public Usuario getComprador() {
		return comprador;
	}
	public String getMedioDePago() {
		return medioDePago;
	}
	
	

}
