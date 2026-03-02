package JocPinguino;

import java.util.Scanner;
import java.util.ArrayList;

public class PantallaMenu {
	private Scanner scanner;
	private String controlador;
	
	public PantallaMenu() {
		this.scanner = new Scanner(System.in);
		this.controlador = new String();
	}
	public void menu() {
		System.out.println("--------------------------------");
		System.out.println("Menu Principal: ");
		System.out.println("1. Nueva Partida ");
		System.out.println("2. Cargar Partida ");
		System.out.println("3. Salir ");
		System.out.println("---------------------------------");
		System.out.println("Selecciona una opcion(1-3): ");
	
	int opcion = scanner.nextInt();
	scanner.nextInt();
	
	if (opcion == 1 ) {
		botonNuevaPartida();
	} else if (opcion == 2) {
		
	}
}
	
	
	public void botonNuevaPartida() {
		System.out.println("---------------------------");
		System.out.println("Crear nueva Partida ");
		System.out.println("---------------------------");
		
		System.out.println("Cuantos jugadores? (2-4): ");
		int numJugadores = scanner.nextInt();
		scanner.nextInt();
	
	}
}
