package JocPengui;

public abstract class Pinguino extends Jugador {
	private Inventario inv;
	public Pinguino(String nombre, String color, int posicion) {
		super(posicion, nombre,color);
		this.inv = new Inventario();
	}
	public Inventario getInv() {
		return inv;
	}
	public void setInv(Inventario inv) {
		this.inv = inv;
	}
	public void usarItem() {
		System.out.println("El usuario ha usado un objeto del inventario");
	}
	
	@Override
	public String toString() {
		return "Pinguino: " + nombre + " Posicion: " + posicion + " color: " + color;
	}
}