package jocpinguiFinal.Model;

/**
 * casilla estandar del tablero, no tiene ningun efecto o evento especial.
 */
public class Normal extends Casilla {
	// Casilla normal, no hace nada especial
	public Normal(int posicion) {
		super(posicion);
	}

	@Override
	// metodo encargado de la funcion realizaraccion recibiendo parametros: Partida partida, Jugador jugador
	public void realizarAccion(Partida partida, Jugador jugador) {
		// Casilla normal: no pasa nada
	}
}
