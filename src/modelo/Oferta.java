package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import persistencia.AdmPersistenciaOfertas;

public class Oferta {
	private float monto;
	private Usuario comprador;
	private String medioDePago;
	private LocalDateTime fechaOferta;
	
	public Oferta(float monto, Usuario comprador, String medioDePago) {
		super();
		this.monto = monto;
		this.comprador = comprador;
		this.medioDePago = medioDePago;
		this.fechaOferta = LocalDateTime.now();
	}
	
	public Oferta(float monto, Usuario comprador, String medioDePago, LocalDateTime fechaOferta) {
		this.monto = monto;
		this.comprador = comprador;
		this.medioDePago = medioDePago;
		this.fechaOferta = fechaOferta;
	}
	
	public LocalDateTime getFechaOferta() {
		return fechaOferta;
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
	
	public int insertOferta(int nroPublicacion) {
		return AdmPersistenciaOfertas.getInstancia().insertOferta(this, nroPublicacion);
	}
	
	static public Oferta buscarMayorOferta(int nroPublicacion){
		return AdmPersistenciaOfertas.getInstancia().getMayorOferta(nroPublicacion);
	}
	
	static public ArrayList<Oferta> buscarOfertas(int nroPublicacion) {
		return AdmPersistenciaOfertas.getInstancia().getOfertas(nroPublicacion);
	}
	

}
