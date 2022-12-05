package dad.bingo.modelo;

import java.util.ArrayList;
import java.util.List;

import dad.bingo.utils.Aleatorio;

public class Bombo {
	private static final Integer NUM_BOLAS = 90; 
	
	private List<Bola> bolas;

	public Bombo() {
		this.bolas = new ArrayList<Bola>();
		llenar();
	}
	
	public Boolean hayBolas() {
		return !this.bolas.isEmpty();
	}
	
	public void llenar() {
		this.bolas.clear();
		for (int i = 1; i <= NUM_BOLAS; i++) {
			Bola bola = new Bola(i);
			this.bolas.add(bola);
		}
	}
	
	public Bola sacarBola() {
		Integer posicion = Aleatorio.siguienteEntero(0, this.bolas.size() - 1);
		Bola bola = this.bolas.get(posicion);
		this.bolas.remove(bola);
		return bola;
	}
	
}
