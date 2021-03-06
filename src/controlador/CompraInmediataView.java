package controlador;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CompraInmediataView extends PublicacionView {
	private int stock;

	public CompraInmediataView(String tipoPublicacion, String nombreProducto,
			String descripcion, LocalDateTime fechaPublicacion,
			ArrayList<String> imagenes, float precioActual,
			String estadoPublicacion, int numPublicacion, int stock, String nombreVendedor, boolean soyDueno) {
		super(tipoPublicacion, nombreProducto, descripcion, fechaPublicacion, imagenes,
				precioActual, estadoPublicacion, numPublicacion, nombreVendedor, soyDueno);
		this.stock = stock;
	}

	public int getStock() {
		return stock;
	}

	@Override
	public LocalDateTime getFechaHasta() {
		return null;
	}

}
