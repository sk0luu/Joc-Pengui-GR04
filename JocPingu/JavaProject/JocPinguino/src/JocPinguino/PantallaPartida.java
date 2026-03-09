package JocPinguino;

import java.util.Scanner;

public class PantallaPartida {
	private Scanner scanner; // Sirve para leer del teclado
	//private GestorPartida gestorPartida

	public PantallaPartida() {
		this.scanner = new Scanner (System.in); // Sirve para leer del teclado
		// this.gestorPartida = new GestorPartida();
	}
	
	
	public void botonTirarDado() { //metodo para TirarDado
		System.out.println("---------------------");
		System.out.println("Tirando Dado "); 
		System.out.println("---------------------");
		
		int resultado = (int) (Math.random() * 6) + 1; // Tiramos el dado de 1 a 6 
		System.out.println("Resultado: " + resultado); //Nos dice que hemos sacado
		System.out.println("Te mueves " + resultado + "casillas "); // Y cuantas casillas nos movemos
		System.out.println("");
	}
	public void botonUserObjeto() { //metodo para los objetos
		System.out.println("----------------------");
		System.out.println("Inventario de Objetos ");
		System.out.println("----------------------");
		System.out.println("1. Pez (Restaura 20HP)");
		System.out.println("2. Hielo (Congela enemigo)");
		System.out.println("3. Escudo(Protege 1 turno) ");
		System.out.println("Selecciona objeto (0 para cancelar) ");
		
		int objeto = scanner.nextInt(); 
		scanner.nextLine();
		if (objeto == 0) {
			System.out.println("Cancelado "); //Si elegimos 0 nos dira que es cancelado
		} else if (objeto >= 1 && objeto <= 3) {
			System.out.println("Objeto usado "); //Elegimos uno de los 3 objetos
		} else {
			System.out.println("Objeto no valido "); //Si no decimos ningun numero 1 a 3 nos dira el objeto invalido
		}
		System.out.println("");
	}
	public void botonFinalizarTurno() { //metodo para cuando termina el turno
		System.out.println("--------------");
		System.out.println("Turno Finalizado "); 
		System.out.println("-------------------");
		System.out.println("Siguiente jugador... ");
		System.out.println("");
	}
	public void IniciarPartida() { 
		System.out.println("------------------");
		System.out.println("Iniciando Partida");
		System.out.println("------------------");
		System.out.println("Partida iniciada");
		System.out.println("");
	}
	public void cargarPartida() {
		System.out.println("-----------------");
		System.out.println("Cargando Partida");
		System.out.println("------------------");
		System.out.println("Partida cargada exitosamente");
		System.out.println("");
	}
	public void guardarPartida() {
		System.out.println("-----------------------");
		System.out.println("Guardando Partida...");
		System.out.println("----------------------");
		System.out.println("Partida guardada exitosamente");
		System.out.println("");
	}
	public void refrescarPanel() {
		System.out.println("----------------------");
		System.out.println("Refrecando Panel...");
		System.out.println("Partida guardada exitosamente ");
		System.out.println("");
	}

}
