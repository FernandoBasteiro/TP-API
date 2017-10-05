package controlador;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SubastaView extends PublicacionView {
	private LocalDateTime fechaHasta;

	public SubastaView(String tipoPublicacion, String nombreProducto,
			String descripcion, LocalDateTime fechaPublicacion,
			ArrayList<String> imagenes, float precioActual,
			String estadoPublicacion, int numPublicacion, LocalDateTime fechaHasta) {
		super(tipoPublicacion, nombreProducto, descripcion, fechaPublicacion, imagenes,
				precioActual, estadoPublicacion, numPublicacion);
		this.fechaHasta = fechaHasta;
	}

	public LocalDateTime getFechaHasta() {
		return fechaHasta;
	}

	@Override
	public int getStock() {
		return 0;
	}
	
	

}
