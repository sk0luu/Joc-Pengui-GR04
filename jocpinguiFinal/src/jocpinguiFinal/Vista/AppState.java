package jocpinguiFinal.Vista;

import java.sql.Connection;
import javafx.stage.Stage;

/**
 * Clase singleton para mantener el estado global de la aplicación
 */
public class AppState {
    private static AppState instancia;
    private Connection conexionBD;
    private String usuarioActual;
    private Stage ventanaPrincipal;

    private AppState() {
    }

    public static AppState getInstance() {
        if (instancia == null) {
            instancia = new AppState();
        }
        return instancia;
    }

    public Connection getConexionBD() {
        return conexionBD;
    }

    public void setConexionBD(Connection conexion) {
        this.conexionBD = conexion;
    }

    public String getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(String usuario) {
        this.usuarioActual = usuario;
    }

    public Stage getVentanaPrincipal() {
        return ventanaPrincipal;
    }

    public void setVentanaPrincipal(Stage stage) {
        this.ventanaPrincipal = stage;
    }

    public void cerrarConexion() {
        try {
            if (conexionBD != null && !conexionBD.isClosed()) {
                conexionBD.close();
                System.out.println("Conexión a BB.DD cerrada");
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
