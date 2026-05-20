package jocpinguiFinal.Model;

/**
 * casilla de meta. el jugador que llega a esta casilla gana la partida.
 */
public class Final extends Casilla {
    // metodo encargado de la funcion final recibiendo parametros: int posicion
    public Final(int posicion) {
        super(posicion);
    }

    @Override
    // metodo encargado de la funcion realizaraccion recibiendo parametros: Partida partida, Jugador jugador
    public void realizarAccion(Partida partida, Jugador jugador) {
        // La casilla final no realiza acción extra, la victoria la comprueba el Gestor.
        System.out.println("¡Has llegado a la meta épica!");
    }
}
