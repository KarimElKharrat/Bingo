package dad.bingo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Voces {

	public static void reproducir(Integer numero) {
		URL url = Voces.class.getClassLoader().getResource("audios/" + numero + ".WAV");
		InputStream stream;
		try {
			stream = url.openStream();
			new PlayWave(stream).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
