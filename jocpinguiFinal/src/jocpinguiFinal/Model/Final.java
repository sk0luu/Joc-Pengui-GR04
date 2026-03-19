package jocpinguiFinal.Model;

public class Final extends Casilla {
    public Final(int posicion) {
        super(posicion);
    }

    @Override
    public void realizarAccion(Partida partida, Jugador jugador) {
        // La casilla final no realiza acción extra, la victoria la comprueba el Gestor.
        System.out.println("¡Has llegado a la meta épica!");
    }
}
