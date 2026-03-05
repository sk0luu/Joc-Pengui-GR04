package JocPengui;
import java.util.ArrayList;
public class Tablero {
	private ArrayList<Casilla> casillas;
	//Este constructor sirve para poner las casillas en el tablero
	public Tablero() {
		casillas = new ArrayList<>();
		inicializarTablero();
	}
	//Este metodo muestra todas las casillas
	public ArrayList<Casilla> getCasillas() {
		return casillas;
	}
	//Este metodo actualiza las casillas
	public void setCasillas(ArrayList<Casilla> casillas) {
		this.casillas = casillas;
	}
	//Este metodo actualiza el tablero 
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
	//Este metodo inicializa el tablero para poner las casillas de oso, trineo, agujero y suelo quebradizo
	public void inicializarTablero() {
		casillas.add(new Oso(0));
		casillas.add(new Trineo(1));
		casillas.add(new Agujero(2));
		casillas.add(new SueloQuebradizo(3));
	}
}