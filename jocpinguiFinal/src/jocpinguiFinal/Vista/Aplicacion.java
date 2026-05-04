package jocpinguiFinal.Vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Aplicacion extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Registrar la ventana principal en AppState
        AppState.getInstance().setVentanaPrincipal(stage);

        // Cargar la pantalla de carga
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jocpinguiFinal/Vista/PantallaCarga.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Joc de'n Pingu");
        stage.setScene(scene);

        // Fullscreen obligatorio desde el primer momento
        stage.setFullScreen(true);
        // Evitar que la tecla Escape salga del fullscreen accidentalmente
        stage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);

        // Asegurar que la aplicación se cierre completamente al cerrar la ventana
        stage.setOnCloseRequest(e -> {
            System.exit(0);
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}