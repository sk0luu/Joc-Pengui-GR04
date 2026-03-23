package jocpinguiFinal.Model;
import java.util.Random;
public class Dado extends Item {
	private int min;
	private int max;

	public Dado() {
		super("Dado", 1);
		this.min = 1;
		this.max = 6;
	}

	public Dado(int min, int max) {
		super("Dado", 1);
		this.min = min;
		this.max = max;
	}

	public int getMin() { return min; }
	public void setMin(int min) { this.min = min; }
	public int getMax() { return max; }
	public void setMax(int max) { this.max = max; }

	// Mantenemos este para compatibilidad
	public int tirar() {
		return tirar(new Random());
	}

	public int tirar(Random r) {
		int random = r.nextInt((max - min) + 1) + min;
		System.out.println("Has sacado un " + random + " en el dado");
		return random;
	}
}