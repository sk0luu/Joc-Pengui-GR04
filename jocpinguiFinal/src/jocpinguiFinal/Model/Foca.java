package jocpinguiFinal.Model;

public class Foca extends Jugador {
	private boolean soborno;
	//Este constructor sirve para poner la posicion el nombre y el color de la foca
	public Foca(int posicion, String nombre, String color) {
		super(posicion, nombre, color);
		this.soborno = false;
	}
	//Este metodo mira si se ha sobornado a la foca
	public boolean isSoborno() {
		return soborno;
	}
	//Este metodo actualiza el soborno a la foca
	public void setSoborno(boolean soborno) {
		this.soborno = soborno;
	}
	//Este metodo aplasta al jugador si no ha sobornado a la foca y lo manda a la posicion 0, pero si la ha sobornado no le hace nada
	public void aplastarJugador(Pinguino p) {
		if(!soborno) {
			System.out.println("La foca aplasta a " + p.getNom());
			p.setPosicion(0);
		} else {
			System.out.println("La foca esta sobornada y no hace nada");
		}
	}
	//La foca golpea al jugador y lo manda dos posiciones atras
	public void golpearJugador(Pinguino p) {
		if(!soborno) {
			System.out.println("La foca te ha golpeado y te ha llevado dos posiciones atras");
			p.posicion -= 2;
		}
	}
	//Si la foca ha sido sobornada sale este mensaje
	public void esSobornado() {
		this.soborno = true;
		System.out.println("La foca ha sido sobornada");
	}
}