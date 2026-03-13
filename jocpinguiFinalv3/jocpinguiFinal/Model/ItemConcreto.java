package jocpinguiFinal.Model;

public class ItemConcreto extends Item {
    private static final long serialVersionUID = 1L;

    public ItemConcreto(String nombre, int cantidad) {
        super(nombre, cantidad);
    }

    @Override
    public String toString() {
        return nombre + " x" + cantidad;
    }
}
