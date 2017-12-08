package controlador;

import java.util.ArrayList;

import modelo.UsuarioRegular;

public class AppMobile {
	
	public int login (String nombreDeUsuario, String passwordString) {
		return AdmUsuarios.getInstancia().login(nombreDeUsuario, passwordString);
	}
	
	public ArrayList<MovCtaCteView> getMovsCtaCteView(){
		return AdmUsuarios.getInstancia().getMovsCtaCteView();
	}
	
	public ArrayList<MovCtaCteView> getComisionesPagadasView(){
		return AdmUsuarios.getInstancia().getComisionesPagadasView();
	}
	
	public ArrayList<CalificacionView> getCalificacionesPendientesView() {
		return AdmUsuarios.getInstancia().getCalificacionesPendientesView();
	}
	
	public ArrayList<CalificacionView> getCalificacionesView() {
		return AdmUsuarios.getInstancia().getCalificacionesView();
	}
	
	public ArrayList<VentaView> obtenerListadoCompras() {
		return AdmUsuarios.getInstancia().obtenerListadoCompras();
	}
	
	public ArrayList<VentaView> obtenerListadoVentas() {
		return AdmUsuarios.getInstancia().obtenerListadoVentas();
	}
}
