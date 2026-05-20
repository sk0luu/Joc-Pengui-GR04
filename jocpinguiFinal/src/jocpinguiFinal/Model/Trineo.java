package jocpinguiFinal.Model;

/**
 * casilla con el evento del trineo, que hace avanzar al jugador varias posiciones extra.
 */
public class Trineo extends Casilla{
    // constructor para situar el trineo
    public Trineo(int posicion) {
        super(posicion);
    }
    // al usar un trineo, el jugador avanza automaticamente hasta el siguiente trineo
    @Override
    // metodo encargado de la funcion realizaraccion recibiendo parametros: Partida partida, Jugador jugador
    public void realizarAccion(Partida partida, Jugador jugador) {
		// variable que guarda informacion sobre posactual
		int posActual = this.getPosicion();
		// variable que guarda informacion sobre siguientetrineopos
		int siguienteTrineoPos = -1;
		
		java.util.ArrayList<Casilla> casillas = partida.getTablero().getCasillas();
		// variable que guarda informacion sobre encontrado
		boolean encontrado = false;
		for (int i = 0; i < casillas.size() && !encontrado; i++) {
			// variable que guarda informacion sobre c
			Casilla c = casillas.get(i);
			if (c instanceof Trineo && c.getPosicion() > posActual) {
				siguienteTrineoPos = c.getPosicion();
				encontrado = true;
			}
		}

		if (siguienteTrineoPos != -1) {
			jugador.setPosicion(siguienteTrineoPos);
			System.out.println("¡Has utilizado un trineo y has avanzado hasta el siguiente en la casilla " + siguienteTrineoPos + "!");
		} else {
			System.out.println("Es el último trineo del tablero. ¡Disfruta del paisaje!");
		}
	}
}