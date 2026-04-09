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
            
            // 1. Buscamos el archivo de diseño de tu menú
            Parent raiz = FXMLLoader.load(getClass().getResource("/jocpinguiFinal/Vista/PantallaMenu.fxml"));
            
            // 2. Creamos la escena (lo que va dentro de la ventana)
            Scene escena = new Scene(raiz);
            
            // 3. Le ponemos título y la mostramos
            ventanaPrincipal.setTitle("Juego del Pingüino");
            ventanaPrincipal.setScene(escena);
            ventanaPrincipal.setResizable(true);
            ventanaPrincipal.show();
            
        } catch (Exception e) {
            // Si algo falla al cargar la ventana, nos lo dirá por aquí
            System.out.println("Error al abrir la ventana: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Este comando es el que lanza todo el motor de JavaFX
        launch(args);
    }
}