package JocPinguino;

import java.util.Scanner; 
import java.util.ArrayList; //import la clase ArrayList 

public class PantallaMenu {  //Define la clase PantallaMenu
	//Atributos
	private Scanner scanner; 
	private Main controlador; //referencia a la clase Main
	
	//Constructores
	public PantallaMenu() {
		this.scanner = new Scanner(System.in); // Sirve para leer del teclado
		this.controlador = new Main(); // Crea un objeto de la clase Main
	}
	
	public void menu() { // Creacio de metodo menu()
		System.out.println("--------------------------------");
		System.out.println("Menu Principal: ");
		System.out.println("1. Nueva Partida ");
		System.out.println("2. Cargar Partida ");
		System.out.println("3. Salir ");
		System.out.println("---------------------------------");
		System.out.println("Selecciona una opcion(1-3): ");
	
	int opcion = scanner.nextInt(); //Leer el numero que el usuario escribe
	scanner.nextLine(); //Lee la siguiente linea
	
	if (opcion == 1 ) { //Si el usuario escribe 1, se va a botonNuevaPartida()
		botonNuevaPartida();
	} else if (opcion == 2) { //Si el usuario escribe 2, se va a botonCargarPartida()
		botonCargarPartida(0);
	} else if (opcion == 3) { //Si el usuario escribe 3, se va a botonSalir()
		botonSalir();
	} else {
		System.out.println("Opcion invalida "); // Y si no escribe ninguno de los 3 numeros diremos que es Opcion Invalida
	}
}
	public void botonNuevaPartida() { //Creacio de metodo botonNuevaPartida()
		System.out.println("---------------------------");
		System.out.println("Crear nueva Partida ");
		System.out.println("---------------------------");
		
		System.out.println("Cuantos jugadores? (2-4): "); 
		int numJugadores = scanner.nextInt(); //Lee el numero de 2-4
		scanner.nextLine(); //Limpia el buffer
		
		if (numJugadores < 2 || numJugadores > 4) { //Validamos si esta dentro de 2 o 4
			System.out.println("Numero invalido "); //Sino muestra error
			return;
		}
	ArrayList<String> nombres = new ArrayList<>(); //Lista que guarda los nombres de los jugadores
	for (int i = 1; i <= numJugadores; i++) { //Bucle que se repite desde 1 hasta numJugadores
		System.out.print("Nombre del Jugador " + i + ": ");
		
		String nombre = scanner.nextLine(); // //Guarda tu nombre
		nombres.add(nombre); //Nombres que se añaden 
	}
	System.out.println("Partida creada con " + numJugadores + " jugadores ");
	
	GestorPartida gestor = new GestorPartida(); //Falta con la otra clase
	Partida partida = gestor.crearPartida(nombres); // Falta con la otra clase
	
	controlador.jugar(); //Iniciar juego en la classe Main
	
	}
	public void botonCargarPartida(int id) {
		System.out.println("------------------------- ");
		System.out.println("Cargar Partida Guardada ");
		System.out.println("------------------------- ");
		
		System.out.println("Ingresa el ID de la partida: "); //Pide el ID de partida a cargar
		int idPartida = scanner.nextInt(); 
		scanner.nextLine();
		
		System.out.println("Partida cargada ");
		controlador.jugar(); 
	}
	
	public void botonSalir() { //Metodo botonSalir() funciona cuando el usuario elige opcion 3
		System.out.println("-------------------------------------");
		System.out.println("Gracias por jugar el juego Pinguino ");
		System.out.println("-------------------------------------");
	}
	
}
