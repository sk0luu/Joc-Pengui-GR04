package jocpinguiFinal.Model;

/**
 * item o evento que sirve para ralentizar o congelar el turno de un rival.
 */
public abstract class BolaDeNieve extends Item {
	//Constructor de BolaDeNieve que sirve para ponerle un nombre y la cantidad de Bola de nieve
	public BolaDeNieve(String nombre, int cantidad) {
		super(nombre, cantidad);
	}
	
	
	
	//Este metodo devuelve el nombre y la cantidad de bolas de nieve
	@Override
	// metodo encargado de la funcion tostring recibiendo parametros: ninguno
	public String toString() {
		return nombre + " | Cantidad: " + cantidad;
	}
}