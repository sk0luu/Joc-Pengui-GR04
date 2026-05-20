package jocpinguiFinal.Model;
import java.util.Random;
/**
 * simula el lanzamiento de un dado para generar los pasos que debe avanzar un jugador.
 */
public class Dado extends Item {
	// variable que guarda informacion sobre min
	private int min;
	// variable que guarda informacion sobre max
	private int max;

	// metodo encargado de la funcion dado recibiendo parametros: ninguno
	public Dado() {
		super("Dado", 1);
		this.min = 1;
		this.max = 6;
	}

	// metodo encargado de la funcion dado recibiendo parametros: int min, int max
	public Dado(int min, int max) {
		super("Dado", 1);
		this.min = min;
		this.max = max;
	}

	public int getMin() { return min; }
	// metodo que actualiza o establece el valor de min
	public void setMin(int min) { this.min = min; }
	public int getMax() { return max; }
	// metodo que actualiza o establece el valor de max
	public void setMax(int max) { this.max = max; }

	// Mantenemos este para compatibilidad
	public int tirar() {
		// metodo encargado de la funcion tirar recibiendo parametros: new Random()
		return tirar(new Random());
	}

	// metodo encargado de la funcion tirar recibiendo parametros: Random r
	public int tirar(Random r) {
		// variable que guarda informacion sobre random
		int random = r.nextInt((max - min) + 1) + min;
		System.out.println("Has sacado un " + random + " en el dado");
		return random;
	}
}