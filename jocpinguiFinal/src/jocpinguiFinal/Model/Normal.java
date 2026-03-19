package jocpinguiFinal.Model;

public class Normal extends Casilla {
	// Casilla normal, no hace nada especial
	public Normal(int posicion) {
		super(posicion);
	}

	@Override
	public void realizarAccion(Partida partida, Jugador jugador) {
		// Casilla normal: no pasa nada
	}
}
