package jocpinguiFinal.Vista;

import java.sql.Connection;
import javafx.stage.Stage;

/**
 * Clase singleton para mantener el estado global de la aplicación
 */
/**
 * almacena informacion o estado global para la interfaz visual.
 */
public class AppState {
    // variable que guarda informacion sobre instancia
    private static AppState instancia;
    // variable que guarda informacion sobre conexionbd
    private Connection conexionBD;
    // variable que guarda informacion sobre usuarioactual
    private String usuarioActual;
    // variable que guarda informacion sobre ventanaprincipal
    private Stage ventanaPrincipal;

    // metodo encargado de la funcion appstate recibiendo parametros: ninguno
    private AppState() {
    }

    // metodo que devuelve el valor de instance
    public static AppState getInstance() {
        if (instancia == null) {
            instancia = new AppState();
        }
        return instancia;
    }

    // metodo que devuelve el valor de conexionbd
    public Connection getConexionBD() {
        return conexionBD;
    }

    // metodo que actualiza o establece el valor de conexionbd
    public void setConexionBD(Connection conexion) {
        this.conexionBD = conexion;
    }

    // metodo que devuelve el valor de usuarioactual
    public String getUsuarioActual() {
        return usuarioActual;
    }

    // metodo que actualiza o establece el valor de usuarioactual
    public void setUsuarioActual(String usuario) {
        this.usuarioActual = usuario;
    }

    // metodo que devuelve el valor de ventanaprincipal
    public Stage getVentanaPrincipal() {
        return ventanaPrincipal;
    }

    // metodo que actualiza o establece el valor de ventanaprincipal
    public void setVentanaPrincipal(Stage stage) {
        this.ventanaPrincipal = stage;
    }

    // metodo encargado de la funcion cerrarconexion recibiendo parametros: ninguno
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
