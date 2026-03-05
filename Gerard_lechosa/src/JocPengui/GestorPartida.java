package JocPengui;
import java.util.ArrayList;
import java.util.Random;

public class GestorPartida {

    // referencia a la partida actual
    private Partida partida;

    // controladores necesarios para ejecutar acciones del juego
    private GestorTablero gestorTablero;
    private GestorJugador gestorJugador;

    // acceso a base de datos
    private BBDD bbdd;

    // random usado por el dado
    private Random random;

    // constructor que recibe los gestores y la bbdd
    public GestorPartida(GestorTablero gestorTablero, GestorJugador gestorJugador, BBDD bbdd) {
        this.gestorTablero = gestorTablero;
        this.gestorJugador = gestorJugador;
        this.bbdd = bbdd;
        this.random = new Random();
    }

    // crea una partida nueva con jugadores y tablero inicial
    public void nuevaPartida(ArrayList<Jugador> jugadores, Tablero tablero) {
        this.partida = new Partida();
        partida.setJugadores(jugadores);      // guarda lista de jugadores
        partida.setTablero(tablero);          // guarda tablero inicial
        partida.setTurnos(0);                 // contador de turnos
        partida.setJugadorActual(0);          // posicion del jugador inicial
        partida.setFinalizada(false);         // partida aun no ha terminado
        partida.setGanador(null);             // sin ganador al inicio
        actualizarEstadoTablero();            // refresca tablero
    }

    // tira un dado sobre un jugador usando random
    public int tirarDado(Jugador j, Dado dadoOpcional) {
        if (dadoOpcional == null) return 0;   // si no hay dado no se mueve
        return dadoOpcional.tirar(random);    // usa min y max del dado
    }

    // ejecuta todas las acciones que tiene un turno
    public void ejecutarTurnoCompleto() {
        if (partida == null || partida.isFinalizada()) return;
        Jugador j = partida.getJugadorActual();   // jugador que juega este turno
        procesarTurnoJugador(j);                  // mueve jugador y ejecuta casilla
        siguienteTurno();                         // pasa al siguiente jugador
    }

    // logica del turno de un jugador concreto
    public void procesarTurnoJugador(Jugador j) {
        if (j == null || partida == null) return;
        int pasos = tirarDado(j, null);                   // obtiene numero de casillas
        gestorJugador.jugadorSeMueve(j, pasos, partida.getTablero()); // mueve jugador
        actualizarEstadoTablero();                        // refresca tablero
        gestorTablero.comprobarFinTurno(partida);         // revisa fin de partida
    }

    // actualiza el estado visual y logico del tablero
    public void actualizarEstadoTablero() {
        if (partida == null) return;
        Tablero t = partida.getTablero();
        if (t != null) t.actualizarTablero();
    }

    // avanza al siguiente jugador del turno
    public void siguienteTurno() {
        if (partida == null) return;

        ArrayList<Jugador> js = partida.getJugadores();
        if (js == null || js.isEmpty()) return;

        Jugador actual = partida.getJugadorActual();   // jugador actual
        int idx = js.indexOf(actual);                  // indice del jugador
        if (idx < 0) idx = 0;

        int next = (idx + 1) % js.size();              // circula por la lista
        partida.setJugadorActual(next);                // actualiza
        partida.setTurnos(partida.getTurnos() + 1);    // suma turno
    }

    // devuelve la partida actual
    public Partida getPartida() {
        return partida;
    }

    // guarda el estado de la partida en la bbdd
    public void guardarPartida() {
        if (bbdd != null && partida != null) {
            bbdd.guardarPartida(partida);
        }
    }

    // carga una partida desde la bbdd por id
    public void cargarPartida(int id) {
        if (bbdd != null) {
            this.partida = bbdd.cargarPartida(id);
        }
    }
}