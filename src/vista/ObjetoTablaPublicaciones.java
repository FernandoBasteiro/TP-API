package vista;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ObjetoTablaPublicaciones extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8596469549579315436L;
	private List<String[]> filas;
	
	public ObjetoTablaPublicaciones() {
		filas=new ArrayList<>();
	}

	@Override
	public int getRowCount() {
		return filas.size();
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex){
            case 0:
                return "TipoPublicacion";
            case 1:
                return "Producto";
            case 2:
                return "Descripcion";
            case 3:
                return "Precio";
            case 4:
                return "Estado";
	    		case 5:
	    			return "numPublicacion";
        }
        return "";  
    }
    
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String[] fila = filas.get(rowIndex);
		return fila[columnIndex];
	}

	public void addRow(int numPublicacion,String tipoPublicacion,String nombreProducto,String descripcion,float precioActual,String estadoPublicacion) {
        int rowCount = getRowCount();
        String[] fila = new String[getColumnCount()];
        fila[0] = tipoPublicacion;
        fila[1] = nombreProducto;
        fila[2] = descripcion;
        fila[3] = String.valueOf(precioActual);
        fila[4] = estadoPublicacion;
        fila[5] = String.valueOf(numPublicacion);
        filas.add(fila);
        fireTableRowsInserted(rowCount, rowCount);
    }  

	public void remRow(int numero) {
        filas.remove(numero);
        int rowCount = getRowCount();
        fireTableRowsInserted(rowCount, rowCount);
    }

	public void remAll() {
        filas.clear();
        int rowCount = getRowCount();
        fireTableRowsInserted(rowCount, rowCount);
    }
}
