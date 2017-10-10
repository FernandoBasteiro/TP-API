package modelo;

import java.util.ArrayList;

import persistencia.AdmPersistenciaMovCtaCteMySQL;
import controlador.MovCtaCteView;

public class CtaCte {
	private float saldoTotal;
	private ArrayList<MovCtaCte> movimientos;
	
	public CtaCte() {
		this.saldoTotal = 0;
		movimientos = null;
	}
	
	public CtaCte(float saldo) {
		this.saldoTotal = saldo;
		movimientos = null;
	}
	
	public int generarMovimiento(String nombreDeUsuario, Venta venta, float monto, String concepto) {
		MovCtaCte movimiento = new MovCtaCte(nombreDeUsuario, venta, monto, concepto);
		if (movimiento.getNroMovimiento() != 0) {
			if (this.movimientos == null) {
				movimientos = new ArrayList<MovCtaCte>();
			}
			this.movimientos.add(movimiento);
			saldoTotal = saldoTotal + monto;
			return 0;
		}
		else {
			return 1;
		}	
	}

	public float getSaldoTotal() {
		return saldoTotal;
	}

	public ArrayList<MovCtaCteView> getMovsCtaCteView(String nombreDeUsuario) {
		this.movimientos = AdmPersistenciaMovCtaCteMySQL.getInstancia().buscarMovimientos(nombreDeUsuario);
		movimientos = MovCtaCte.buscarMovimientosDB(nombreDeUsuario);
		ArrayList<MovCtaCteView> movsView = new ArrayList<MovCtaCteView>();
		for (int i = 0; i < movimientos.size(); i++) {
			movsView.add(movimientos.get(i).getMovCtaCteView());
		}
		return movsView;
	}
	
}
