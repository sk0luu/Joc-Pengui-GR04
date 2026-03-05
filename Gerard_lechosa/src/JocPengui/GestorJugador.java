package JocPengui;

// clase encargada de que el jugador elija que hace en su pantalla de menues
public class GestorJugador {

    // crear el objeto vacio porque no necesita guardar variables aqui adentro
    public GestorJugador() {
    }

    // funcion para dar paso al que le toque jugar
    public void jugadorActua(String nombreUser) {
        // empieza el turno del jugador y se lo mostramos a el
        System.out.println("empieza " + nombreUser);

        // tras esto aqui iria el menu de:
        // pulsa 1 para moverse
        // pulsa 2 para comer pez
    }
}