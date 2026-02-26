package JocPengui;

public abstract class BolaDeNieve extends Item {
	public BolaDeNieve(String nombre, int cantidad) {
		super(nombre, cantidad);
	}
	@Override
	public String toString() {
		return nombre + " | Cantidad: " + cantidad;
	}
}