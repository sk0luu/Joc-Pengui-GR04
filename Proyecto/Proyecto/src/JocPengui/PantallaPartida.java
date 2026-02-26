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
                System.out.println("\n❌ Cancelado.\n");
            } else if (objeto >= 1 && objeto <= 3) {
                gestor.usarObjeto(objeto);
            } else {
                System.out.println("\n❌ Objeto no válido.\n");
            }
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("\n❌ Error de entrada.\n");
        }
    }
    
    public void botonFinalizarTurno() {
        System.out.println("\n✅ Turno finalizado.\n");
        gestor.siguienteTurno();
    }
    
    public void iniciarPartida() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      ¡LA PARTIDA HA COMENZADO!         ║");
        System.out.println("║    ¡QUE GANE EL MEJOR PINGUINO!        ║");
        System.out.println("╚════════════════════════════════════════╝\n");
    }
    
    public void cargarPartida() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      ¡PARTIDA CARGADA!                 ║");
        System.out.println("║     CONTINUANDO EL JUEGO...            ║");
        System.out.println("╚════════════════════════════════════════╝\n");
    }
    
    public void guardarPartida() {
        System.out.println("\n💾 Guardando partida...");
        gestor.guardarPartida(null);
        System.out.println("✅ Partida guardada exitosamente.\n");
    }
    
    public void refrescaPanel() {
        System.out.println("\n🔄 Refrescando panel...");
        System.out.println("✅ Panel actualizado.\n");
    }
}
