package dad.bingo.utils;

public class Aleatorio {
	
	public static Integer siguienteEntero(Integer minimo, Integer maximo) {
		Double aleatorio = Math.random(); 
		return (int)Math.round(((maximo - minimo) * aleatorio) + minimo);
	}

}
