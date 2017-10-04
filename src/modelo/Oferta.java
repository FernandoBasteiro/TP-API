//Completar constructor para poder matchear llamada desde subasta

package modelo;

public class Oferta {
	
	private float monto;
	private Usuario comprador;
	private String medioDePago;
	
	
	public Oferta(float monto, Usuario comprador, String medioDePago) {
		
		this.monto = monto;
		this.comprador = comprador;
		this.medioDePago = medioDePago;
		
	}

}
