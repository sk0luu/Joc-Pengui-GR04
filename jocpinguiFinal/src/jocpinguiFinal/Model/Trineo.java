package jocpinguiFinal.Model;

public class Trineo extends Casilla{
    // constructor para situar el trineo
    public Trineo(int posicion) {
        super(posicion);
    }
    // al usar un trineo, el jugador avanza automaticamente hasta el siguiente trineo
    @Override
    public void realizarAccion(Partida partida, Jugador jugador) {
		int posActual = this.getPosicion();
		int siguienteTrineoPos = -1;
		
		java.util.ArrayList<Casilla> casillas = partida.getTablero().getCasillas();
		boolean encontrado = false;
		for (int i = 0; i < casillas.size() && !encontrado; i++) {
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