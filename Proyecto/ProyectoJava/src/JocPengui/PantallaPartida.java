package JocPengui;

import java.util.Scanner;

public class PantallaPartida {
    private Scanner scanner;
    private GestorPartida gestor;
    
    public PantallaPartida() {
        this.scanner = new Scanner(System.in);
        this.gestor = new GestorPartida();
    }
    
    public void botonTirarDado() {
        int resultado = gestor.tirarDado();
        System.out.println("Resultado del dado: " + resultado);
        System.out.println("Te mueves " + resultado + " casillas ");
    }
    
    public void botonUserObjeto() {
        System.out.println("INVENTARIO DE OBJETOS: ");
        System.out.println("1. Pez (Restaura 20 HP) ");
        System.out.println("2. Hielo (Congela enemigo) ");
        System.out.println("3. Escudo (Protege 1 turno) ");
        System.out.print("\nSelecciona objeto (0 para cancelar): ");
        
        try {
            int objeto = scanner.nextInt();
            scanner.nextLine();
            
            if (objeto == 0) {
                System.out.println("\nâ�Œ Cancelado.\n");
            } else if (objeto >= 1 && objeto <= 3) {
                gestor.usarObjeto(objeto);
            } else {
                System.out.println("\nâ�Œ Objeto no vÃ¡lido.\n");
            }
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("\nâ�Œ Error de entrada.\n");
        }
    }
    
    public void botonFinalizarTurno() {
        System.out.println("\nâœ… Turno finalizado.\n");
        gestor.siguienteTurno();
    }
    
    public void iniciarPartida() {
        System.out.println("\nâ•”â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•—");
        System.out.println("â•‘      Â¡LA PARTIDA HA COMENZADO!         â•‘");
        System.out.println("â•‘    Â¡QUE GANE EL MEJOR PINGUINO!        â•‘");
        System.out.println("â•šâ•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�\n");
    }
    
    public void cargarPartida() {
        System.out.println("\nâ•”â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•—");
        System.out.println("â•‘      Â¡PARTIDA CARGADA!                 â•‘");
        System.out.println("â•‘     CONTINUANDO EL JUEGO...            â•‘");
        System.out.println("â•šâ•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�\n");
    }
    
    public void guardarPartida() {
        System.out.println("\nðŸ’¾ Guardando partida...");
        gestor.guardarPartida(null);
        System.out.println("âœ… Partida guardada exitosamente.\n");
    }
    
    public void refrescaPanel() {
        System.out.println("\nðŸ”„ Refrescando panel...");
        System.out.println("âœ… Panel actualizado.\n");
    }
}
