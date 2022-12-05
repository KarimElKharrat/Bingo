package dad.bingo.modelo;

import java.util.ArrayList;
import java.util.List;

public class Bingo {
	private Bombo bombo;
	private Panel panel;

	public Bingo() {
		bombo = new Bombo();
		panel = new Panel();
	}
	
	public Bola sacarBola() {
		if (!bombo.hayBolas()) return null;
		Bola bola = bombo.sacarBola();
		panel.colocarBola(bola);
		return bola;
	}
	
	public void reiniciar() {
		panel.vaciar();
		bombo.llenar();
	}

	public Boolean quedanBolas() {
		return bombo.hayBolas();
	}

	public final Panel getPanel() {
		return panel;
	}
	
	public Boolean validar(List<Integer> numeros) {
		List<Bola> bolas = new ArrayList<Bola>();
		for (Integer numero : numeros) {
			bolas.add(new Bola(numero));
		}
		return panel.getBolas().containsAll(bolas);
	}

}
