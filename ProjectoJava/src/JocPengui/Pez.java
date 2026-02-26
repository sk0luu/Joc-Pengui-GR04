package JocPengui;

public abstract class Pez extends Item{
	public Pez(String nombre, int cantidad) {
		super(nombre, cantidad);
	}
	@Override
	public String toString() {
		return " Pez: " + nombre + " | Cantidad: " + cantidad;
	}
}
