package persistencia;

public class AdmPersistenciaVentaMySQL {
	static private AdmPersistenciaVentaMySQL instancia;
	
	static public AdmPersistenciaVentaMySQL getInstancia(){
		if (instancia != null) {
			instancia = new AdmPersistenciaVentaMySQL();
		}
		return instancia;
	}
	
	private AdmPersistenciaVentaMySQL() {
		
	}
	
	
	

}
