package jocpinguiFinal.Vista;

import java.sql.Connection;

/**
 * Clase singleton para mantener el estado global de la aplicación
 */
public class AppState {
    private static AppState instancia;
    private Connection conexionBD;
    private String usuarioActual;

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
