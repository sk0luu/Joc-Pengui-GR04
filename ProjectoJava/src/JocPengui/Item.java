package JocPengui;

public abstract class Item {
	String nombre;
	int cantidad;
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
	
}
