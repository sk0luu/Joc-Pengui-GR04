package JocPinguino;

public class mainJoc {

	public static void main(String[] args) {
		System.out.println("");
		System.out.println("Bienvenido al Juego ");
		
		PantallaMenu menu = new PantallaMenu();
		menu.menu();
	}
		public void jugar() {
			System.out.println("--------------------");
			System.out.println("Iniciando Partida ");
			System.out.println("---------------------");
			
			PantallaPartida pantallaPartida = new PantallaPartida();
			
	}
}
