package JocPengui;

import java.util.Random;
import java.util.ArrayList;

public class GestorPartida {

    private Partida partida;
    private GestorTablero gestorTablero;
    private GestorJugador gestorJugador;
    private Random random;

    public GestorPartida() {
        this.random = new Random();
    }

    // crea una partida nueva segun UML
    public void nuevaPartida(ArrayList<Jugador> jugadores, Tablero tablero) {
        // crea partida
        partida = new Partida();

        // anade jugadores
        for (Jugador j : jugadores) {
            partida.afegirJugador(j);
        }

        // asigna tablero
        partida.setTablero(tablero);

        // crea controladores
        gestorTablero = new GestorTablero(tablero);
        gestorJugador = new GestorJugador();

        // anade jugadores a gestorJugador
        for (Jugador j : jugadores) {
            gestorJugador.afegirJugador(j);
        }

        // inicia turno
        partida.setTornActual(0);
    }
}