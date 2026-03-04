package JocPengui;
import java.util.ArrayList;

public class Inventario {
	private ArrayList<Item> items;
	public Inventario() {
		items = new ArrayList<>();
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	public void añadirItem(Item item) {
		items.add(item);
	}
	public void eliminarItem(Item item) {
		items.remove(item);
	}
	public void mostrarInventario() {
		for(int i = 0; i < items.size(); i++) {
			System.out.println(items.get(i));
		}
	}
}
