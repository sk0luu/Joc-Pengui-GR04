package jocpinguiFinal.Controlador;

import jocpinguiFinal.*;
import jocpinguiFinal.Model.Foca;
import jocpinguiFinal.Model.Jugador;
import jocpinguiFinal.Model.Pinguino;
import jocpinguiFinal.Model.Tablero;

/**
 * gestiona toda la logica relacionada con los jugadores (turnos, movimientos, estados).
 */
public class GestorJugador {

    // metodo encargado de la funcion jugadorvuelve recibiendo parametros: String nombreItem
    public void jugadorVuelve(String nombreItem) {
        System.out.println("El jugador vuelve con " + nombreItem);
    }

    // metodo encargado de la funcion jugadorsemueve recibiendo parametros: Jugador j, int pasos, Tablero t
    public void jugadorSeMueve(Jugador j, int pasos, Tablero t) {
        j.moverPosicion(pasos);
        if (j.getPosicion() < 0) {
            j.setPosicion(0);
        }
    }

    // metodo encargado de la funcion jugadorfinalizaturno recibiendo parametros: Jugador j
    public void jugadorFinalizaTurno(Jugador j) {
        System.out.println("El jugador " + j.getNom() + " ha finalizado su turno");
    }

    // metodo encargado de la funcion pinguinovuelve recibiendo parametros: Pinguino p
    public void pinguinoVuelve(Pinguino p) {
        System.out.println("El pinguino " + p.getNom() + " vuelve");
    }

    // metodo encargado de la funcion pinguinocontra recibiendo parametros: Pinguino p1, Pinguino p2
    public void pinguinoContra(Pinguino p1, Pinguino p2) {
        System.out.println("¡Duelo entre " + p1.getNom() + " y " + p2.getNom() + "!");
    }

    // metodo encargado de la funcion focainteractua recibiendo parametros: Pinguino p, Foca f
    public void focaInteractua(Pinguino p, Foca f) {
        if (p.getPosicion() == f.getPosicion()) {
            f.aplastarJugador(p);
        }
    }
}