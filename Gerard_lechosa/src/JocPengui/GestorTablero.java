package JocPengui;

public class GestorTablero {
    private Tablero tablero;

    public GestorTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public void actualizarTablero() {
        // Lógica para actualizar el tablero, por ejemplo, después de un movimiento
        // Esto podría incluir la actualización de la interfaz gráfica, etc.
    }

    public Tablero getTablero() {
        return tablero;
    }
    public void ejecutarCasilla(Partida partida, Pinguino p, Casilla c){
        // Lógica para ejecutar la acción de una casilla específica
        // Esto podría incluir la interacción con el pinguino, la partida, etc.
    }
    public void comprobarFinTurno(Partida partida){
        // Lógica para comprobar si el turno ha terminado, por ejemplo, si el pinguino ha llegado a una casilla de meta
        // Esto podría incluir la actualización del estado de la partida, etc.
    }
}
