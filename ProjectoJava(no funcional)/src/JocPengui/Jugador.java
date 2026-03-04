package JocPengui;

public abstract class Jugador {
	protected int posicion;
	protected String nombre;
	protected String color;
	public Jugador(int posicion, String nom, String color) {
		this.posicion = 0;
		this.nombre = nom;
		this.color = color;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	public String getNom() {
		return nombre;
	}
	public void setNom(String nom) {
		this.nombre = nom;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void moverPosicion(int p) {
		this.posicion += p;
	}
	@Override
	public String toString() {
		return "Jugador: " + nombre + 
	               " | Color: " + color + 
	               " | Posición: " + posicion;
	}
}