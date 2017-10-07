package controlador;

public class UsuarioLogueadoView {
	private String nombre;
	private float estadoCtaCte;
	private int calificacionesPendientes;
	public UsuarioLogueadoView(String nombre, float estadoCtaCte,
			int calificacionesPendientes) {
		super();
		this.nombre = nombre;
		this.estadoCtaCte = estadoCtaCte;
		this.calificacionesPendientes = calificacionesPendientes;
	}
	
	public String getNombre() {
		return nombre;
	}
	public float getEstadoCtaCte() {
		return estadoCtaCte;
	}
	public int getCalificacionesPendientes() {
		return calificacionesPendientes;
	}
	
	
}
