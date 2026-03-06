package JocPengui;

public class GestorTablero {
    private Tablero tablero;

    public GestorTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public void ejecutarCasilla(Partida partida, Pingu, Casilla c){
        // Lógica para ejecutar la acción de la casilla
        // Esto podría incluir mover al pingüino, actualizar el estado del juego, etc.
    }

    public void comprobarFinTurno(Partida partida) {
        // Lógica para comprobar si el turno ha terminado
        // Esto podría incluir verificar si el pingüino ha llegado a una casilla de destino, etc.
    }

    public void actualizarTablero() {
        // Lógica para actualizar el tablero, por ejemplo, después de un movimiento
        // Esto podría incluir la actualización de la interfaz gráfica, etc.
    }

    public Tablero getTablero() {
        return tablero;
    }
}
