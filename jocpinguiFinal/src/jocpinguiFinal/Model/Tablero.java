package jocpinguiFinal.Model;
import java.util.ArrayList;
public class Tablero implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Casilla> casillas;
	// inicializa la lista de casillas del tablero
	public Tablero() {
		casillas = new ArrayList<>();
		inicializarTablero();
	}
	// devuelve todas las casillas
	public ArrayList<Casilla> getCasillas() {
		return casillas;
	}
	// actualiza la lista completa de casillas
	public void setCasillas(ArrayList<Casilla> casillas) {
		this.casillas = casillas;
	}
	// muestra por consola la posicion de los jugadores en el tablero
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
	// crea las 50 casillas y distribuye las especiales de forma equilibrada
	public void inicializarTablero() {
		int total = 50;
		int ultima = total - 1;

		// 1. Llenar todo de casillas normales inicialmente
		casillas = new ArrayList<>();
		for (int i = 0; i < total; i++) {
			casillas.add(new Normal(i));
		}

		// 2. Preparar lista de casillas especiales
		ArrayList<Casilla> especiales = new ArrayList<>();
		for (int i = 0; i < 3; i++) especiales.add(new Oso(0));
		for (int i = 0; i < 4; i++) especiales.add(new Trineo(0));
		for (int i = 0; i < 3; i++) especiales.add(new Agujero(0));
		for (int i = 0; i < 3; i++) especiales.add(new SueloQuebradizo(0));
		String[] listaEventos = {
			"Viento a favor: +2", "Tormenta de nieve: -2", 
			"Tormenta de nieve: Pierdes turno", "Robo en el nido: Pierdes un objeto",
			"¡Pez congelado encontrado!", "Pequeñas bolas de nieve encontradas (1-3)",
			"¡Has encontrado un Dado Rápido!", "¡Has encontrado un Dado Lento!", 
			"¡Has encontrado un Dado Lento!", "¡Has encontrado un Dado Lento!"
		};
		for (int i = 0; i < 5; i++) especiales.add(new Evento(0, listaEventos));

		java.util.Collections.shuffle(especiales);

		// 3. Distribuir en segmentos (6 segmentos de 8 casillas cada uno, del 1 al 48)
		// Pondremos exactamente 3 especiales en cada segmento para que esté equilibrado.
		java.util.Random rnd = new java.util.Random();
		int especIndex = 0;
		
		for (int s = 0; s < 6; s++) {
			int inicioSeg = 1 + (s * 8);
			ArrayList<Integer> indices = new ArrayList<>();
			for (int i = 0; i < 8; i++) indices.add(inicioSeg + i);
			java.util.Collections.shuffle(indices);
			
			// Ponemos 3 especiales en este segmento
			for (int i = 0; i < 3 && especIndex < especiales.size(); i++) {
				int pos = indices.get(i);
				Casilla c = especiales.get(especIndex++);
				c.setPosicion(pos);
				casillas.set(pos, c);
			}
		}

		// 4. Asegurar que la última sea de tipo Final
		casillas.set(ultima, new Final(ultima));
	}
}