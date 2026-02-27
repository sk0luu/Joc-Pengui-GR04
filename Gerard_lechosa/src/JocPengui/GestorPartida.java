package JocPengui;

import java.util.Random;
import java.util.ArrayList;

public class GestorPartida {

    // atributos segun el diagrama
    private Partida partida;
    private GestorTablero gestorTablero;
    private GestorJugador gestorJugador;
    private Random random;

    // constructor vacio
    public GestorPartida() {
        // inicializa random
        this.random = new Random();
    }
}