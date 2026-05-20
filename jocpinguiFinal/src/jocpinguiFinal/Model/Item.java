package jocpinguiFinal.Model;

import java.io.Serializable;

/**
 * clase abstracta o interfaz que define las propiedades basicas de los objetos recolectables.
 */
public abstract class Item implements Serializable {
	// variable que guarda informacion sobre serialversionuid
	private static final long serialVersionUID = 1L;
	// variable que guarda informacion sobre nombre
	protected String nombre;
	// variable que guarda informacion sobre cantidad
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

	// metodo que actualiza o establece el valor de cantidad
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
