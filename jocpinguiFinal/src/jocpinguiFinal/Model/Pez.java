package jocpinguiFinal.Model;

/**
 * item especial que da ventajas al pinguino, como avanzar posiciones adicionales.
 */
public abstract class Pez extends Item{
	//Este constructor le pone nombre al pez y la cantidad de peces
	public Pez(String nombre, int cantidad) {
		super(nombre, cantidad);
	}
	//Este metodo muestra el nombre del pez y la cantidad
	@Override
	// metodo encargado de la funcion tostring recibiendo parametros: ninguno
	public String toString() {
		return " Pez: " + nombre + " | Cantidad: " + cantidad;
	}
}