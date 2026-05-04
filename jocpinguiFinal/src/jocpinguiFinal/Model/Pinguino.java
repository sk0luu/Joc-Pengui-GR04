package jocpinguiFinal.Model;

public abstract class Pinguino extends Jugador {
	private static final long serialVersionUID = 1L;
	private Inventario inv;
	
	//Este constructor le pone nombre color y posicion al pinguino
	public Pinguino(String nombre, String color, int posicion) {
		super(posicion, nombre, color);
		this.inv = new Inventario();
	}
	
	//Este metodo muestra el inventario
	public Inventario getInv() {
		return inv;
	}
	
	//Este metodo actualiza el inventario
	public void setInv(Inventario inv) {
		this.inv = inv;
	}
	
	//Este metodo usa el item del inventario
	public void usarItem() {
		System.out.println("El usuario ha usado un objeto del inventario");
	}
	
	//Este metodo muestra el nombre posicion y color del pinguino
	@Override
	public String toString() {
		return "Pinguino: " + nombre + " Posicion: " + posicion + " color: " + color;
	}
}