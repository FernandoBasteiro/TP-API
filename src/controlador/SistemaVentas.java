package controlador;

import java.util.ArrayList;

import modelo.Venta;

public class SistemaVentas {
	private ArrayList<Venta> ventas;
	static private SistemaVentas instancia;
	
	private SistemaVentas() {
		ventas = new ArrayList<Venta>();
	}
	
	static public SistemaVentas getInstancia(){
		if (instancia == null) {
			instancia = new SistemaVentas();
		}
		return instancia;
	}
	
	public String getNombrePublicacion(Venta v) {
		return SistPublicaciones.getInstancia().getNombrePublicacion(v.getPublicacion());
	}
}
