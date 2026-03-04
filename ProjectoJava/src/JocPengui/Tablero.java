package JocPengui;
import java.util.ArrayList;
public class Tablero {
	private ArrayList<Casilla> casillas;
	public Tablero() {
		casillas = new ArrayList<>();
		inicializarTablero();
	}
	public ArrayList<Casilla> getCasillas() {
		return casillas;
	}
	public void setCasillas(ArrayList<Casilla> casillas) {
		this.casillas = casillas;
	}
	public void actualizarTablero(ArrayList<Jugador> jugadores) {

	    for (int i = 0; i < casillas.size(); i++) {
	        System.out.print("[" + i + "]");
	        for (Jugador j : jugadores) {
	            if (j.getPosicion() == i) {
	                System.out.print(" " + j.getNom());
	            }
	        }

	        System.out.println();
	    }
	}
	public void inicializarTablero() {
		casillas.add(new Oso(0));
		casillas.add(new Trineo(1));
		casillas.add(new Agujero(2));
		casillas.add(new SueloQuebradizo(3));
	}
}