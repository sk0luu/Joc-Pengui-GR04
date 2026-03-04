package JocPengui;

public class Trineo extends Casilla{

	public Trineo(int posicion) {
		super(posicion);
	}
	@Override
	public void realizarAccion(Partida partida, Jugador jugador) {
		jugador.moverPosicion(4);
	}
}
