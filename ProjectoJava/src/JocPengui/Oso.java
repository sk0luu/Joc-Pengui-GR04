package JocPengui;

public class Oso extends Casilla {
	public Oso(int posicion) {
		super(posicion);
	}
	@Override
    public void realizarAccion(Partida partida, Jugador jugador) {
        jugador.moverPosicion(-3);
    }
}