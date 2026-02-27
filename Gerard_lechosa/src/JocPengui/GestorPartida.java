import java.util.ArrayList;
import java.util.Random;

public class GestorPartida {
    private Partida partida;
    private GestorTablero gestorTablero;
    private GestorJugador gestorJugador;
    private BBDD bbdd;
    private Random random;

    public GestorPartida(GestorTablero gestorTablero, GestorJugador gestorJugador, BBDD bbdd) {
        this.gestorTablero = gestorTablero;
        this.gestorJugador = gestorJugador;
        this.bbdd = bbdd;
        this.random = new Random();
    }

    public void nuevaPartida(ArrayList<Jugador> jugadores, Tablero tablero) {
        this.partida = new Partida();
        partida.setJugadores(jugadores);
        partida.setTablero(tablero);
        partida.setTurnos(0);
        partida.setJugadorActual(0);
        partida.setFinalizada(false);
        partida.setGanador(null);
        actualizarEstadoTablero();
    }

    public int tirarDado(Jugador j, Dado dadoOpcional) {
        if (dadoOpcional == null) return 0;
        return dadoOpcional.tirar(random);
    }
}