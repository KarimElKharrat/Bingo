package dad.bingo.modelo;

public class Bola {
	private Integer numero;
	
	public Bola() {
	}
	
	public Bola(Integer numero) {
		this.numero = numero;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Bola) {
			Bola bola = (Bola) obj;
			return this.numero.equals(bola.getNumero());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Bola[" + numero + "]";
	}

}
