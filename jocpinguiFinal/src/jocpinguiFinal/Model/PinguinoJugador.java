package jocpinguiFinal.Model;

import java.io.Serializable;

public class PinguinoJugador extends Pinguino implements Serializable {
    private static final long serialVersionUID = 1L;

    public PinguinoJugador(String nombre, String color, int posicion) {
        super(nombre, color, posicion);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
