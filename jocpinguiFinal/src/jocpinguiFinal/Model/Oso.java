package jocpinguiFinal.Model;

public class Oso extends Casilla {
	//Este constructor recibe de la clase padre la posicion en la que estara
	public Oso(int posicion) {
		super(posicion);
	}
	//Este metodo es la accion que realizara el oso
	@Override
    public void realizarAccion(Partida partida, Jugador jugador) {
        jugador.moverPosicion(-3);
        System.out.println("¡El oso te ha golpeado y has huido 3 casillas!.");
    }
}