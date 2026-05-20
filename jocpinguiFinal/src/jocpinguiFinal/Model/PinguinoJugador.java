package jocpinguiFinal.Model;

import java.io.Serializable;

/**
 * representa un pinguino controlado directamente por una persona.
 */
public class PinguinoJugador extends Pinguino implements Serializable {
    // variable que guarda informacion sobre serialversionuid
    private static final long serialVersionUID = 1L;

    // metodo encargado de la funcion pinguinojugador recibiendo parametros: String nombre, String color, int posicion
    public PinguinoJugador(String nombre, String color, int posicion) {
        super(nombre, color, posicion);
    }

    @Override
    // metodo encargado de la funcion tostring recibiendo parametros: ninguno
    public String toString() {
        return super.toString();
    }
}
