package jocpinguiFinal.Model;
import java.util.Random;
public class SueloQuebradizo extends Casilla{
	//Este constructor pone la posicion de la casilla que tiene suelo resbaladizo
	public SueloQuebradizo(int posicion) {
		super(posicion);
	}
	//Este metodo realiza la accion del suelo quebradizo
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