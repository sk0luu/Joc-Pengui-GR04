package JocPengui;

// clase encargada de que el jugador elija que hace en su pantalla de menues
public class GestorJugador {

    // crear el objeto vacio porque no necesita guardar variables aqui adentro
    public GestorJugador() {
    }
    public void jugadorUsaItem(String nombreItem){
        // aqui iria el menu de los items que tiene el jugador y lo que hace cada uno
        System.out.println("has usado el item " + nombreItem);
    }

    public void jugadorSeMueve(Jugador j, int pasos, Tablero t) {
        // aqui iria el menu de las direcciones a las que se puede mover el jugador
        System.out.println("te has movido hacia " + pasos + " pasos");
    }

    public void jugadorFinalizaTurno(Jugador j) {
        // aqui iria el menu de finalizar turno o usar item
        System.out.println("has finalizado tu turno " + j.getNombre());
    }

    public void pinguinoEvento(Pinguino p) {
        // aqui iria el menu de los eventos que pueden pasarle al pinguino
        System.out.println("has tenido un evento con tu pinguino " + p.getNombre());
    }

    public void pinguinoGuerra(Pinguino p1, Pinguino p2) {
        // aqui iria el menu de las opciones de guerra entre pinguinos
        System.out.println("has entrado en guerra con " + p2.getNombre());
    }

    public void focaInteractua(Pinguino p, Foca f) {
        // aqui iria el menu de las opciones de interaccion entre el pinguino y la foca
        System.out.println("has interactuado con la foca " + f.getNombre());
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