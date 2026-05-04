package jocpinguiFinal.Controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jocpinguiFinal.Vista.AppState;

// "extends Application" es como decirle a Java: "¡Oye, quiero abrir una ventana!"
public class Main extends Application {

    @Override
    public void start(Stage ventanaPrincipal) {
        try {
            // Guardar la ventana principal en AppState para acceso global
            AppState.getInstance().setVentanaPrincipal(ventanaPrincipal);

            // Cargar la pantalla de carga como primera pantalla
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jocpinguiFinal/Vista/PantallaCarga.fxml"));
            Parent raiz = loader.load();

            // Crear la escena
            Scene escena = new Scene(raiz);

            // Título y fullscreen ANTES de show() para que arranque en pantalla completa
            ventanaPrincipal.setTitle("Joc de'n Pingu");
            ventanaPrincipal.setScene(escena);
            ventanaPrincipal.setFullScreen(true);
            // Deshabilitar la tecla Escape para que no salga del fullscreen
            ventanaPrincipal.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);
            ventanaPrincipal.show();

        } catch (Exception e) {
            System.out.println("Error al abrir la ventana: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Este comando es el que lanza todo el motor de JavaFX
        launch(args);
    }
}