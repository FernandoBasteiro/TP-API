package modelo;

import java.util.ArrayList;

import controlador.MovCtaCteView;

public class CtaCte {
	private float saldoTotal;
	private ArrayList<MovCtaCte> movimientos;
	
	public CtaCte() {
		this.saldoTotal = 0;
		movimientos = new ArrayList<MovCtaCte>();
	}
	
	public int generarMovimiento(Venta venta, float monto, String concepto) {
		MovCtaCte movimiento = new MovCtaCte(venta, monto, concepto);
		this.movimientos.add(movimiento);
		saldoTotal = saldoTotal + monto;
		return 0;
	}

	public float getSaldoTotal() {
		return saldoTotal;
	}

	public ArrayList<MovCtaCteView> getMovsCtaCteView() {
		ArrayList<MovCtaCteView> movsView = new ArrayList<MovCtaCteView>();
		for (int i = 0; i < movimientos.size(); i++) {
			movsView.add(movimientos.get(i).getMovCtaCteView());
		}
		return movsView;
	}
	
}
