package jocpinguiFinal.Model;
import java.util.ArrayList;

/**
 * casilla de agujero que penaliza al jugador haciendolo retroceder o perder su avance.
 */
public class Agujero extends Casilla{
    // constructor para situar el agujero
    public Agujero(int posicion) {
        super(posicion);
    }
    // al caer en un agujero, el jugador vuelve al agujero anterior o al inicio
    @Override
    // metodo encargado de la funcion realizaraccion recibiendo parametros: Partida partida, Jugador jugador
    public void realizarAccion(Partida partida, Jugador jugador) {
		// variable que guarda informacion sobre destino
		int destino = 0;
		// variable que guarda informacion sobre posactualhoyo
		int posActualHoyo = this.getPosicion();
		
		// Buscar el agujero anterior en el tablero
		ArrayList<Casilla> casillas = partida.getTablero().getCasillas();
		// variable que guarda informacion sobre mejorpos
		int mejorPos = -1;
		
		for (Casilla c : casillas) {
			if (c instanceof Agujero && c.getPosicion() < posActualHoyo) {
				if (c.getPosicion() > mejorPos) {
					mejorPos = c.getPosicion();
				}
			}
		}
		
		if (mejorPos != -1) {
			destino = mejorPos;
		}
		
		jugador.setPosicion(destino);
		
		if (destino == 0) {
			System.out.println("¡Has caido al primer agujero! Vuelves al inicio.");
		} else {
			System.out.println("¡Has caido a un agujero! Vuelves al agujero anterior en la casilla " + destino + ".");
		}
	}
}