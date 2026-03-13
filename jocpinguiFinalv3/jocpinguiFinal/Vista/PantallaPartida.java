package jocpinguiFinal.Vista;

import java.util.Scanner;

import jocpinguiFinal.Controlador.GestorPartida;
import jocpinguiFinal.Controlador.Main;
import jocpinguiFinal.Model.Jugador;
import jocpinguiFinal.Model.Partida;

public class PantallaPartida {
	private Scanner scanner; // Sirve para leer del teclado
	private GestorPartida gestorPartida;

	public PantallaPartida(GestorPartida gestor) {
		this.gestorPartida = gestor;
	}

	public void botonTirarDado() { // metodo para TirarDado
		System.out.println("---------------------");
		System.out.println("Tirando Dado ");
		System.out.println("---------------------");

		int resultado = (int) (Math.random() * 6) + 1; // Tiramos el dado de 1 a 6
		System.out.println("Resultado: " + resultado); // Nos dice que hemos sacado
		System.out.println("Te mueves " + resultado + "casillas "); // Y cuantas casillas nos movemos
		System.out.println("");
	}

	public void botonUsarObjeto() { // metodo para los objetos
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
			System.out.println("Cancelado "); // Si elegimos 0 nos dira que es cancelado
		} else if (objeto >= 1 && objeto <= 3) {
			System.out.println("Objeto usado "); // Elegimos uno de los 3 objetos
		} else {
			System.out.println("Objeto no valido "); // Si no decimos ningun numero 1 a 3 nos dira el objeto invalido
		}
		System.out.println("");
	}

	public void botonFinalizarTurno() { // metodo para cuando termina el turno
		System.out.println("--------------");
		System.out.println("Turno Finalizado ");
		System.out.println("-------------------");
		System.out.println("Siguiente jugador... ");
		System.out.println("");
	}

	public void iniciarPartida() {
		System.out.println("------------------");
		System.out.println("Iniciando Partida");
		System.out.println("------------------");
		System.out.println("Partida iniciada");
		

		if (gestorPartida == null || gestorPartida.getPartida() == null) {
			System.out.println("ERROR: no hay partida creada.");
			return;
		}

		Partida p = gestorPartida.getPartida();
		Scanner sc = new Scanner(System.in); // Scanner propio para evitar conflictos

		while (!p.isFinalizado()) {
			Jugador actual = p.getJugador().get(p.getJugadorActual());
			System.out.println("\n=== TURNO DE: " + actual.getNom() + " ===");
			System.out.println("Posicion: " + actual.getPosicion());
			System.out.println("1. Tirar Dado y Mover");
			System.out.println("2. Usar Objeto");
			System.out.println("3. Guardar Partida");
			System.out.println("Elige (1/2/3):");

			try {
				int opcion = sc.nextInt();
				sc.nextLine();
				if (opcion == 1) {
					botonTirarDado();
					gestorPartida.ejecutarTurnoCompleto();
					botonFinalizarTurno();
				} else if (opcion == 2) {
					botonUsarObjeto();
				} else if (opcion == 3) {
					guardarPartida();
				} else {
					System.out.println("Opcion invalida.");
				}
			} catch (Exception e) {
				System.out.println("Error leyendo opcion: " + e.getMessage());
				sc.nextLine(); // limpiar buffer
			}
		}
		System.out.println("\n¡El juego ha terminado!");
	}

	public void cargarPartida() { // metodo para cargar la Partida
		System.out.println("-----------------");
		System.out.println("Cargando Partida");
		System.out.println("------------------");
		System.out.println("Partida cargada exitosamente");
		System.out.println("");
	}

	public void guardarPartida() { // metodo para guardar la Partida
		System.out.println("-----------------------");
		System.out.println("Guardando Partida...");
		System.out.println("----------------------");
		if (gestorPartida != null) {
			gestorPartida.guardarPartida();
		}
		System.out.println("");
	}

	public void refrescarPartida() { // metodo para refrescar Panel
		System.out.println("----------------------");
		System.out.println("Refrecando Panel...");
		System.out.println("Partida guardada exitosamente ");
		System.out.println("");
	}

}
