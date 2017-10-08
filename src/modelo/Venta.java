package modelo;

import java.time.LocalDateTime;

import controlador.AdmUsuarios;
import controlador.SistPublicaciones;

public abstract class Venta {
	protected int nroVenta;
	protected int cantidad;
	protected Publicacion publicacion;
	protected Usuario comprador;
	protected float montoUnitario;
	protected float montoComision;
	protected String estadoPago;
	protected LocalDateTime fechaDeCompra;
	static private float porcentajeComision = 5;
	
	public Venta(Publicacion p, Usuario c, int cantidad, float montoUnitario) {
		this.publicacion = p;
		this.comprador = c;
		this.cantidad = cantidad;
		this.montoUnitario = montoUnitario;
		this.montoComision = montoUnitario * cantidad * (Venta.getPorcentajeComision() / 100);
		this.estadoPago = "Pendiente";
		this.fechaDeCompra = LocalDateTime.now();
		//nroVenta = AdmPersistenciaVentas.insertVenta(this);
		
		System.out.println(p.getNombre() + " vendido");
	}
	
	public int getNroVenta() {
		return nroVenta;
	}

	public Venta(int nroVenta, Publicacion p, Usuario c, int cantidad, float montoUnitario, float montoComision, String estadoPago, LocalDateTime fechaDeCompra) {
		this.nroVenta = nroVenta;
		this.publicacion = p;
		this.comprador = c;
		this.cantidad = cantidad;
		this.montoUnitario = montoUnitario;
		this.montoComision = montoComision;
		this.estadoPago = estadoPago;
		this.fechaDeCompra = fechaDeCompra;
	}

	public static float getPorcentajeComision() {
		return porcentajeComision;
	}

	public static void setPorcentajeComision(float porcentajeComision) {
		Venta.porcentajeComision = porcentajeComision;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public Usuario getComprador() {
		return comprador;
	}

	public float getMontoUnitario() {
		return montoUnitario;
	}

	public float getMontoComision() {
		return montoComision;
	}

	public String getEstadoPago() {
		return estadoPago;
	}

	public LocalDateTime getFechaDeCompra() {
		return fechaDeCompra;
	}
	
	public int confirmarPago() {
		this.estadoPago = "Pagado";
		AdmUsuarios.getInstancia().cargarMovCtaCte(publicacion.getVendedor(), (montoUnitario * cantidad) - montoComision, "Venta de " + publicacion.getNombre(), this);
		//TODO Generar Calificacion para Comprador y Vendedor.
		//TODO Persistir
		return 0;
	}
	
	public int rechazarPago() {
		this.estadoPago = "Rechazado";
		//TODO Si es CompraInmediata -> Devolver el Stock.
		//TODO Persisitir
		return 0;
	}
}
