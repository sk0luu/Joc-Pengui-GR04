package jocpinguiFinal.Model;
import java.util.ArrayList;

public class Inventario implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Item> items;
	//Creamos el constructor y ponemos el arraylist de los items del jugador
	public Inventario() {
		items = new ArrayList<>();
	}
	//Este metodo muestra todos los items
	public ArrayList<Item> getItems() {
		return items;
	}
	public void añadirItem(Item item) {
		String nombre = item.getNombre().toLowerCase();
		
		if (nombre.contains("pez")) {
			int actualPez = getCantidadDe("pez");
			int aAñadir = Math.min(item.getCantidad(), 2 - actualPez);
			if (aAñadir <= 0) return;
			incrementarOAgregar("Pez", aAñadir);
		} else if (nombre.contains("nieve")) {
			int actualNieve = getCantidadDe("nieve");
			int aAñadir = Math.min(item.getCantidad(), 6 - actualNieve);
			if (aAñadir <= 0) return;
			incrementarOAgregar("Nieve", aAñadir);
		} else if (nombre.contains("rápido") || nombre.contains("lento") || nombre.contains("dado")) {
			int actualDados = getCantidadDe("rápido") + getCantidadDe("lento") + getCantidadDe("dado");
			int aAñadir = Math.min(item.getCantidad(), 3 - actualDados);
			if (aAñadir <= 0) return;
			String nombreFinal = nombre.contains("rápido") ? "Dado Rápido" : 
								 nombre.contains("lento") ? "Dado Lento" : "Dado";
			incrementarOAgregar(nombreFinal, aAñadir);
		} else {
			incrementarOAgregar(item.getNombre(), item.getCantidad());
		}
	}

	private int getCantidadDe(String subcadena) {
		int cant = 0;
		for (Item it : items) {
			if (it.getNombre().toLowerCase().contains(subcadena.toLowerCase())) {
				cant += it.getCantidad();
			}
		}
		return cant;
	}

	private void incrementarOAgregar(String nombre, int cantidad) {
		for (Item it : items) {
			if (it.getNombre().equalsIgnoreCase(nombre)) {
				it.setCantidad(it.getCantidad() + cantidad);
				return;
			}
		}
		items.add(new ItemConcreto(nombre, cantidad));
	}
	//Este metodo elimina el item que decidas
	public void eliminarItem(Item item) {
		items.remove(item);
	}
	//Este metodo muestra el inventario completo
	public void mostrarInventario() {
		for(int i = 0; i < items.size(); i++) {
			System.out.println(items.get(i));
		}
	}
	public int getTotalItems() {
		int total = 0;
		for (Item it : items) {
			total += it.getCantidad();
		}
		return total;
	}
}
