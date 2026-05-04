package jocpinguiFinal.Model;
import java.util.ArrayList;

public class Oso extends Casilla {
    // constructor para situar el oso
    public Oso(int posicion) {
        super(posicion);
    }
    // el oso ataca al jugador y lo manda al inicio, a menos que tenga un pez
    @Override
    public void realizarAccion(Partida partida, Jugador jugador) {
        if (jugador instanceof Pinguino) {
            Pinguino p = (Pinguino) jugador;
            Item pez = null;
            boolean hallado = false;
            ArrayList<Item> listaItems = p.getInv().getItems();
            for (int i = 0; i < listaItems.size() && !hallado; i++) {
                Item it = listaItems.get(i);
                if (it.getNombre().toLowerCase().contains("pez")) {
                    pez = it;
                    hallado = true;
                }
            }

            if (pez != null && pez.getCantidad() > 0) {
                pez.setCantidad(pez.getCantidad() - 1);
                if (pez.getCantidad() <= 0) p.getInv().eliminarItem(pez);
                System.out.println("¡Has sobornado al oso con un pez! Te deja marchar en paz.");
                return;
            }
        }
        
        jugador.setPosicion(0);
        System.out.println("¡El oso te ha golpeado! Vuelves al inicio.");
    }
}