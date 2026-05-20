package jocpinguiFinal.Controlador;

import jocpinguiFinal.*;
import jocpinguiFinal.Model.Casilla;
import jocpinguiFinal.Model.Jugador;
import jocpinguiFinal.Model.Partida;
import jocpinguiFinal.Model.Pinguino;

/**
 * controla la logica del tablero y la ejecucion de eventos en las casillas.
 */
public class GestorTablero {

    // metodo encargado de la funcion ejecutacasilla recibiendo parametros: Partida partida, Jugador p, Casilla c
    public void ejecutaCasilla(Partida partida, Jugador p, Casilla c) {
        if (c != null) {
            c.realizarAccion(partida, p);
        }
    }

    // metodo encargado de la funcion comprobarfinturno recibiendo parametros: Partida partida
    public void comprobarFinTurno(Partida partida) {
        for (Jugador j : partida.getJugador()) {
            if (j.getPosicion() >= 49) {
                partida.setFinalizado(true);
                partida.setGanador(j);
                System.out.println("¡El jugador " + j.getNom() + " ha ganado la partida!");
            }
        }
    }
}