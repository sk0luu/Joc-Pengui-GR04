package JocPinguino;

public class Main {

	public static void main(String[] args) {
		System.out.println("");
		System.out.println("Bienvenido al Juego ");
		
		PantallaMenu menu = new PantallaMenu();
		menu.menu();
	}
		public void jugar() {
			
			PantallaPartida pantallaPartida = new PantallaPartida();
			pantallaPartida.IniciarPartida();
	}
}