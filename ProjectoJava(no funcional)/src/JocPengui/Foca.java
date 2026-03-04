package JocPengui;

public class Foca extends Jugador {
	private boolean soborno;
	public Foca(int posicion, String nombre, String color) {
		super(posicion, nombre, color);
		this.soborno = false;
	}
	public boolean isSoborno() {
		return soborno;
	}
	public void setSoborno(boolean soborno) {
		this.soborno = soborno;
	}
	public void aplastarJugador(Pinguino p) {
		if(!soborno) {
			System.out.println("La foca aplasta a " + p.getNom());
			p.setPosicion(0);
		} else {
			System.out.println("La foca esta sobornada y no hace nada");
		}
	}
	public void golpearJugador(Pinguino p) {
		if(!soborno) {
			System.out.println("La foca te ha golpeado y te ha llevado dos posiciones atras");
			p.posicion -= 2;
		}
	}
	public void esSobornado() {
		this.soborno = true;
		System.out.println("La foca ha sido sobornada");
	}
}