package JocPengui;

public class Trineo extends Casilla{
	//Este constructor de trineo sirve para ponerlo en la posicion
	public Trineo(int posicion) {
		super(posicion);
	}
	//Este metodo realiza la accion del trineo
	@Override
	public void realizarAccion(Partida partida, Jugador jugador) {
		jugador.moverPosicion(4);
		System.out.println("¡Has utilizado un trineo y has avanzado 4 casillas!.");
	}
}