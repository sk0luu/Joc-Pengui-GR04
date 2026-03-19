package jocpinguiFinal.Controlador;

import jocpinguiFinal.*;
import jocpinguiFinal.Model.Foca;
import jocpinguiFinal.Model.Jugador;
import jocpinguiFinal.Model.Pinguino;
import jocpinguiFinal.Model.Tablero;

public class GestorJugador {

    public void jugadorVuelve(String nombreItem) {
        System.out.println("El jugador vuelve con " + nombreItem);
    }

    public void jugadorSeMueve(Jugador j, int pasos, Tablero t) {
        j.moverPosicion(pasos);
        if (j.getPosicion() < 0) {
            j.setPosicion(0);
        }
    }

    public void jugadorFinalizaTurno(Jugador j) {
        System.out.println("El jugador " + j.getNom() + " ha finalizado su turno");
    }

    public void pinguinoVuelve(Pinguino p) {
        System.out.println("El pinguino " + p.getNom() + " vuelve");
    }

    public void pinguinoContra(Pinguino p1, Pinguino p2) {
        System.out.println("¡Duelo entre " + p1.getNom() + " y " + p2.getNom() + "!");
    }

    public void focaInteractua(Pinguino p, Foca f) {
        if (p.getPosicion() == f.getPosicion()) {
            f.aplastarJugador(p);
        }
    }
}