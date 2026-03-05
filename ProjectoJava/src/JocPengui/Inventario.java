package JocPengui;
import java.util.ArrayList;

public class Inventario {
	private ArrayList<Item> items;
	//Creamos el constructor y ponemos el arraylist de los items del jugador
	public Inventario() {
		items = new ArrayList<>();
	}
	//Este metodo muestra todos los items
	public ArrayList<Item> getItems() {
		return items;
	}
	//Este metodo añade items al arraylist
	public void añadirItem(Item item) {
		items.add(item);
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
}
