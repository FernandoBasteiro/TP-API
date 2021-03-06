package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import persistencia.AdmPersistenciaMovCtaCte;
import controlador.MovCtaCteView;

public class MovCtaCte {
	private float monto;
	private String concepto;
	private Venta venta;
	private int nroMovimiento;
	private LocalDateTime fechaMovimiento;
	
	public MovCtaCte(String nombreDeUsuario, Venta venta, float monto, String concepto) {
		this.monto = monto;
		this.concepto = concepto;
		this.venta = venta;
		this.fechaMovimiento = LocalDateTime.now();
		this.nroMovimiento = AdmPersistenciaMovCtaCte.getInstancia().insert(nombreDeUsuario, this);
	}
	
	public MovCtaCte(int nroMovimiento, Venta venta, float monto, String concepto, LocalDateTime fechaMovimiento) {
		this.nroMovimiento = nroMovimiento;
		this.fechaMovimiento = fechaMovimiento;
		this.monto = monto;
		this.concepto = concepto;
		this.venta = venta;
	}
	
	public MovCtaCteView getMovCtaCteView(){
		return (new MovCtaCteView(this.monto, this.concepto,this.venta));
	}
	
	public float getMonto() {
		return monto;
	}

	public String getConcepto() {
		return concepto;
	}

	public Venta getVenta() {
		return venta;
	}

	public int getNroMovimiento() {
		return nroMovimiento;
	}

	public LocalDateTime getFechaMovimiento() {
		return fechaMovimiento;
	}
	
	static public ArrayList<MovCtaCte> buscarMovimientosDB(String nombreDeUsuario) {
		return AdmPersistenciaMovCtaCte.getInstancia().buscarMovimientos(nombreDeUsuario);
	}
	
	public boolean sosComision(){
		return (concepto.equals("COMISION"));
	}
	
}
