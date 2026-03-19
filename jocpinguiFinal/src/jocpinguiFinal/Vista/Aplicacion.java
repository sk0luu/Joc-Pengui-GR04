package jocpinguiFinal.Vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Aplicacion extends Application {

    @Override
    public void start(Stage stage) {
        // Prueba mínima de JavaFX
        Label label = new Label("¡Funciona papi!");
        Scene scene = new Scene(label, 400, 250);

        stage.setTitle("Prueba JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args); // Arranca la aplicación JavaFX
    }
}