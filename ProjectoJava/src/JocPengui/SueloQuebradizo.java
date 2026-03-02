package JocPengui;
import java.util.Random;
public class SueloQuebradizo extends Casilla{
	public SueloQuebradizo(int posicion) {
		super(posicion);
	}
	@Override
	public void realizarAccion(Partida partida, Jugador jugador) {
		Random r = new Random();
		int probabilidad = r.nextInt(2);
	if(probabilidad == 0) {
		System.out.println("¡El hielo se rompe!");
		jugador.moverPosicion(-2);
	} else {
		System.out.println("El hielo se resiste.");
	}
	}
}
