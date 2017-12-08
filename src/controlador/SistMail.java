package controlador;

import modelo.Subasta;
import observer.IObserverMailSubasta;

public class SistMail implements IObserverMailSubasta{
	private static SistMail admMail;
	
	private SistMail() {
		
	}
	
	public static SistMail getInstance() {
		if(admMail==null)
			admMail=new SistMail();
		return admMail;
	}

	@Override
	public void enviarMailSubasta(Subasta subasta) {
		for (String mail : subasta.getMailsCompradores()) {
			System.out.println("Mail enviado a " + mail + " - Oferta superada para producto " + subasta.getNombre() + " - Nueva oferta: " + subasta.getPrecioActual());
		}
		
	}
	
}
