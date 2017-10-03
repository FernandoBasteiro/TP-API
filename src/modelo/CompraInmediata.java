package modelo;

public class CompraInmediata extends Publicacion {
	private int stock;

	public CompraInmediata(int stock) {
		super();
		this.stock = stock;
	}

	@Override
	public int ofertar(float monto, Usuario comprador, String medioDePago) {
		//TODO Realziar venta.
		return 0;
	}
	
	
}
