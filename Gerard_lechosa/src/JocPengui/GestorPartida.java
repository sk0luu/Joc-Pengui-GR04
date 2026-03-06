package JocPengui;

import java.util.Random;
import java.sql.Connection;
import java.util.Scanner;
import java.util.ArrayList;

// clase que controla todo el juego de manera general
public class GestorPartida {

    // variables donde guardamos las cosas importantes
    private Partida partida; // la partida que estamos jugando ahora
    private GestorTablero gestorTablero; // el que controla el tablero
    private GestorJugador gestorJugador; // el que controla a los jugadores
    private BBDD gestorBBDD; // el acceso a la base de datos
    private Random random; // clase para sacar numeros aleatorios (como tirar un dado real)

    // cuando se arranca la partida, se preparan todos gestores
    public GestorPartida() {
        this.gestorTablero = new GestorTablero();
        this.gestorJugador = new GestorJugador();
        this.gestorBBDD = new BBDD();
        this.random = new Random();
    }

    // funcion para crear una partida desde cero
    public void nuevaPartida() {
        this.partida = new Partida(); // creamos una partida nueva y limpia
    }

    // funcion para ver cuantas casillas se mueve el jugador
    public int tirarDado(Jugador j, Dado dadoOpcional) {
        // si el jugador tiene algun dado especial lo usa
        if (dadoOpcional != null) {
            return dadoOpcional.tirarRandom();
        }
        // si no tiene dado, devolvemos un numero normal del 1 al 6 usando nuestro
        // random
        return this.random.nextInt(6) + 1;
    }

    // aqui se junta todo lo que pasa en el turno de alguien
    public void ejecutarTurnoCompleto() {
        // primero miramos que de verdad haya partida y que nadie haya ganado aun
        if (this.partida != null && !this.partida.isFinalizada()) {

            // miramos de quien es el turno ahora mismo
            Jugador jugadorActual = this.partida.getJugadorActual();

            // dejamos que ese jugador tire dado y decida que hacer
            procesarTurnoJugador(jugadorActual);

            // actualizamos el mapa por si alguien rompio el hielo o paso algo
            actualizarEstadoTablero();

            // finalmente terminamos y le toca jugar al que va despues
            siguienteTurno();
        }
    }

    // funcion que contiene los movimientos y acciones de un jugador de verdad
    public void procesarTurnoJugador(Jugador j) {
        // si nos pasan un jugador que si existe
        if (j != null) {
            // el gestor del jugador le avisa y le da opciones
            this.gestorJugador.jugadorActua(j.getNombre());
        }
    }

    // funcion que comprueba los cambios de todas las casillas
    public void actualizarEstadoTablero() {
        // solo lo hace si esta partida de verdad tiene tablero creado
        if (this.partida != null && this.partida.getTablero() != null) {
            // le dice al tablero que revise y aplique si se hunde alguna parte
            this.partida.getTablero().actualizarTablero();
        }
    }

    // funcion para saltar al proximo turno
    public void siguienteTurno() {
        // si no se ha acabado el juego aun
        if (this.partida != null && !this.partida.isFinalizada()) {

            // vemos cuantos turnos han pasado en la partida y sumamos uno
            int turnos = this.partida.getTurnos();
            this.partida.setTurnos(turnos + 1);

            // pedimos una lista con los participantes
            ArrayList<Jugador> jugadores = this.partida.getJugadores();

            // si hay participantes de verdad
            if (jugadores != null && !jugadores.isEmpty()) {

                // preguntamos por que numero de la lista vamos ahora mismo
                int indiceActual = this.partida.getJugadorActualInt();

                // calculamos de forma matematica a quien le toca.
                // si va por el ultimo jugador, vuelve a tocarle al primero
                int siguienteIndice = (indiceActual + 1) % jugadores.size();

                // aplicamos el cambio guardando a quien le toca jugar en el futuro
                this.partida.setJugadorActualInt(siguienteIndice);
            }
        }
    }

    // pedimos saber que partida estamos jugando
    public Partida getPartida() {
        return this.partida;
    }

    // funcion que hace de boton para guardar todo
    public void guardarPartida() {
        // mientras la partida exista de verdad
        if (this.partida != null) {

            // conectamos a la terminal porque vuestra base de datos exige un escaner
            Scanner scan = new Scanner(System.in);
            // usamos la funcion que trae vuestro codigo de la profe para conectarse a
            // oracle
            Connection con = BBDD.conectarBaseDatos(scan);

            // si consigue entrar a internet bien
            if (con != null) {
                // haria toda la subida de guardado
                System.out.println("partida guardada");

                // siempre cerramos para dejar sitio libre en el internet
                BBDD.cerrar(con);
            }
        }
    }

    // funcion que hace de boton para continuar una partida guardada antes
    public void cargarPartida(int id) {

        // activamos modo teclado igual que el metodo de arriba
        Scanner scan = new Scanner(System.in);
        // nos intentamos volver a conectar a la maquina de clase
        Connection con = BBDD.conectarBaseDatos(scan);

        // si se conecto sin internet roto
        if (con != null) {

            // preparamos el comando de busqueda SQL en vuestra BBDD con el codigo del
            // guardado
            String sql = "SELECT * FROM PARTIDA WHERE id = " + id;

            // usamos la busqueda de vuestro archivo bbdd usando lista simple para no ser
            // lioso
            ArrayList resultados = BBDD.select(con, sql);

            // comprobamos que la lista traiga algo escrito (que encontro la partida)
            if (!resultados.isEmpty()) {

                // cambiamos nuestra partida vacia por todo lo de este archivo
                this.partida = new Partida();
                System.out.println("partida cargada");

            } else {

                // saltaria si la BBDD responde que ese ID no esta registrado
                System.out.println("no se encontro la partida");
            }
            // importante siempre apagar la conexion al terminar
            BBDD.cerrar(con);
        }
    }
}
