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
}