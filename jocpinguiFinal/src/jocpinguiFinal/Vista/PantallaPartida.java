package jocpinguiFinal.Vista;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ChoiceDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Node;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import jocpinguiFinal.Controlador.GestorPartida;
import jocpinguiFinal.Model.Jugador;
import jocpinguiFinal.Model.Partida;

public class PantallaPartida {
	
	@FXML private MenuItem newGame;
	@FXML private MenuItem saveGame;
	@FXML private MenuItem loadGame;
	@FXML private MenuItem quitGame;
	
	@FXML private TextField player1Field;
	@FXML private TextField player2Field;
	@FXML private TextField player3Field;
	@FXML private TextField player4Field;
	
	@FXML private ComboBox<String> color1Combo;
	@FXML private ComboBox<String> color2Combo;
	@FXML private ComboBox<String> color3Combo;
	@FXML private ComboBox<String> color4Combo;
	
	@FXML private Button startGameButton;
	@FXML private Button backButton;
	@FXML private Button loadGameButton;
	@FXML private Button saveGameButton;
	
	@FXML private Label titleLabel;
	@FXML private Label infoText;
	
	private GestorPartida gestorPartida;
	private Connection conexionBD;
	private String usuarioActual;
	private static final String[] COLORES = {"Azul", "Rojo", "Verde", "Amarillo", "Naranja", "Púrpura"};
	
	@FXML
	private void initialize() {
		System.out.println("PantallaPartida JavaFX inicializada");
		gestorPartida = new GestorPartida();

		// Si hay conexión, setearla en GestorPartida
		if (conexionBD != null) {
			gestorPartida.setConexionBD(conexionBD);
		}

		// Inicializar etiquetas
		if (titleLabel != null) {
			titleLabel.setText("CONFIGURACIÓN DE PARTIDA");
		}
		if (infoText != null) {
			infoText.setText("Ingresa los nombres de los jugadores y elige colores (mínimo 1, máximo 4)");
		}
		
		// Inicializar ComboBox de colores
		if (color1Combo != null) {
			color1Combo.getItems().addAll(COLORES);
			color1Combo.setValue("Azul");
		}
		if (color2Combo != null) {
			color2Combo.getItems().addAll(COLORES);
			color2Combo.setValue("Rojo");
		}
		if (color3Combo != null) {
			color3Combo.getItems().addAll(COLORES);
			color3Combo.setValue("Verde");
		}
		if (color4Combo != null) {
			color4Combo.getItems().addAll(COLORES);
			color4Combo.setValue("Amarillo");
		}
	}
	
	@FXML
	private void handleStartGame(ActionEvent event) {
		ArrayList<String> nombres = new ArrayList<>();
		ArrayList<String> colores = new ArrayList<>();
		
		// Recopilar nombres y colores de jugadores que no estén vacíos
		if (!player1Field.getText().trim().isEmpty()) {
			nombres.add(player1Field.getText().trim());
			colores.add(color1Combo.getValue());
		}
		if (!player2Field.getText().trim().isEmpty()) {
			nombres.add(player2Field.getText().trim());
			colores.add(color2Combo.getValue());
		}
		if (!player3Field.getText().trim().isEmpty()) {
			nombres.add(player3Field.getText().trim());
			colores.add(color3Combo.getValue());
		}
		if (!player4Field.getText().trim().isEmpty()) {
			nombres.add(player4Field.getText().trim());
			colores.add(color4Combo.getValue());
		}
		
		// Validar que hay al menos un jugador
		if (nombres.isEmpty()) {
			infoText.setText("Error: Debes ingresar al menos un nombre de jugador");
			return;
		}
		
		try {
			// Crear la partida con los nombres y colores
			gestorPartida.nuevaPartida(nombres, colores);
			
			// Cambiar a PantallaJuego
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/jocpinguiFinal/Vista/PantallaJuego.fxml"));
			Parent root = loader.load();
			
			// Pasar la conexión y el gestor a la pantalla de juego
			PantallaJuego controllerJuego = loader.getController();
			controllerJuego.setConexion(conexionBD);
			controllerJuego.setUsuario(usuarioActual);
			controllerJuego.setGestorPartida(gestorPartida);
			
			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Pinguino Game - En Partida");
			stage.show();
			
		} catch (Exception e) {
			System.out.println("Error al cargar PantallaJuego.fxml: " + e.getMessage());
			e.printStackTrace();
			infoText.setText("Error: No se pudo iniciar la partida");
		}
	}
	
