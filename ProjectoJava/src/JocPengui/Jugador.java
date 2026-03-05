package JocPengui;

public abstract class Jugador {
	protected int posicion;
	protected String nombre;
	protected String color;
	//Este constructor crea la posicion, nombre y color del juegador
	public Jugador(int posicion, String nom, String color) {
		this.posicion = 0;
		this.nombre = nom;
		this.color = color;
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
	//Este metodo sobreescribe el jugador y pone el nombre color y posicion del jugador
	@Override
	public String toString() {
		return "Jugador: " + nombre + 
	               " | Color: " + color + 
	               " | Posición: " + posicion;
	}
}