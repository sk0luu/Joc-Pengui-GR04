package jocpinguiFinal.Vista;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Node;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import jocpinguiFinal.Controlador.GestorPartida;
import jocpinguiFinal.Model.Jugador;
import jocpinguiFinal.Model.Partida;

/**
 * pantalla donde se configuran los jugadores y colores antes de empezar.
 */
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
	@FXML private CheckBox focaCheckBox;
	
	@FXML private Label titleLabel;
	@FXML private Label infoText;

	// Ranking panel
	@FXML private VBox rankingBox;
	@FXML private Label rankingEmptyLabel;

	// variable que guarda informacion sobre gestorpartida
	private GestorPartida gestorPartida;
	// variable que guarda informacion sobre conexionbd
	private Connection conexionBD; // conexion a oracle
	// variable que guarda informacion sobre usuarioactual
	private String usuarioActual; // usuario que ha hecho login
	// variable que guarda informacion sobre colores
	private static final String[] COLORES = {"Azul", "Rojo", "Verde", "Amarillo", "Naranja", "Púrpura"};
	
	// inicializa el gestor y los combos de colores
	@FXML
	// metodo encargado de la funcion initialize recibiendo parametros: ninguno
	private void initialize() {
		gestorPartida = new GestorPartida();

		if (conexionBD != null) {
			gestorPartida.setConexionBD(conexionBD);
		}

		if (titleLabel != null) {
			titleLabel.setText("CONFIGURACIÓN DE PARTIDA");
		}
		
		// configura los 4 selectores de colores
		if (color1Combo != null) {
			color1Combo.getItems().addAll(COLORES);
			aplicarCeldaConImagen(color1Combo);
			color1Combo.setValue("Azul");
		}
		if (color2Combo != null) {
			color2Combo.getItems().addAll(COLORES);
			aplicarCeldaConImagen(color2Combo);
			color2Combo.setValue("Rojo");
		}
		if (color3Combo != null) {
			color3Combo.getItems().addAll(COLORES);
			aplicarCeldaConImagen(color3Combo);
			color3Combo.setValue("Verde");
		}
		if (color4Combo != null) {
			color4Combo.getItems().addAll(COLORES);
			aplicarCeldaConImagen(color4Combo);
			color4Combo.setValue("Amarillo");
		}
	}
	
	// metodo encargado de la funcion aplicarceldaconimagen recibiendo parametros: ComboBox<String> combo
	private void aplicarCeldaConImagen(ComboBox<String> combo) {
		combo.setCellFactory(listView -> new javafx.scene.control.ListCell<String>() {
			@Override
			// metodo encargado de la funcion updateitem recibiendo parametros: String item, boolean empty
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
					setGraphic(null);
				} else {
					setText(item);
					try {
						// variable que guarda informacion sobre colorbuscado
						String colorBuscado = item.toLowerCase().replace("ú", "u").replace("ó", "o");
						// variable que guarda informacion sobre base
						String base = "/jocpinguiFinal/Vista/images/pinguino_" + colorBuscado;
						// variable que guarda informacion sobre imagepath
						String imagePath = base + ".png";
						java.io.InputStream is = getClass().getResourceAsStream(imagePath);
						if (is == null) { is = getClass().getResourceAsStream(base + ".jpg"); }
						if (is != null) {
							javafx.scene.image.Image img = new javafx.scene.image.Image(is, 30, 30, true, true);
							setGraphic(new javafx.scene.image.ImageView(img));
						} else {
							setGraphic(null);
						}
					} catch (Exception e) {
						setGraphic(null);
					}
				}
			}
		});
		
		combo.setButtonCell(new javafx.scene.control.ListCell<String>() {
			@Override
			// metodo encargado de la funcion updateitem recibiendo parametros: String item, boolean empty
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
					setGraphic(null);
				} else {
					setText(item);
					try {
						// variable que guarda informacion sobre colorbuscado
						String colorBuscado = item.toLowerCase().replace("ú", "u").replace("ó", "o");
						// variable que guarda informacion sobre base
						String base = "/jocpinguiFinal/Vista/images/pinguino_" + colorBuscado;
						// variable que guarda informacion sobre imagepath
						String imagePath = base + ".png";
						java.io.InputStream is = getClass().getResourceAsStream(imagePath);
						if (is == null) { is = getClass().getResourceAsStream(base + ".jpg"); }
						if (is != null) {
							javafx.scene.image.Image img = new javafx.scene.image.Image(is, 30, 30, true, true);
							setGraphic(new javafx.scene.image.ImageView(img));
						} else {
							setGraphic(null);
						}
					} catch (Exception e) {
						setGraphic(null);
					}
				}
			}
		});
	}
	
	// crea la partida y cambia a la pantalla del tablero
	@FXML
	// metodo encargado de la funcion handlestartgame recibiendo parametros: ActionEvent event
	private void handleStartGame(ActionEvent event) {
		// variable que guarda informacion sobre nombres
		ArrayList<String> nombres = new ArrayList<>();
		// variable que guarda informacion sobre colores
		ArrayList<String> colores = new ArrayList<>();
		
		// añade jugadores si tienen nombre
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
		
		if (nombres.isEmpty()) {
			infoText.setText("Error: Debes ingresar al menos un nombre de jugador");
		} else {
			try {
				// variable que guarda informacion sobre confoca
				boolean conFoca = focaCheckBox != null && focaCheckBox.isSelected();
				gestorPartida.nuevaPartida(nombres, colores, conFoca);
				
				// variable que guarda informacion sobre loader
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/jocpinguiFinal/Vista/PantallaJuego.fxml"));
				// variable que guarda informacion sobre root
				Parent root = loader.load();
				
				// variable que guarda informacion sobre controllerjuego
				PantallaJuego controllerJuego = loader.getController();
				controllerJuego.setConexion(conexionBD);
				controllerJuego.setUsuario(usuarioActual);
				controllerJuego.setGestorPartida(gestorPartida);
				
				// variable que guarda informacion sobre scene
				Scene scene = new Scene(root);
				// variable que guarda informacion sobre stage
				Stage stage = AppState.getInstance().getVentanaPrincipal();
				stage.setScene(scene);
				stage.setTitle("Pinguino Game - En Partida");
				stage.setFullScreen(true);
				stage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);
				stage.show();
			} catch (Exception e) {
				infoText.setText("Error: No se pudo iniciar la partida");
			}
		}
	}
	
	@FXML
	// metodo encargado de la funcion handleloadgame recibiendo parametros: ActionEvent event
	private void handleLoadGame(ActionEvent event) {
		// variable que guarda informacion sobre usuario
		String usuario = this.usuarioActual;
		// variable que guarda informacion sobre partidas
		ArrayList<String[]> partidas = gestorPartida.listarPartidasBD(usuario);

		if (partidas.isEmpty()) {
			infoText.setText("No hay partidas guardadas");
		} else {
			// Crear lista de opciones con nombre + fecha
			ArrayList<String> opciones = new ArrayList<>();
			Map<String, Integer> mapaNombresID = new HashMap<>();
			for (String[] partida : partidas) {
				// variable que guarda informacion sobre opcion
				String opcion = partida[1] + " (" + partida[2] + ")";
				opciones.add(opcion);
				mapaNombresID.put(opcion, Integer.parseInt(partida[0]));
			}

			// Mostrar diálogo de selección
			ChoiceDialog<String> dialog = new ChoiceDialog<>(opciones.get(0), opciones);
			dialog.initOwner(AppState.getInstance().getVentanaPrincipal());
			dialog.setTitle("Cargar Partida");
			dialog.setHeaderText("Selecciona una partida para cargar");
			dialog.setContentText("Partidas:");

			// variable que guarda informacion sobre resultado
			Optional<String> resultado = dialog.showAndWait();
			if (resultado.isPresent()) {
				// variable que guarda informacion sobre idpartida
				int idPartida = mapaNombresID.get(resultado.get());
				if (gestorPartida.cargarPartidaBD(idPartida)) {
					// Cargar directamente el juego
					try {
						// variable que guarda informacion sobre loader
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/jocpinguiFinal/Vista/PantallaJuego.fxml"));
						// variable que guarda informacion sobre root
						Parent root = loader.load();
						
						// variable que guarda informacion sobre controllerjuego
						PantallaJuego controllerJuego = loader.getController();
						controllerJuego.setConexion(conexionBD);
						controllerJuego.setUsuario(usuarioActual);
						controllerJuego.setGestorPartida(gestorPartida);
						
						// variable que guarda informacion sobre scene
						Scene scene = new Scene(root);
						// variable que guarda informacion sobre stage
						Stage stage = AppState.getInstance().getVentanaPrincipal();
						stage.setScene(scene);
						stage.setTitle("Pinguino Game - En Partida");
						stage.setFullScreen(true);
						stage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);
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
	}
	
	@FXML
	// metodo encargado de la funcion handlesavegame recibiendo parametros: ActionEvent event
	private void handleSaveGame(ActionEvent event) {
		if (gestorPartida == null || gestorPartida.getPartida() == null) {
			mostrarAlerta("Error", "No hay partida para guardar. Inicia una partida primero.");
		} else {
			// variable que guarda informacion sobre filechooser
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Guardar Partida");
			fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Archivos de Partida (*.partida)", "*.partida")
			);
			fileChooser.setInitialFileName("partida.partida");

			// variable que guarda informacion sobre stage
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			// variable que guarda informacion sobre archivo
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
	}
	
	@FXML
	// metodo encargado de la funcion handleback recibiendo parametros: ActionEvent event
	private void handleBack(ActionEvent event) {
		try {
			// variable que guarda informacion sobre loader
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/jocpinguiFinal/Vista/PantallaMenu.fxml"));
			// variable que guarda informacion sobre root
			Parent root = loader.load();
			
			// variable que guarda informacion sobre scene
			Scene scene = new Scene(root);
			// variable que guarda informacion sobre stage
			Stage stage = AppState.getInstance().getVentanaPrincipal();
			stage.setScene(scene);
			stage.setTitle("Pinguino Game - Menu");
			stage.setFullScreen(true);
			stage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);
			stage.show();
		} catch (Exception e) {
			System.out.println("Error al volver al menú: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@FXML
	// metodo encargado de la funcion handlenewgame recibiendo parametros: ninguno
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
	// metodo encargado de la funcion handlequitgame recibiendo parametros: ninguno
	private void handleQuitGame() {
		System.out.println("Saliendo del juego...");
		System.exit(0);
	}
	
	@FXML
	// metodo encargado de la funcion handleloadgamemenu recibiendo parametros: ninguno
	private void handleLoadGameMenu() {
		System.out.println("Lógica para cargar partida");
	}
	
	@FXML
	// metodo encargado de la funcion handlesavegamemenu recibiendo parametros: ninguno
	private void handleSaveGameMenu() {
		System.out.println("Lógica para guardar partida");
	}
	
	// metodo que actualiza o establece el valor de gestorpartida
	public void setGestorPartida(GestorPartida gestor) {
		this.gestorPartida = gestor;
	}
	
	// metodo que devuelve el valor de gestorpartida
	public GestorPartida getGestorPartida() {
		return gestorPartida;
	}
	
	// metodo que actualiza o establece el valor de conexion
	public void setConexion(Connection conexion, String usuario) {
		this.conexionBD = conexion;
		this.usuarioActual = usuario;
		// Si el gestor ya fue inicializado, actualizar la conexión
		if (gestorPartida != null && conexion != null) {
			gestorPartida.setConexionBD(conexion);
		}
		// Cargar ranking al recibir la conexión
		cargarRanking();
	}

	// Consulta la BD y rellena el panel de ranking con top 10 victorias
	private void cargarRanking() {
		if (rankingBox == null) return;
		rankingBox.getChildren().clear();

		if (conexionBD == null) {
			if (rankingEmptyLabel != null) rankingEmptyLabel.setText("Sin conexión a la BD");
		} else {
			try {
				String sql = "SELECT NICKNAME, VICTORIAS FROM USUARIO " +
						     "WHERE VICTORIAS > 0 ORDER BY VICTORIAS DESC FETCH FIRST 10 ROWS ONLY";
				// variable que guarda informacion sobre ps
				PreparedStatement ps = conexionBD.prepareStatement(sql);
				// variable que guarda informacion sobre rs
				ResultSet rs = ps.executeQuery();

				// variable que guarda informacion sobre pos
				int pos = 1;
				// variable que guarda informacion sobre haydatos
				boolean hayDatos = false;
				// variable que guarda informacion sobre medallas
				String[] medallas = {"🥇", "🥈", "🥉"};

				while (rs.next()) {
					hayDatos = true;
					// variable que guarda informacion sobre nick
					String nick = rs.getString("NICKNAME");
					// variable que guarda informacion sobre victorias
					int victorias = rs.getInt("VICTORIAS");

					// Fila de ranking
					HBox fila = new HBox(8);
					fila.getStyleClass().add("ranking-row");

					// variable que guarda informacion sobre posstr
					String posStr = pos <= 3 ? medallas[pos - 1] : pos + ".";
					// variable que guarda informacion sobre lblpos
					Label lblPos = new Label(posStr);
					lblPos.getStyleClass().add("ranking-pos");

					// variable que guarda informacion sobre lblnick
					Label lblNick = new Label(nick);
					lblNick.getStyleClass().add("ranking-name");
					HBox.setHgrow(lblNick, Priority.ALWAYS);
					lblNick.setMaxWidth(Double.MAX_VALUE);

					// variable que guarda informacion sobre lblvic
					Label lblVic = new Label(victorias + " ★");
					lblVic.getStyleClass().add("ranking-wins");

					fila.getChildren().addAll(lblPos, lblNick, lblVic);
					rankingBox.getChildren().add(fila);
					pos++;
				}

				rs.close();
				ps.close();

				if (rankingEmptyLabel != null) {
					rankingEmptyLabel.setVisible(!hayDatos);
					rankingEmptyLabel.setText(hayDatos ? "" : "Aún no hay victorias registradas");
				}

			} catch (Exception e) {
				System.out.println("Error cargando ranking: " + e.getMessage());
				if (rankingEmptyLabel != null) rankingEmptyLabel.setText("Error al cargar el ranking");
			}
		}
	}
	
	// metodo que devuelve el valor de usuarioactual
	public String getUsuarioActual() {
		return usuarioActual;
	}
	
	// metodo encargado de la funcion mostraralerta recibiendo parametros: String titulo, String mensaje
	private void mostrarAlerta(String titulo, String mensaje) {
		// variable que guarda informacion sobre alerta
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.initOwner(AppState.getInstance().getVentanaPrincipal());
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(mensaje);
		alerta.showAndWait();
	}
}
