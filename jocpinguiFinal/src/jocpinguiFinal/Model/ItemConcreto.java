package jocpinguiFinal.Model;

/**
 * implementacion concreta de un item que puede guardarse en el inventario.
 */
public class ItemConcreto extends Item {
    // variable que guarda informacion sobre serialversionuid
    private static final long serialVersionUID = 1L;

    // metodo encargado de la funcion itemconcreto recibiendo parametros: String nombre, int cantidad
    public ItemConcreto(String nombre, int cantidad) {
        super(nombre, cantidad);
    }

    @Override
    // metodo encargado de la funcion tostring recibiendo parametros: ninguno
    public String toString() {
        return nombre + " x" + cantidad;
    }
}
