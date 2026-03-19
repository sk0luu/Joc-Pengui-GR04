package jocpinguiFinal.Model;

public abstract class Jugador implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	protected int posicion;
	protected String nombre;
	protected String color;
	private int turnosCongelado;
	//Este constructor crea la posicion, nombre y color del juegador
	public Jugador(int posicion, String nom, String color) {
		this.posicion = 0;
		this.nombre = nom;
		this.color = color;
		this.turnosCongelado = 0;
	}
	//Este metodo muestra la posicion
	public int getPosicion() {
		return posicion;
	}
	//Este metodo actualiza la posicion del jugador
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	//Este metodo muestra el nombre del jugador
	public String getNom() {
		return nombre;
	}
	//Este metodo actualiza el nombre del jugador
	public void setNom(String nom) {
		this.nombre = nom;
	}
	//Este metodo muestra el color del jugador
	public String getColor() {
		return color;
	}
	//Este metodo actualiza el color del jugador
	public void setColor(String color) {
		this.color = color;
	}
	//Este metodo mueve la posicion del jugador
	public void moverPosicion(int p) {
		this.posicion += p;
	}

	public int getTurnosCongelado() {
		return turnosCongelado;
	}

	public void setTurnosCongelado(int turnosCongelado) {
		this.turnosCongelado = turnosCongelado;
	}

	public boolean estaCongelado() {
		return this.turnosCongelado > 0;
	}

	public void pasaTurnoCongelado() {
		if (this.turnosCongelado > 0) {
			this.turnosCongelado--;
		}
	}

	//Este metodo sobreescribe el jugador y pone el nombre color y posicion del jugador
	@Override
	public String toString() {
		return "Jugador: " + nombre + 
		       " | Color: " + color + 
		       " | Posición: " + posicion +
		       " | Congelado: " + turnosCongelado;
	}
}