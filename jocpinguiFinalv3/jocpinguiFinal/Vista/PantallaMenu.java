package jocpinguiFinal.Vista;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.util.ArrayList;
import jocpinguiFinal.Controlador.GestorPartida;

public class PantallaMenu {

    @FXML private MenuItem newGame;
    @FXML private MenuItem saveGame;
    @FXML private MenuItem loadGame;
    @FXML private MenuItem quitGame;

    @FXML private TextField userField; // Aquí el jugador pondrá su nombre
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

        if (!username.isEmpty()) {
            try {
                // 1. LÓGICA DE TU CÓDIGO DE CLASE: Crear el Gestor y la Partida
                GestorPartida gestor = new GestorPartida();
                ArrayList<String> nombres = new ArrayList<>();
                nombres.add(username); // Añadimos el nombre del campo de texto
                
                // Creamos la partida con los nombres (como hacías en consola)
                gestor.nuevaPartida(nombres);

                // 2. CAMBIO DE PANTALLA
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/jocpinguiFinal/Vista/PantallaJuego.fxml"));
                Parent root = loader.load();

                // 3. PASAR EL GESTOR A LA SIGUIENTE PANTALLA
                // Obtenemos el controlador de la pantalla de juego para pasarle la partida ya creada
                PantallaJuego controllerJuego = loader.getController();
                controllerJuego.setGestorPartida(gestor);

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Pinguino Game - En Partida");
                stage.show();

            } catch (Exception e) {
                System.out.println("Error al cargar PantallaJuego.fxml: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: Debes introducir al menos un nombre de usuario.");
        }
    }

    @FXML
    private void handleNewGame() {
        // Limpiamos el campo para una nueva partida
        userField.clear();
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
        // Aquí iría tu gestor.cargarPartida(id);
    }

    @FXML
    private void handleSaveGame() {
        System.out.println("Lógica para guardar partida");
    }

    @FXML
    private void handleRegister() {
        System.out.println("Registro pulsado");
    }
}
