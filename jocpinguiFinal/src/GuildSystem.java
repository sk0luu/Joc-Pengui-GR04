import java.util.Scanner;
import java.io.*;

/**
 * DAW.0485.PROGRAMACIÓ
 * Prova de validació - Funcions i fitxers
 * Temps: 60 minuts
 * 
 * SISTEMA DE RANGS:
 * E-Rank: 1-20
 * D-Rank: 21-40
 * C-Rank: 41-60
 * B-Rank: 61-80
 * A-Rank: 81-90
 * S-Rank: 91-100
 */
public class GuildSystem {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Array amb els nivells de poder dels hunters 
        int[] hunters = {15, 82, 45, 8, 95, 38, 67, 12, 88, 55, 23, 91, 40, 78, 19};
        
        int opcio;
        do {
            mostrarMenu();
            opcio = sc.nextInt();
            
            switch(opcio) {
                case 1:
                    // TODO: Cridar la funció trobarHunterMesFort passant l'array
                    
                    
                    
                    break;
                    
                case 2:
                    // TODO: Cridar la funció comptarSRank passant l'array
                    
                    
                    
                    break;
                    
                case 3:
                    // TODO: Cridar la funció calcularPoderMitja passant l'array
                    
                    
                    
                    break;
                    
                case 4:
                    // TODO: Cridar la funció eliminarHuntersDebils passant l'array i el miniRank
                    // Aquesta funció retorna un nou array
                    
                    
                    
                    
                    
                    break;
                    
                case 5:
                    // TODO: Guardar les dades actuals al fitxer abans de sortir
                    System.out.println("Sortint del sistema de Guild...");
                    
                    
                    
                    break;
                    
                default:
                    System.out.println("Opció no vàlida!");
            }
        } while(opcio != 5);
        
        sc.close();
    }
    
    /**
     * Mostra el menú principal
     */
    public static void mostrarMenu() {
        System.out.println("\n===== SISTEMA DE GUILD - HUNTERS =====");
        System.out.println("1. Trobar el hunter més fort");
        System.out.println("2. Comptar hunters S-Rank");
        System.out.println("3. Calcular poder mitjà de la Guild");
        System.out.println("4. Expulsar hunters dèbils");
        System.out.println("5. Sortir");
        System.out.print("Escull una opció (1-5): ");
    }
    
    /**
     * FUNCIÓ 1 (2 punts)
     * Troba el hunter amb més poder
     */
    
    
    /**
     * FUNCIÓ 2 (2 punts)
     * Compta quants hunters són S-Rank (poder >= 91)
     */
    
    /**
     * FUNCIÓ 3 (2 punts)
     * Calcula el poder mitjà de tots els hunters
     */
    
    /**
     * FUNCIÓ 4 (2 punts)
     * Elimina els hunters amb poder inferior al mínim especificat
     */
    
    /**
     * Retorna el rang d'un hunter segons el seu poder
     * Funció auxiliar (opcional, però útil)
     */
    public static String obtenirRang(int poder) {
        if (poder >= 91) return "S-Rank";
        if (poder >= 81) return "A-Rank";
        if (poder >= 61) return "B-Rank";
        if (poder >= 41) return "C-Rank";
        if (poder >= 21) return "D-Rank";
        return "E-Rank";
    }
}
