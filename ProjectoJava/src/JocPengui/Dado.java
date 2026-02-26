package JocPengui;
import java.util.Random;
public class Dado {
	private int max;
	private int min;
	public Dado(int max, int min) {
		this.max = max;
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	
	public int tirar() {
		Random r = new Random();
		int numeroRandom = r.nextInt(min) + max;
		return numeroRandom;
		
	}
}
