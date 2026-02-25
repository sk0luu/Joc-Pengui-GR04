package Proyecto;

	import java.util.Scanner;
	import java.util.ArrayList;

	public class PantallaMenu {
	    private Scanner scanner;
	    
	    public PantallaMenu() {
	        this.scanner = new Scanner(System.in);
	    }
	    
	    public void mostrarMenu() {
	        boolean salir = false;
	        
	        while (!salir) {
	            System.out.println("\n╔════════════════════════════════════════╗");
	            System.out.println("║          MENÚ PRINCIPAL                ║");
	            System.out.println("╠════════════════════════════════════════╣");
	            System.out.println("║  1. Nueva Partida                      ║");
	            System.out.println("║  2. Cargar Partida                     ║");
	            System.out.println("║  3. Salir                              ║");
	            System.out.println("╚════════════════════════════════════════╝\n");
	            System.out.print("Selecciona una opción (1-3): ");
	            
	            int opcion = obtenerOpcion();
	            
	            switch (opcion) {
	                case 1:
	                    nuevaPartida();
	                    salir = true;
	                    break;
	                case 2:
	                    cargarPartida();
	                    salir = true;
	                    break;
	                case 3:
	                    System.out.println("\n¡Hasta luego!\n");
	                    salir = true;
	                    break;
	                default:
	                    System.out.println("\n❌ Opción inválida.\n");
	            }
	        }
	        scanner.close();
	    }
	    
	    private int obtenerOpcion() {
	        try {
	            int opcion = scanner.nextInt();
	            scanner.nextLine();
	            return opcion;
	        } catch (Exception e) {
	            scanner.nextLine();
	            return -1;
	        }
	    }
	    
	    private void nuevaPartida() {
	        System.out.println("\n╔════════════════════════════════════════╗");
	        System.out.println("║       CREAR NUEVA PARTIDA              ║");
	        System.out.println("╚════════════════════════════════════════╝\n");
	        
	        // Pedir número de jugadores
	        System.out.print("¿Cuántos jugadores? (2-4): ");
	        int numJugadores = scanner.nextInt();
	        scanner.nextLine();
	        
	        if (numJugadores < 2 || numJugadores > 4) {
	            System.out.println("\n❌ Número inválido.\n");
	            return;
	        }
	        
	        // Pedir nombres de jugadores
	        ArrayList<String> nombres = new ArrayList<>();
	        for (int i = 1; i <= numJugadores; i++) {
	            System.out.print("Nombre del Jugador " + i + ": ");
	            nombres.add(scanner.nextLine());
	        }
	        
	        // Llamar a GestorPartida para crear la partida
	        GestorPartida gestor = new GestorPartida();
	        Partida partida = gestor.crearPartida(nombres);
	        
	        // Mostrar pantalla de partida
	        PantallaPartida pantallaPartida = new PantallaPartida();
	        pantallaPartida.mostrarPartida(partida);
	    }
	    
	    private void cargarPartida() {
	        System.out.println("\n╔════════════════════════════════════════╗");
	        System.out.println("║       CARGAR PARTIDA GUARDADA          ║");
	        System.out.println("╚════════════════════════════════════════╝\n");
	        
	        System.out.print("Ingresa el ID de la partida: ");
	        int idPartida = scanner.nextInt();
	        scanner.nextLine();
	        
	        // Llamar a GestorPartida para cargar la partida
	        GestorPartida gestor = new GestorPartida();
	        Partida partida = gestor.cargarPartida(idPartida);
	        
	        if (partida != null) {
	            // Mostrar pantalla de partida
	            PantallaPartida pantallaPartida = new PantallaPartida();
	            pantallaPartida.mostrarPartida(partida);
	        } else {
	            System.out.println("\n❌ Partida no encontrada.\n");
	        }
	    }
	}

	

