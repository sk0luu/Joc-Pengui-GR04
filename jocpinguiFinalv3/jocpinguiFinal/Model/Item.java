package jocpinguiFinal.Model;

import java.io.Serializable;

public abstract class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String nombre;
	protected int cantidad;
	//Este constructor sirve para ponerle nombre de un item y cantidad
	public Item(String nombre, int cantidad) {
		this.nombre = nombre;
		this.cantidad = cantidad;
	}
	//Este metodo muestra el nombre del item
	public String getNombre() {
		return nombre;
	}
	//Este metodo muestra la cantidad del item
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
