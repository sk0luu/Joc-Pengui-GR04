package JocPengui;

import java.util.Scanner;
import java.util.ArrayList;

public class PantallaMenu {
    private Scanner scanner;
    
    public PantallaMenu() {
        this.scanner = new Scanner(System.in);
    }
    
    public void menu() {
        boolean salir = false;
        
        while (!salir) {
            System.out.println("");
            System.out.println("          MENU PRINCIPAL                ");
            System.out.println("");
            System.out.println("1. Nueva Partida ");
            System.out.println("2. Cargar Partida ");
            System.out.println("3. Salir ");
            System.out.println("");
            System.out.print("Selecciona una opción (1-3): ");
            
            int opcion = obtenerOpcion();
            
            switch (opcion) {
                case 1:
                    botonNuevaPartida();
                    salir = true;
                    break;
                case 2:
                    botonCargarPartida(0);
                    salir = true;
                    break;
                case 3:
                    botonSalir();
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida ");
            }
        }
        scanner.close();
    }
    
    public void botonNuevaPartida() {
        System.out.println("");
        System.out.println("CREAR NUEVA PARTIDA ");
        System.out.println("");
        
        System.out.print("¿Cuántos jugadores? (2-4): ");
        int numJugadores = scanner.nextInt();
        scanner.nextLine();
        
        if (numJugadores < 2 || numJugadores > 4) {
            System.out.println("Numero invalido ");
            return;
        }
        
        ArrayList<String> nombres = new ArrayList<>();
        for (int i = 1; i <= numJugadores; i++) {
            System.out.print("Nombre del Jugador " + i + ": ");
            nombres.add(scanner.nextLine());
        }
        
        GestorPartida gestor = new GestorPartida();
        Partida partida = gestor.crearPartida(nombres);
        
        PantallaPartida pantallaPartida = new PantallaPartida();
        pantallaPartida.mostrarPartida(partida);
    }
    
    public void botonCargarPartida(int id) {
        System.out.println("");
        System.out.println("CARGAR PARTIDA GUARDADA");
        System.out.println("");
        
        System.out.print("Ingresa el ID de la partida: ");
        int idPartida = scanner.nextInt();
        scanner.nextLine();
        
        GestorPartida gestor = new GestorPartida();
        Partida partida = gestor.cargarPartida(idPartida);
        
        if (partida != null) {
            PantallaPartida pantallaPartida = new PantallaPartida();
            pantallaPartida.mostrarPartida(partida);
        } else {
            System.out.println("Partida no encontrada ");
        }
    }
    
    public void botonSalir() {
        System.out.println("");
        System.out.println("Gracias por jugar Pinguino ");
        System.out.println("Hasta la proxima ");
        System.out.println("");
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
}