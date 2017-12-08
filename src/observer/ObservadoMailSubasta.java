package observer;

import java.util.ArrayList;
import java.util.List;

import modelo.Subasta;

public class ObservadoMailSubasta {
		private List<IObserverMailSubasta> obsMail = new ArrayList<IObserverMailSubasta>();
		
		public void registrarObservador(IObserverMailSubasta obs){
			this.obsMail.add (obs);
		}
		
		public void quitarObservador(IObserverMailSubasta obs){
			this.obsMail.remove(obs);
		}
		
		public void envioMail(Subasta subasta){
			for (IObserverMailSubasta obs : this.obsMail){
				obs.enviarMailSubasta(subasta);
			}
		}

}
