package JocPengui;

public class Agujero extends Casilla{
	//Constructor de agujero para que se establezca en una posicion
	public Agujero(int posicion) {
		super(posicion);
	}
	//Este metodo hace que la casilla se reemplace por un agujero y el jugador caiga a un agujero y vuelva al principio del juego
	@Override
	public void realizarAccion(Partida partida, Jugador jugador) {
		jugador.setPosicion(0);
		System.out.println("¡Has caido a un agujero!.");
	}
}