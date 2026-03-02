package JocPengui;

public class Agujero extends Casilla{

	public Agujero(int posicion) {
		super(posicion);
	}
	@Override
	public void realizarAccion(Partida partida, Jugador jugador) {
		jugador.setPosicion(0);
	}
}