package dad.bingo.modelo;

import java.util.ArrayList;
import java.util.List;

public class Panel {
	private List<Bola> bolas;

	public Panel() {
		bolas = new ArrayList<Bola>();
	}
	
	public void colocarBola(Bola bola) {
		bolas.add(bola);
	}
	
	public void vaciar() {
		bolas.clear();
	}
	
	public final List<Bola> getBolas() {
		return bolas;
	}
	
}
