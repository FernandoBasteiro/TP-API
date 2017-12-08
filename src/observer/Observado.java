package observer;

import java.util.ArrayList;
import java.util.List;

public class Observado {
		private List<IObserver> observadores = new ArrayList<IObserver>();
		
		public void registrarObservador(IObserver obs){
			this.observadores.add (obs);
		}
		
		public void quitarObservador(IObserver obs){
			this.observadores.remove(obs);
		}
		
		public void actualizarObservadores(){
			for (IObserver obs : this.observadores){
				obs.actualizar();
			}
		}

}
