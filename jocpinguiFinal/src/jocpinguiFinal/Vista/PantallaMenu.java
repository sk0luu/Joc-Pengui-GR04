package jocpinguiFinal.Vista;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedHashMap;
import java.util.ArrayList;

import jocpinguiFinal.Model.BBDD;

public class PantallaMenu {

    @FXML private MenuItem newGame;
    @FXML private MenuItem saveGame;
    @FXML private MenuItem loadGame;
    @FXML private MenuItem quitGame;

    @FXML private TextField userField;
    @FXML private PasswordField passField;

    @FXML private Button loginButton;
    @FXML private Button registerButton;

    @FXML
    private void initialize() {
        System.out.println("PantallaMenu JavaFX inicializada");
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = userField.getText();
        String password = passField.getText();

        // Validar que no esté vacío
        if (username.trim().isEmpty()) {
            mostrarAlerta("Error", "El usuario no puede estar vacío");
            return;
        }

        Connection conexion = verificarCredencialesOracleYConectar(username, password);
        if (conexion != null) {
            try {
                // Cambio a PantallaPartida
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/jocpinguiFinal/Vista/PantallaPartida.fxml"));
                Parent root = loader.load();
                
                // Pasar la conexión y usuario a PantallaPartida
                PantallaPartida controllerPartida = loader.getController();
                controllerPartida.setConexion(conexion, username);

                Scene scene = new Scene(root);
                Stage stage = AppState.getInstance().getVentanaPrincipal();
                stage.setScene(scene);
                stage.setTitle("Pinguino Game - Configuración");
                stage.show();

            } catch (Exception e) {
                System.out.println("Error al cargar PantallaPartida.fxml: " + e.getMessage());
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo cargar la pantalla de configuración");
            }
        } else {
            mostrarAlerta("Error", "Usuario o contraseña incorrectos");
            passField.clear();
        }
    }

    private Connection verificarCredencialesOracleYConectar(String usuario, String contraseña) {
        Connection con = null;
        try {
            // Usar credenciales proporcionadas para conectar a Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Intentar conectarse con las credenciales del usuario
            String url = "jdbc:oracle:thin:@//oracle.ilerna.com:1521/XEPDB2";
            con = DriverManager.getConnection(url, usuario, contraseña);
            
            // Si llegamos aquí, la conexión fue exitosa
            if (con.isValid(5)) {
                System.out.println("Login exitoso: " + usuario);
                return con; // Retornar la conexión sin cerrar
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Oracle no encontrado: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al conectar a Oracle: " + e.getMessage());
        }
        return null;
    }

    @FXML
    private void handleNewGame() {
        userField.clear();
        passField.clear();
        System.out.println("Preparado para nueva partida");
    }

    @FXML
    private void handleQuitGame() {
        System.out.println("Saliendo del juego...");
        System.exit(0);
    }

    @FXML
    private void handleLoadGame() {
        System.out.println("Lógica para cargar partida (BBDD o Fichero)");
    }

    @FXML
    private void handleSaveGame() {
        System.out.println("Lógica para guardar partida");
    }

    @FXML
    private void handleRegister() {
        mostrarAlerta("Registro", "Para registrar una cuenta, contacte al administrador de la base de datos");
    }

    // Método auxiliar para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
