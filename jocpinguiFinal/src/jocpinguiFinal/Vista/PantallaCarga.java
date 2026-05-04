package jocpinguiFinal.Vista;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

public class PantallaCarga {

    @FXML
    private ProgressBar barraProgreso;

    @FXML
    private Text textoEstado;

    @FXML
    private StackPane videoContainer;

    @FXML
    private void initialize() {
        // ── Intentar cargar y reproducir el video ────────────────────────────
        cargarVideo();

        // ── Animar la barra de progreso en paralelo ──────────────────────────
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(barraProgreso.progressProperty(), 0)),
            new KeyFrame(Duration.millis(1000),
                e -> textoEstado.setText("Cargando recursos..."),
                new KeyValue(barraProgreso.progressProperty(), 0.30)),
            new KeyFrame(Duration.millis(2200),
                e -> textoEstado.setText("Preparando el tablero..."),
                new KeyValue(barraProgreso.progressProperty(), 0.65)),
            new KeyFrame(Duration.millis(3400),
                e -> textoEstado.setText("¡Listo para jugar!"),
                new KeyValue(barraProgreso.progressProperty(), 1.0))
        );

        timeline.setOnFinished(e -> cargarMenuPrincipal());
        timeline.play();
    }

    private void cargarVideo() {
        try {
            URL videoUrl = getClass().getResource("/jocpinguiFinal/resources/Video 2.mp4");
            if (videoUrl == null) {
                System.out.println("[PantallaCarga] Video no encontrado en resources.");
                return;
            }

            Media media = new Media(videoUrl.toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // bucle mientras carga
            mediaPlayer.setMute(true);                         // sin audio

            MediaView mediaView = new MediaView(mediaPlayer);
            mediaView.setPreserveRatio(false);

            // El MediaView debe crecer para llenar el StackPane
            mediaView.fitWidthProperty().bind(videoContainer.widthProperty());
            mediaView.fitHeightProperty().bind(videoContainer.heightProperty());

            // Insertar el video detrás de los demás nodos del StackPane
            videoContainer.getChildren().add(0, mediaView);

        } catch (Exception ex) {
            System.out.println("[PantallaCarga] Error al cargar el video: " + ex.getMessage());
        }
    }

    private void cargarMenuPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jocpinguiFinal/Vista/PantallaMenu.fxml"));
            Parent root = loader.load();

            Stage stage = AppState.getInstance().getVentanaPrincipal();
            Scene scene = new Scene(root);

            root.setOpacity(0);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);
            stage.show();

            FadeTransition fadeIn = new FadeTransition(Duration.millis(700), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

        } catch (Exception ex) {
            System.out.println("Error al cargar el menú principal: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