	@FXML
	private void handleLoadGame(ActionEvent event) {
		String usuario = this.usuarioActual;
		ArrayList<String[]> partidas = gestorPartida.listarPartidasBD(usuario);

		if (partidas.isEmpty()) {
			infoText.setText("No hay partidas guardadas");
			return;
		}

		// Crear lista de opciones con nombre + fecha
		ArrayList<String> opciones = new ArrayList<>();
		Map<String, Integer> mapaNombresID = new HashMap<>();
		for (String[] partida : partidas) {
			String opcion = partida[1] + " (" + partida[2] + ")";
			opciones.add(opcion);
			mapaNombresID.put(opcion, Integer.parseInt(partida[0]));
		}

		// Mostrar diálogo de selección
		ChoiceDialog<String> dialog = new ChoiceDialog<>(opciones.get(0), opciones);
		dialog.setTitle("Cargar Partida");
		dialog.setHeaderText("Selecciona una partida para cargar");
		dialog.setContentText("Partidas:");

		Optional<String> resultado = dialog.showAndWait();
		if (resultado.isPresent()) {
			int idPartida = mapaNombresID.get(resultado.get());
			if (gestorPartida.cargarPartidaBD(idPartida)) {
				// Cargar directamente el juego
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/jocpinguiFinal/Vista/PantallaJuego.fxml"));
					Parent root = loader.load();
					
					PantallaJuego controllerJuego = loader.getController();
					controllerJuego.setConexion(conexionBD);
					controllerJuego.setUsuario(usuarioActual);
					controllerJuego.setGestorPartida(gestorPartida);
					
					Scene scene = new Scene(root);
					Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					stage.setScene(scene);
					stage.setTitle("Pinguino Game - En Partida");
					stage.show();
				} catch (Exception e) {
					System.out.println("Error al cargar PantallaJuego: " + e.getMessage());
					mostrarAlerta("Error", "No se pudo cargar la partida");
				}
			} else {
				mostrarAlerta("Error", "No se pudo cargar la partida del archivo");
			}
		}
	}
	
	@FXML
	private void handleSaveGame(ActionEvent event) {
		if (gestorPartida == null || gestorPartida.getPartida() == null) {
			mostrarAlerta("Error", "No hay partida para guardar. Inicia una partida primero.");
			return;
		}

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Guardar Partida");
		fileChooser.getExtensionFilters().add(
			new FileChooser.ExtensionFilter("Archivos de Partida (*.partida)", "*.partida")
		);
		fileChooser.setInitialFileName("partida.partida");

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		File archivo = fileChooser.showSaveDialog(stage);

		if (archivo != null) {
			if (gestorPartida.guardarPartida(archivo)) {
				mostrarAlerta("Éxito", "Partida guardada en: " + archivo.getName());
				System.out.println("Partida guardada en: " + archivo.getAbsolutePath());
			} else {
				mostrarAlerta("Error", "Error al guardar la partida");
			}
		}
	}
	
	@FXML
	private void handleBack(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/jocpinguiFinal/Vista/PantallaMenu.fxml"));
			Parent root = loader.load();
			
			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Pinguino Game - Menu");
			stage.show();
			
		} catch (Exception e) {
			System.out.println("Error al volver al menú: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleNewGame() {
		player1Field.clear();
		player2Field.clear();
		player3Field.clear();
		player4Field.clear();
		color1Combo.setValue("Azul");
		color2Combo.setValue("Rojo");
		color3Combo.setValue("Verde");
		color4Combo.setValue("Amarillo");
		infoText.setText("Ingresa los nombres de los jugadores y elige colores (mínimo 1, máximo 4)");
	}
	
	@FXML
	private void handleQuitGame() {
		System.out.println("Saliendo del juego...");
		System.exit(0);
	}
	
	@FXML
	private void handleLoadGameMenu() {
		System.out.println("Lógica para cargar partida");
	}
	
	@FXML
	private void handleSaveGameMenu() {
		System.out.println("Lógica para guardar partida");
	}
	
	public void setGestorPartida(GestorPartida gestor) {
		this.gestorPartida = gestor;
	}
	
	public GestorPartida getGestorPartida() {
		return gestorPartida;
	}
	
	public void setConexion(Connection conexion, String usuario) {
		this.conexionBD = conexion;
		this.usuarioActual = usuario;
		// Si el gestor ya fue inicializado, actualizar la conexión
		if (gestorPartida != null && conexion != null) {
			gestorPartida.setConexionBD(conexion);
		}
	}
	
	public String getUsuarioActual() {
		return usuarioActual;
	}
	
	private void mostrarAlerta(String titulo, String mensaje) {
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(mensaje);
		alerta.showAndWait();
	}
}
