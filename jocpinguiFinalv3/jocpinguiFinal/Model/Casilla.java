package jocpinguiFinal.Model;

public abstract class Casilla implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	protected int posicion;
	//Un constructor que devuelve la posicion actual 
	public Casilla(int posicion) {
		this.posicion = posicion;
	}
	//Este metodo muestra la posicion de la casilla
	public int getPosicion() {
		return posicion;
	}
	//Este metodo actualiza la posicion en la que esta
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	//Este metodo sirve para las otras clases hijas sobreescriban encima de la casilla y crea su accion
	public abstract void realizarAccion(Partida partida, Jugador jugador);
	
}