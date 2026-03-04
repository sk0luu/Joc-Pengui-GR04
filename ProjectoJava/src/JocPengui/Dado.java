package JocPengui;
import java.util.Random;
public class Dado {
	Random r = new Random();
	public int tirar() {
		
		int random = r.nextInt(6) + 1;
		return random;
	}
}