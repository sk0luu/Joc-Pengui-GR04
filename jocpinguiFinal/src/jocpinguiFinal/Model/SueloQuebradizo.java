package jocpinguiFinal.Model;
import java.util.Random;
public class SueloQuebradizo extends Casilla{
    // constructor para situar el suelo quebradizo
    public SueloQuebradizo(int posicion) {
        super(posicion);
    }
    // el hielo se rompe segun el peso del jugador (numero de items)
    @Override
    public void realizarAccion(Partida partida, Jugador jugador) {
		int cantItems = 0;
		if (jugador instanceof Pinguino) {
			cantItems = ((Pinguino) jugador).getInv().getTotalItems();
		}

		if (cantItems > 5) {
			System.out.println("¡Llevas demasiadas cosas! El hielo se rompe y vuelves al inicio.");
			jugador.setPosicion(0);
		} else if (cantItems > 0) {
			System.out.println("El hielo está frágil. Te quedas atrapado un turno.");
			jugador.congelar(1);
		} else {
			System.out.println("No llevas peso, pasas con cuidado sin problemas.");
		}
	}
	}
