package JocPengui;

import java.util.Scanner;

public class PantallaPartida {
    private Scanner scanner;
    
    public PantallaPartida() {
        this.scanner = new Scanner(System.in);
    }
    
    public void mostrarPartida(Partida partida) {
        boolean juegoActivo = true;
        
        while (juegoActivo) {
            mostrarEstadoPartida(partida);
            int opcion = obtenerOpcion();
            
            switch (opcion) {
                case 1:
                    // Llamar a GestorPartida para tirar dado
                    GestorPartida gestor = new GestorPartida();
                    int resultado = gestor.tirarDado();
                    System.out.println("\n🎲 Resultado del dado: " + resultado);
                    break;
                    
                case 2:
                    System.out.print("\n¿Qué objeto deseas usar? (1-3): ");
                    int objeto = scanner.nextInt();
                    scanner.nextLine();
                    GestorPartida gestor2 = new GestorPartida();
                    gestor2.usarObjeto(objeto);
                    break;
                    
                case 3:
                    System.out.println("\n✅ Turno finalizado.\n");
                    // Llamar a GestorPartida para siguiente turno
                    GestorPartida gestor3 = new GestorPartida();
                    gestor3.siguienteTurno();
                    break;
                    
                case 4:
                    System.out.println("\n💾 Guardando partida...");
                    GestorPartida gestor4 = new GestorPartida();
                    gestor4.guardarPartida(partida);
                    break;
                    
                case 5:
                    System.out.println("\n🔄 Refrescando...\n");
                    break;
                    
                case 6:
                    System.out.print("\n¿Guardar antes de salir? (s/n): ");
                    String resp = scanner.nextLine();
                    if (resp.equalsIgnoreCase("s")) {
                        GestorPartida gestor5 = new GestorPartida();
                        gestor5.guardarPartida(partida);
                    }
                    System.out.println("\n👋 Volviendo al menú principal...\n");
                    juegoActivo = false;
                    break;
                    
                default:
                    System.out.println("\n❌ Opción inválida.\n");
            }
        }
    }
    
    private void mostrarEstadoPartida(Partida partida) {
        System.out.println("");
        System.out.println("ESTADO ACTUAL DE LA PARTIDA ");
        System.out.println("");
        System.out.println("Turno: " + partida.getTurnoActual());            
        System.out.println("Jugadores: " + partida.getNumJugadores());                     
        System.out.println("Posición: " + partida.getPosicion());
        System.out.println("Salud: 100/100 ");
        System.out.println("");
        System.out.println("1. Tirar Dado ");
        System.out.println("2. Usar Objeto ");
        System.out.println("3. Finalizar Turno ");
        System.out.println("4. Guardar Partida ");
        System.out.println("5. Refrescar Panel ");
        System.out.println("6. Salir del Juego ");
        System.out.println("");
        System.out.print("Selecciona una acción (1-6): ");
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
