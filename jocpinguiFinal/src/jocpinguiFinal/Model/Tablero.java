package jocpinguiFinal.Model;
import java.util.ArrayList;
public class Tablero implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
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
	//Este metodo inicializa el tablero con 50 casillas
	public void inicializarTablero() {
		int total = 50;
		int ultima = total - 1; // casilla final épica

		ArrayList<Casilla> especiales = new ArrayList<>();
		// 5 Osos, 4 Trineos, 4 Agujeros, 4 Suelos quebradizos, 4 Eventos (total 21 especiales)
		for (int i = 0; i < 5; i++) especiales.add(new Oso(0));
		for (int i = 0; i < 4; i++) especiales.add(new Trineo(0));
		for (int i = 0; i < 4; i++) especiales.add(new Agujero(0));
		for (int i = 0; i < 4; i++) especiales.add(new SueloQuebradizo(0));
		String[] listaEventos = {"Viento a favor: +2", "Trampa de hielo: -2", "Tormenta de nieve: Pierdes turno"};
		for (int i = 0; i < 4; i++) especiales.add(new Evento(0, listaEventos));

		int normales = ultima - especiales.size();
		for (int i = 0; i < normales; i++) especiales.add(new Normal(0));

		// Aleatorizar posiciones de las 49 primeras casillas
		java.util.Collections.shuffle(especiales);

		for (int i = 0; i < ultima; i++) {
			Casilla c = especiales.get(i);
			c.setPosicion(i);
			casillas.add(c);
		}

		casillas.add(new Final(ultima));
	}
}