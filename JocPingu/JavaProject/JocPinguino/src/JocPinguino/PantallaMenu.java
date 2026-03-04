package JocPinguino;

import java.util.Scanner;
import java.util.ArrayList;

public class PantallaMenu {
	private Scanner scanner;
	private mainJoc controlador;
	
	public PantallaMenu() {
		this.scanner = new Scanner(System.in);
		this.controlador = new mainJoc();
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
	scanner.nextLine();
	
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
		scanner.nextLine();
		
		if (numJugadores < 2 || numJugadores > 4) {
			System.out.println("Numero invalido ");
			return;
		}
	ArrayList<String> nombres = new ArrayList<>();
	for (int i = 1; i <= numJugadores; i++) {
		System.out.print("Nombre del Jugador " + i + ": ");
		
		String nombre = scanner.nextLine();
		nombres.add(nombre);
	}
	System.out.println("Partida creada con " + numJugadores + " jugadores ");
	
	// GestorPartida gestor = new GestorPartida(); //Falta con la otra clase
	// Partida partida = gestor.crearPartida(nombres); // Falta con la otra clase
	
	controlador.jugar();
	
	}
	public void botonCargarPartida(int id) {
		System.out.println("----------------- ");
		System.out.println("Cargar Partida Guardada ");
		System.out.println("............... ");
		
		System.out.println("Ingresa el ID de la partida: ");
		int idPartida = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("Partida cargada ");
		controlador.jugar();
	}
	
	public void botonSalir() {
		System.out.println("-------------------------------------");
		System.out.println("Gracias por jugar el juego Pinguino ");
		System.out.println("-------------------------------------");
	}
	
}
