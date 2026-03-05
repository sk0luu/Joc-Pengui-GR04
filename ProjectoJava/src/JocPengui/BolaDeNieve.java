package JocPengui;

public abstract class BolaDeNieve extends Item {
	//Constructor de BolaDeNieve que sirve para ponerle un nombre y la cantidad de Bola de nieve
	public BolaDeNieve(String nombre, int cantidad) {
		super(nombre, cantidad);
	}
	
	//Este metodo devuelve el nombre y la cantidad de bolas de nieve
	@Override
	public String toString() {
		return nombre + " | Cantidad: " + cantidad;
	}
}