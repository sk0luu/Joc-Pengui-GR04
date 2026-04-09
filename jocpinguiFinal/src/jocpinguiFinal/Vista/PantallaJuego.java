package jocpinguiFinal.Vista;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;
import jocpinguiFinal.Controlador.GestorPartida;
import jocpinguiFinal.Model.Agujero;
import jocpinguiFinal.Model.Casilla;
import jocpinguiFinal.Model.Dado;
import jocpinguiFinal.Model.Inventario;
import jocpinguiFinal.Model.Item;
import jocpinguiFinal.Model.Jugador;
import jocpinguiFinal.Model.Normal;
import jocpinguiFinal.Model.Oso;
import jocpinguiFinal.Model.Pinguino;
import jocpinguiFinal.Model.SueloQuebradizo;
import jocpinguiFinal.Model.Foca;
import jocpinguiFinal.Model.Evento;
import jocpinguiFinal.Model.Tablero;
import jocpinguiFinal.Model.Trineo;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PantallaJuego {

	// Elementos del menú principal
	@FXML
	private MenuItem newGame;
	@FXML
	private MenuItem saveGame;
	@FXML
	private MenuItem loadGame;
	@FXML
	private MenuItem quitGame;

	// Botones de acción en la interfaz
	@FXML
	private Button dado;
	@FXML
	private Button rapido;
	@FXML
	private Button lento;
	@FXML
	private Button peces;
	@FXML
	private Button nieve;
	@FXML
	private Button saveGameButton;
	@FXML
	private Button loadGameButton;
	@FXML
	private Button backMenuButton;

	// Textos informativos en pantalla
	@FXML
	private Text dadoResultText;
	@FXML
	private Text rapido_t;
	@FXML
	private Text lento_t;
	@FXML
	private Text peces_t;
	@FXML
	private Text nieve_t;
	@FXML
	private Text eventos;

	// Etiquetas de la interfaz
	@FXML
	private Label jugadorActualLabel;

	// Tablero de juego y piezas de los jugadores
	@FXML
	private GridPane tablero;
	@FXML
	private Circle P1;
	@FXML
	private Circle P2;
	@FXML
	private Circle P3;
	@FXML
	private Circle P4;
	@FXML
	private Circle P5;

	private GestorPartida gestorPartida;
	private Map<Integer, Circle> playerCircles; // Mapeo de índice de jugador a círculo
	private Map<Integer, Integer> playerPositions; // Mapeo de índice de jugador a posición
	private Connection conexionBD;
	private String usuarioActual;

	private static final int COLUMNS = 5;
	private static final int TOTAL_CELLS = 50;
	private static final String TAG_CASILLA_TEXT = "CASILLA_TEXT";

	private Object diceSound;
	private Object bearSound;
	private Object sealSound;
	private Object backgroundMusicPlayer;

	@FXML
	private void initialize() {
		eventos.setText("¡El juego ha comenzado!");
		playerCircles = new HashMap<>();
		playerPositions = new HashMap<>();
		cargarSonidos();

		// Inicializar visibilidad de círculos
		P1.setVisible(true);
		P2.setVisible(false);
		P3.setVisible(false);
		P4.setVisible(false);
		if (P5 != null) P5.setVisible(false);
	}

	public void setGestorPartida(GestorPartida gestor) {
		this.gestorPartida = gestor;

		// Setear la conexión de BB.DD si existe
		if (conexionBD != null) {
			this.gestorPartida.setConexionBD(conexionBD);
		}

		inicializarJuego();
	}

	public void setConexion(Connection conexion) {
		this.conexionBD = conexion;
	}

	public void setUsuario(String usuario) {
		this.usuarioActual = usuario;
	}

	private void cargarSonidos() {
		diceSound = cargarAudioClip("/jocpinguiFinal/resources/sounds/dice_roll.mp3");
		System.out.println("Sonido de dado cargado: " + (diceSound != null));
		bearSound = cargarAudioClip("/jocpinguiFinal/resources/sounds/bear_roar.mp3");
		System.out.println("Sonido de oso cargado: " + (bearSound != null));
		sealSound = cargarAudioClip("/jocpinguiFinal/resources/sounds/seal_sound.mp3");
		System.out.println("Sonido de foca cargado: " + (sealSound != null));

		Object bgMusic = cargarMedia("/jocpinguiFinal/resources/sounds/club_pengui.mp3");
		System.out.println("Música de fondo cargada: " + (bgMusic != null));
		if (bgMusic != null) {
			backgroundMusicPlayer = crearMediaPlayer(bgMusic);
			System.out.println("Reproductor de música creado: " + (backgroundMusicPlayer != null));
			if (backgroundMusicPlayer != null) {
				invocarMetodo(backgroundMusicPlayer, "setCycleCount", int.class, -1);
				invocarMetodo(backgroundMusicPlayer, "setVolume", double.class, 0.25);
				invocarMetodo(backgroundMusicPlayer, "play");
				System.out.println("Música de fondo iniciada");
			} else {
				System.out.println("No se pudo crear el reproductor de música de fondo");
			}
		} else {
			System.out.println("No se pudo cargar la música de fondo");
		}
	}

	private Object cargarAudioClip(String recurso) {
		URL url = getClass().getResource(recurso);
		if (url == null) {
			System.out.println("Audio no encontrado: " + recurso);
			return null;
		}
		return crearInstancia("javafx.scene.media.AudioClip", url.toExternalForm());
	}

	private Object cargarMedia(String recurso) {
		URL url = getClass().getResource(recurso);
		if (url == null) {
			System.out.println("Media no encontrado: " + recurso);
			return null;
		}
		return crearInstancia("javafx.scene.media.Media", url.toExternalForm());
	}

	private Object crearMediaPlayer(Object media) {
		if (media == null) return null;
		try {
			Class<?> mediaPlayerClass = Class.forName("javafx.scene.media.MediaPlayer");
			Class<?> mediaClass = Class.forName("javafx.scene.media.Media");
			java.lang.reflect.Constructor<?> ctor = mediaPlayerClass.getConstructor(mediaClass);
			return ctor.newInstance(media);
		} catch (Exception e) {
			System.out.println("MediaPlayer no disponible: " + e.getMessage());
			return null;
		}
	}

	private Object crearInstancia(String className, String parametro) {
		try {
			Class<?> clazz = Class.forName(className);
			java.lang.reflect.Constructor<?> ctor = clazz.getConstructor(String.class);
			return ctor.newInstance(parametro);
		} catch (Exception e) {
			System.out.println(className + " no disponible: " + e.getMessage());
			return null;
		}
	}

	private void invocarMetodo(Object objeto, String metodo, Class<?> paramType, Object arg) {
		if (objeto == null) return;
		try {
			Class<?> clazz = objeto.getClass();
			clazz.getMethod(metodo, paramType).invoke(objeto, arg);
		} catch (Exception e) {
			System.out.println("Error invocando método " + metodo + ": " + e.getMessage());
		}
	}

	private void invocarMetodo(Object objeto, String metodo) {
		if (objeto == null) return;
		try {
			Class<?> clazz = objeto.getClass();
			clazz.getMethod(metodo).invoke(objeto);
		} catch (Exception e) {
			System.out.println("Error invocando método " + metodo + ": " + e.getMessage());
		}
	}

	private void inicializarJuego() {
		if (gestorPartida == null || gestorPartida.getPartida() == null) {
			eventos.setText("Error: No se inicializó la partida correctamente");
			return;
		}

		// Mapear círculos de jugadores
		ArrayList<Jugador> jugadores = gestorPartida.getPartida().getJugador();
		Circle[] circles = { P1, P2, P3, P4, P5 };

		for (int i = 0; i < jugadores.size() && i < 5; i++) {
			playerCircles.put(i, circles[i]);
			
			// Cargar imagen de pingüino
			Jugador j = jugadores.get(i);
			String colorBuscado = j.getColor() != null ? j.getColor().toLowerCase().replace("ú", "u").replace("ó", "o") : "azul";
			
			String colorPath;
			if (j instanceof Foca) {
				colorPath = "/jocpinguiFinal/Vista/images/foca.png";
			} else {
				if ("negro".equalsIgnoreCase(j.getNom())) {
					colorBuscado = "negro"; // Easter egg
				}
				// Intentar .png primero; si no existe, probar .jpg (ej: pinguino_verde.jpg)
				String basePath = "/jocpinguiFinal/Vista/images/pinguino_" + colorBuscado;
				colorPath = basePath + ".png";
				if (getClass().getResourceAsStream(colorPath) == null) {
					colorPath = basePath + ".jpg";
				}
			}
			try {
				java.io.InputStream is = getClass().getResourceAsStream(colorPath);
				if (is != null) {
					Image img = new Image(is, 80, 80, true, true);
					circles[i].setFill(new ImagePattern(img));
				} else {
					System.out.println("Falta la imagen (añádela para verla en el juego): " + colorPath);
					circles[i].setFill(Color.web(obtenerColorHex(colorBuscado)));
				}
			} catch (Exception e) {
				System.out.println("Error al cargar imagen: " + colorPath);
				circles[i].setFill(Color.web(obtenerColorHex(colorBuscado)));
			}

			// Leer la posición real guardada del jugador (en caso de que se haya cargado de
			// la BB.DD)
			int posGuardada = jugadores.get(i).getPosicion();
			playerPositions.put(i, posGuardada);
			circles[i].setVisible(true);

			// Calcular en qué fila y columna cae esa posición
			int row = posGuardada / COLUMNS;
			int col = posGuardada % COLUMNS;

			GridPane.setRowIndex(circles[i], row);
			GridPane.setColumnIndex(circles[i], col);
			circles[i].setTranslateX(0);
			circles[i].setTranslateY(0);
		}

		// Mostrar tipos de casillas en el tablero
		mostrarTiposDeCasillasEnTablero(gestorPartida.getPartida().getTablero());

		// Actualizar información del jugador actual
		actualizarInfoJugadores();

		// Aseguramos que los jugadores queden por encima de las imágenes del tablero
		for (int i = 0; i < jugadores.size() && i < 5; i++) {
			Circle c = playerCircles.get(i);
			if (c != null) {
				c.toFront();
			}
		}
	}
	
	private String obtenerColorHex(String color) {
		switch (color) {
			case "rojo": return "#ef4444";
			case "verde": return "#22c55e";
			case "amarillo": return "#facc15";
			case "naranja": return "#f97316";
			case "purpura": return "#a855f7";
			case "gris": return "#64748b"; // Foca NPC
			default: return "#2f6fed"; // azul
		}
	}

	private void mostrarTiposDeCasillasEnTablero(Tablero t) {
		tablero.getChildren().removeIf(node -> TAG_CASILLA_TEXT.equals(node.getUserData()));

		for (int i = 0; i < t.getCasillas().size(); i++) {
			Casilla casilla = t.getCasillas().get(i);

			if (i > 0 && i < TOTAL_CELLS - 1) {
				String tipo = casilla.getClass().getSimpleName();

				int row = i / COLUMNS;
				int col = i % COLUMNS;

				javafx.scene.Node nodoCelda;

				if (casilla instanceof Oso) {
					try {
						Image img = new Image(getClass().getResourceAsStream("/jocpinguiFinal/Vista/images/oso.png"));
						javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(img);
						iv.setFitWidth(60);
						iv.setFitHeight(60);
						iv.setPreserveRatio(true);
						nodoCelda = iv;
					} catch (Exception e) {
						Text texto = new Text("Oso");
						texto.getStyleClass().add("cell-type");
						nodoCelda = texto;
					}
				} else if (casilla instanceof Agujero) {
					try {
						Image img = new Image(getClass().getResourceAsStream("/jocpinguiFinal/Vista/images/agujero.png"));
						javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(img);
						iv.setFitWidth(60);
						iv.setFitHeight(60);
						iv.setPreserveRatio(true);
						nodoCelda = iv;
					} catch (Exception e) {
						Text texto = new Text("Agujero");
						texto.getStyleClass().add("cell-type");
						nodoCelda = texto;
					}
				} else if (casilla instanceof Normal) {
					try {
						Image img = new Image(getClass().getResourceAsStream("/jocpinguiFinal/Vista/images/casilla_normal.png"));
						javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(img);
						iv.setFitWidth(60);
						iv.setFitHeight(60);
						iv.setPreserveRatio(true);
						nodoCelda = iv;
					} catch (Exception e) {
						Text texto = new Text("Normal");
						texto.getStyleClass().add("cell-type");
						nodoCelda = texto;
					}
				} else if (casilla instanceof Trineo) {
					try {
						Image img = new Image(getClass().getResourceAsStream("/jocpinguiFinal/Vista/images/casilla_trineo.png"));
						javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(img);
						iv.setFitWidth(60);
						iv.setFitHeight(60);
						iv.setPreserveRatio(true);
						nodoCelda = iv;
					} catch (Exception e) {
						Text texto = new Text("Trineo");
						texto.getStyleClass().add("cell-type");
						nodoCelda = texto;
					}
				} else if (casilla instanceof Evento) {
					try {
						Image img = new Image(getClass().getResourceAsStream("/jocpinguiFinal/Vista/images/casilla_evento.png"));
						javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(img);
						iv.setFitWidth(60);
						iv.setFitHeight(60);
						iv.setPreserveRatio(true);
						nodoCelda = iv;
					} catch (Exception e) {
						Text texto = new Text("Evento");
						texto.getStyleClass().add("cell-type");
						nodoCelda = texto;
					}
				} else if (casilla instanceof SueloQuebradizo) {
					try {
						Image img = new Image(getClass().getResourceAsStream("/jocpinguiFinal/Vista/images/suelo_quebradizo.png"));
						javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(img);
						iv.setFitWidth(60);
						iv.setFitHeight(60);
						iv.setPreserveRatio(true);
						nodoCelda = iv;
					} catch (Exception e) {
						Text texto = new Text("SueloQuebradizo");
						texto.getStyleClass().add("cell-type");
						nodoCelda = texto;
					}
				} else {
					Text texto = new Text(tipo);
					texto.getStyleClass().add("cell-type");
					nodoCelda = texto;
				}

				nodoCelda.setUserData(TAG_CASILLA_TEXT);
				GridPane.setRowIndex(nodoCelda, row);
				GridPane.setColumnIndex(nodoCelda, col);
				GridPane.setHalignment(nodoCelda, javafx.geometry.HPos.CENTER);
				GridPane.setValignment(nodoCelda, javafx.geometry.VPos.CENTER);

				tablero.getChildren().add(nodoCelda);
				nodoCelda.toBack(); // Asegurar que las imágenes de casillas queden por detrás de los jugadores
			}
		}
	}

	private String renderVidaCorazones(int vida) {
		int numCorazones = (int) Math.ceil((double) vida / 20.0);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			if (i < numCorazones) {
				sb.append("\u2764"); // Corazón negro fuerte, visible en casillas
			} else {
				sb.append("\u2661"); // Corazón vacío
			}
		}
		return sb.toString();
	}

	private void actualizarInfoJugadores() {
		if (gestorPartida == null || gestorPartida.getPartida() == null)
			return;

		int indiceActual = gestorPartida.getPartida().getJugadorActual();
		Jugador jugadorActual = gestorPartida.getPartida().getJugador().get(indiceActual);

		String infoExtra = "";
		if (jugadorActual instanceof Pinguino) {
			infoExtra = "   " + renderVidaCorazones(((Pinguino) jugadorActual).getVida());
		}

		jugadorActualLabel.setText("Turno: " + jugadorActual.getNom() + " | Posic: " + jugadorActual.getPosicion() + infoExtra);

		actualizarInventario(jugadorActual);
	}

	private void actualizarInventario(Jugador jugador) {
		if (!(jugador instanceof Pinguino)) {
			rapido_t.setText("Dado rápido: 0");
			lento_t.setText("Dado lento: 0");
			peces_t.setText("Peces: 0");
			nieve_t.setText("Bolas de nieve: 0");
			return;
		}

		Pinguino p = (Pinguino) jugador;
		Inventario inv = p.getInv();

		int pezCount = 0;
		int nieveCount = 0;
		int rapidoCount = 0;
		int lentoCount = 0;

		for (Item item : inv.getItems()) {
			String nombre = item.getNombre().toLowerCase();
			if (nombre.contains("pez")) {
				pezCount += item.getCantidad();
			} else if (nombre.contains("nieve") || nombre.contains("hielo")) {
				nieveCount += item.getCantidad();
			} else if (nombre.contains("rápido")) {
				rapidoCount += item.getCantidad();
			} else if (nombre.contains("lento")) {
				lentoCount += item.getCantidad();
			}
		}

		rapido_t.setText("Dado rápido: " + rapidoCount);
		lento_t.setText("Dado lento: " + lentoCount);
		peces_t.setText("Peces: " + pezCount);
		nieve_t.setText("Bolas de nieve: " + nieveCount);
	}

	// Acciones del menú principal
	@FXML
	private void handleNewGame() {
		System.out.println("New game.");
	}

	@FXML
	private void handleSaveGame() {
		if (gestorPartida == null || gestorPartida.getPartida() == null) {
			eventos.setText("Error: No hay partida para guardar");
			return;
		}

		// Mostrar diálogo para ingreso del nombre de la partida
		TextInputDialog dialog = new TextInputDialog("Mi Partida");
		dialog.setTitle("Guardar Partida");
		dialog.setHeaderText("Ingresa un nombre para la partida");
		dialog.setContentText("Nombre:");

		Optional<String> resultado = dialog.showAndWait();
		if (resultado.isPresent() && !resultado.get().trim().isEmpty()) {
			String nombrePartida = resultado.get().trim();

			if (gestorPartida.guardarPartidaBD(nombrePartida, usuarioActual)) {
				eventos.setText("✓ Partida guardada en BB.DD: " + nombrePartida);
				System.out.println("Partida guardada en BB.DD");
			} else {
				eventos.setText("✗ Error al guardar la partida en BB.DD");
			}
		}
	}

	@FXML
	private void handleLoadGame() {
		ArrayList<String[]> partidas = gestorPartida.listarPartidasBD(usuarioActual);

		if (partidas.isEmpty()) {
			eventos.setText("No hay partidas guardadas");
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
				inicializarJuego();
				eventos.setText("✓ Partida cargada exitosamente: " + resultado.get());
				System.out.println("Partida cargada desde BB.DD");
			} else {
				eventos.setText("✗ Error al cargar la partida");
			}
		}
	}

	@FXML
	private void handleBackMenu() {
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Volver al Menú");
		alerta.setHeaderText("¿Deseas volver al menú?");
		alerta.setContentText("Si vuelves sin guardar, perderás la partida actual.\n¿Qué deseas hacer?");

		ButtonType btnGuardar = new ButtonType("Guardar y Volver");
		ButtonType btnSinGuardar = new ButtonType("Volver sin Guardar");
		ButtonType btnCancelar = new ButtonType("Cancelar");

		alerta.getButtonTypes().setAll(btnGuardar, btnSinGuardar, btnCancelar);

		Optional<ButtonType> resultado = alerta.showAndWait();

		if (resultado.isPresent()) {
			if (resultado.get() == btnGuardar) {
				// Guardar antes de volver
				handleSaveGame();
				volverAlMenu();
			} else if (resultado.get() == btnSinGuardar) {
				// Volver directamente sin guardar
				volverAlMenu();
			}
			// Si es btnCancelar, no hace nada (se queda en el juego)
		}
	}

	private void volverAlMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/jocpinguiFinal/Vista/PantallaPartida.fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root);
			Stage stage = AppState.getInstance().getVentanaPrincipal();
			stage.setScene(scene);
			stage.setTitle("Pinguino Game - Configuración");
			stage.setFullScreen(true);
			stage.show();

		} catch (Exception e) {
			System.out.println("Error al volver al menú: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	private void handleQuitGame() {
		System.out.println("Exit...");
		System.exit(0);
	}

	// Acciones de los botones del juego
	@FXML
	private void handleDado(ActionEvent event) {
		if (gestorPartida == null || gestorPartida.getPartida() == null) {
			eventos.setText("Error: Partida no inicializada");
			return;
		}

		dado.setDisable(true);

		int indiceActual = gestorPartida.getPartida().getJugadorActual();
		Jugador jugadorActual = gestorPartida.getPartida().getJugador().get(indiceActual);

		invocarMetodo(diceSound, "play");
		System.out.println("Sonido de dado intentado");
		// Tirar dado
		Dado d = new Dado();
		int resultado = d.tirar();
		dadoResultText.setText("Ha salido: " + resultado);

		// Guardar posición anterior
		int posAnterior = jugadorActual.getPosicion();

		// Mover jugador
		jugadorActual.setPosicion(posAnterior + resultado);

		// Validar límites
		if (jugadorActual.getPosicion() < 0) {
			jugadorActual.setPosicion(0);
		}
		if (jugadorActual.getPosicion() >= TOTAL_CELLS) {
			jugadorActual.setPosicion(TOTAL_CELLS - 1);
		}

		int posNueva = jugadorActual.getPosicion();
		playerPositions.put(indiceActual, posNueva);

		System.out.println("Jugador " + indiceActual + " movió de " + posAnterior + " a " + posNueva);

		// Animar movimiento
		animarMovimiento(indiceActual, posAnterior, posNueva, () -> {
			// Después de la animación, aplicar efecto de la casilla
			aplicarCasilla(jugadorActual, posNueva);
			comprobarSiChocaConFoca(jugadorActual, posNueva, indiceActual);

			// Pausa para que el jugador vea el efecto
			PauseTransition pause = new PauseTransition(Duration.millis(1500));
			pause.setOnFinished(e -> {
				verificarFinDeJuego(jugadorActual);
			});
			pause.play();
		});
	}

	private void animarMovimiento(int playerIndex, int posAnterior, int posNueva, Runnable onFinished) {
		Circle playerCircle = playerCircles.get(playerIndex);
		if (playerCircle == null)
			return;

		int oldRow = posAnterior / COLUMNS;
		int oldCol = posAnterior % COLUMNS;

		int newRow = posNueva / COLUMNS;
		int newCol = posNueva % COLUMNS;

		double cellWidth = tablero.getWidth() / COLUMNS;
		double cellHeight = tablero.getHeight() / 10;

		double dx = (newCol - oldCol) * cellWidth;
		double dy = (newRow - oldRow) * cellHeight;

		TranslateTransition slide = new TranslateTransition(Duration.millis(350), playerCircle);
		slide.setByX(dx);
		slide.setByY(dy);

		slide.setOnFinished(e -> {
			playerCircle.setTranslateX(0);
			playerCircle.setTranslateY(0);
			GridPane.setRowIndex(playerCircle, newRow);
			GridPane.setColumnIndex(playerCircle, newCol);
			onFinished.run();
		});

		slide.play();
	}

	private void aplicarCasilla(Jugador jugador, int posicion) {
		Tablero tablero = gestorPartida.getPartida().getTablero();
		ArrayList<Casilla> casillas = tablero.getCasillas();

		if (posicion < 0 || posicion >= casillas.size()) {
			return;
		}

		// Mandar a este pingüino al frente para que no quede detrás del texto
		int idx = gestorPartida.getPartida().getJugador().indexOf(jugador);
		if (idx >= 0 && playerCircles.containsKey(idx)) {
			playerCircles.get(idx).toFront();
		}

		Casilla casilla = casillas.get(posicion);
		String mensaje = "";
		int posicionAntes = jugador.getPosicion();
		int posicionDespues = posicionAntes;

		// Aplicar efecto según el tipo de casilla
		if (casilla instanceof Agujero) {
			jugador.setPosicion(0);
			posicionDespues = 0;
			mensaje = "¡" + jugador.getNom() + " cayó en un agujero y volvió al inicio!";
			// Actualizar posición visual inmediatamente
			int jugadorIndex = gestorPartida.getPartida().getJugador().indexOf(jugador);
			if (jugadorIndex >= 0) {
				playerPositions.put(jugadorIndex, 0);
				Circle circle = playerCircles.get(jugadorIndex);
				if (circle != null) {
					GridPane.setRowIndex(circle, 0);
					GridPane.setColumnIndex(circle, 0);
					circle.setTranslateX(0);
					circle.setTranslateY(0);
				}
			}
		} else if (casilla instanceof Trineo) {
			// Trineo: avanza 4 casillas más
			int posicionNueva = posicionAntes + 4;
			if (posicionNueva >= TOTAL_CELLS)
				posicionNueva = TOTAL_CELLS - 1;
			jugador.setPosicion(posicionNueva);
			posicionDespues = posicionNueva;
			mensaje = "¡" + jugador.getNom() + " encontró un trineo y avanzó 4 casillas más!";

			// Actualizar posición visual
			int jugadorIndex = gestorPartida.getPartida().getJugador().indexOf(jugador);
			if (jugadorIndex >= 0) {
				playerPositions.put(jugadorIndex, posicionNueva);
				Circle circle = playerCircles.get(jugadorIndex);
				if (circle != null) {
					int newRow = posicionNueva / COLUMNS;
					int newCol = posicionNueva % COLUMNS;
					GridPane.setRowIndex(circle, newRow);
					GridPane.setColumnIndex(circle, newCol);
					circle.setTranslateX(0);
					circle.setTranslateY(0);
				}
			}
		} else if (casilla instanceof Oso) {
			invocarMetodo(bearSound, "play");
			System.out.println("Sonido de oso intentado");
			int posicionNueva = posicionAntes - 3;
			if (posicionNueva < 0) {
				posicionNueva = 0;
			}

			if (jugador instanceof Pinguino) {
				Pinguino p = (Pinguino) jugador;
				p.setVida(p.getVida() - 20);
				if (p.getVida() <= 0) {
					p.setVida(100);
					posicionNueva = 0;
					mensaje = "¡El oso devoró a " + p.getNom() + "! Revive en la casilla de Salida.";
				} else {
					mensaje = "¡El oso ataca a " + p.getNom() + "! (-20 HP y retrocede 3 casillas)";
				}
			} else {
				mensaje = "¡La foca chocó con un oso y retrocedió 3 casillas!";
			}

			jugador.setPosicion(posicionNueva);
			posicionDespues = posicionNueva;

			// Actualizar posición visual
			int jugadorIndex = gestorPartida.getPartida().getJugador().indexOf(jugador);
			if (jugadorIndex >= 0) {
				playerPositions.put(jugadorIndex, posicionNueva);
				Circle circle = playerCircles.get(jugadorIndex);
				if (circle != null) {
					int newRow = posicionNueva / COLUMNS;
					int newCol = posicionNueva % COLUMNS;
					GridPane.setRowIndex(circle, newRow);
					GridPane.setColumnIndex(circle, newCol);
					circle.setTranslateX(0);
					circle.setTranslateY(0);
				}
			}
		} else if (casilla instanceof SueloQuebradizo) {
			// Suelo quebradizo: 50% probabilidad de retroceder 2
			java.util.Random r = new java.util.Random();
			if (r.nextInt(2) == 0) {
				int posicionNueva = posicionAntes - 2;
				if (posicionNueva < 0)
					posicionNueva = 0;
				jugador.setPosicion(posicionNueva);
				posicionDespues = posicionNueva;
				mensaje = "¡" + jugador.getNom() + " pisó suelo quebradizo y se resbaló 2 casillas!";

				// Actualizar posición visual
				int jugadorIndex = gestorPartida.getPartida().getJugador().indexOf(jugador);
				if (jugadorIndex >= 0) {
					playerPositions.put(jugadorIndex, posicionNueva);
					Circle circle = playerCircles.get(jugadorIndex);
					if (circle != null) {
						int newRow = posicionNueva / COLUMNS;
						int newCol = posicionNueva % COLUMNS;
						GridPane.setRowIndex(circle, newRow);
						GridPane.setColumnIndex(circle, newCol);
						circle.setTranslateX(0);
						circle.setTranslateY(0);
					}
				}
			} else {
				mensaje = "¡" + jugador.getNom() + " pisó suelo quebradizo pero logró mantenerse en pie!";
			}
		} else if (casilla instanceof Evento) {
			Evento e = (Evento) casilla;
			java.util.Random r = new java.util.Random();
			String eventoTexto = e.getEventos()[r.nextInt(e.getEventos().length)];
			
			int movimientoExtra = 0;
			if (eventoTexto.contains("+2")) movimientoExtra = 2;
			else if (eventoTexto.contains("-2")) movimientoExtra = -2;
			else if (eventoTexto.contains("turno")) jugador.setTurnosCongelado(1);

			if (movimientoExtra != 0) {
				int pNueva = posicionAntes + movimientoExtra;
				if (pNueva >= TOTAL_CELLS) pNueva = TOTAL_CELLS - 1;
				if (pNueva < 0) pNueva = 0;
				
				final int posicionFinal = pNueva;
				jugador.setPosicion(posicionFinal);
				posicionDespues = posicionFinal;
				
				int jugadorIndex = gestorPartida.getPartida().getJugador().indexOf(jugador);
				if (jugadorIndex >= 0) {
					playerPositions.put(jugadorIndex, posicionFinal);
					// Animar el salto extra del evento
					animarMovimiento(jugadorIndex, posicionAntes, posicionFinal, () -> {
						aplicarCasilla(jugador, posicionFinal);
						if (jugador instanceof Foca) {
							verificarColisionFoca((Foca) jugador);
						} else {
							comprobarSiChocaConFoca(jugador, posicionFinal, jugadorIndex);
						}
					});
				}
			} else if (jugador instanceof Foca) {
				verificarColisionFoca((Foca) jugador);
			}
			mensaje = "¡Misterio! " + eventoTexto;
		} else if (casilla instanceof Normal) {
			mensaje = "Casilla normal, nada especial pasa.";
		}

		eventos.setText(mensaje);
	}
	
	private void comprobarSiChocaConFoca(Jugador pinguino, int posicion, int indiceActual) {
		if (!(pinguino instanceof Pinguino)) return;
		for (Jugador jug : gestorPartida.getPartida().getJugador()) {
			if (jug instanceof Foca && jug.getPosicion() == posicion) {
				Foca foca = (Foca) jug;
				foca.aplastarJugador((Pinguino) pinguino);
				if (!foca.isSoborno()) {
					invocarMetodo(sealSound, "play");
					System.out.println("Sonido de foca intentado");
					eventos.setText("¡" + pinguino.getNom() + " cayó en la casilla de la Morsa y fue aplastado!");
					Circle c = playerCircles.get(indiceActual);
					GridPane.setRowIndex(c, 0);
					GridPane.setColumnIndex(c, 0);
					c.setTranslateX(0); c.setTranslateY(0);
					c.toFront();
				} else {
					eventos.setText("Cayó en la Morsa, pero estaba sobornada.");
				}
			}
		}
	}

	@FXML
	private void handleRapido() {
		usarItem("Rápido");
	}

	@FXML
	private void handleLento() {
		usarItem("Lento");
	}

	@FXML
	private void handlePeces() {
		usarItem("Pez");
	}

	@FXML
	private void handleNieve() {
		usarItem("Nieve");
	}

	private void usarItem(String tipoItem) {
		if (gestorPartida == null || gestorPartida.getPartida() == null) {
			eventos.setText("Error: Partida no inicializada");
			return;
		}

		int indiceActual = gestorPartida.getPartida().getJugadorActual();
		Jugador jugadorActual = gestorPartida.getPartida().getJugador().get(indiceActual);

		if (!(jugadorActual instanceof Pinguino)) {
			eventos.setText("Error: Jugador no es un pingüino");
			return;
		}

		Pinguino p = (Pinguino) jugadorActual;
		Inventario inv = p.getInv();

		Item itemEncontrado = null;
		for (Item item : inv.getItems()) {
			if (item.getNombre().toLowerCase().contains(tipoItem.toLowerCase())) {
				itemEncontrado = item;
				break;
			}
		}

		if (itemEncontrado == null) {
			eventos.setText(tipoItem + " no disponible en el inventario");
			return;
		}

		// Usar item
		itemEncontrado.setCantidad(itemEncontrado.getCantidad() - 1);
		if (itemEncontrado.getCantidad() <= 0) {
			inv.eliminarItem(itemEncontrado);
		}

		String mensaje = "";

		if (tipoItem.toLowerCase().contains("pez")) {
			// Buscar la foca NPC en la lista de jugadores
			Foca focaNPC = null;
			for (Jugador jf : gestorPartida.getPartida().getJugador()) {
				if (jf instanceof Foca) focaNPC = (Foca) jf;
			}
            
			if (focaNPC != null && !focaNPC.isSoborno()) {
				focaNPC.esSobornado();
				mensaje = "¡" + p.getNom() + " ha sobornado a la Morsa con un Pez!";
			} else {
				p.setVida(Math.min(p.getVida() + 20, 100));
				mensaje = "¡" + p.getNom() + " comió un pez! Vida: " + p.getVida();
			}
			eventos.setText(mensaje);
			actualizarInfoJugadores();
		} else if (tipoItem.toLowerCase().contains("nieve")) {
			// Congelar siguiente jugador
			int indiceSiguiente = (indiceActual + 1) % gestorPartida.getPartida().getJugador().size();
			Jugador jugadorSiguiente = gestorPartida.getPartida().getJugador().get(indiceSiguiente);
			if (jugadorSiguiente instanceof Pinguino) {
				Pinguino siguiente = (Pinguino) jugadorSiguiente;
				siguiente.congelar(1);
				mensaje = "¡" + p.getNom() + " congeló a " + siguiente.getNom() + " por 1 turno!";
			}
			eventos.setText(mensaje);
			actualizarInfoJugadores();
		} else if (tipoItem.toLowerCase().contains("rápido")) {
			dado.setDisable(true);

			invocarMetodo(diceSound, "play");
			System.out.println("Sonido de dado rápido intentado");
			// Tirar dado con bonificación
			Dado d = new Dado();
			int tiro1 = d.tirar();
			int tiro2 = d.tirar();
			int total = Math.max(tiro1, tiro2); // Tomar el mayor

			int posAnterior = p.getPosicion();
			p.moverPosicion(total);
			if (p.getPosicion() >= TOTAL_CELLS)
				p.setPosicion(TOTAL_CELLS - 1);
			if (p.getPosicion() < 0)
				p.setPosicion(0);

			int posNueva = p.getPosicion();
			playerPositions.put(indiceActual, posNueva);

			mensaje = "¡Dado Rápido! Sacaste " + tiro1 + " y " + tiro2 + ". Avanzas " + total + " casillas";

			// Animar movimiento
			animarMovimiento(indiceActual, posAnterior, posNueva, () -> {
				aplicarCasilla(p, posNueva);
				comprobarSiChocaConFoca(p, posNueva, indiceActual);

				// Pausa y siguiente turno
				PauseTransition pause = new PauseTransition(Duration.millis(1500));
				pause.setOnFinished(e -> {
					verificarFinDeJuego(jugadorActual);
				});
				pause.play();
			});
		} else if (tipoItem.toLowerCase().contains("lento")) {
			dado.setDisable(true);

			invocarMetodo(diceSound, "play");
			System.out.println("Sonido de dado lento intentado");
			// Tirar dado con penalización
			Dado d = new Dado();
			int tiro1 = d.tirar();
			int tiro2 = d.tirar();
			int total = Math.min(tiro1, tiro2); // Tomar el menor

			int posAnterior = p.getPosicion();
			p.moverPosicion(total);
			if (p.getPosicion() >= TOTAL_CELLS)
				p.setPosicion(TOTAL_CELLS - 1);
			if (p.getPosicion() < 0)
				p.setPosicion(0);

			int posNueva = p.getPosicion();
			playerPositions.put(indiceActual, posNueva);

			mensaje = "¡Dado Lento! Sacaste " + tiro1 + " y " + tiro2 + ". Avanzas " + total + " casillas";

			// Animar movimiento
			animarMovimiento(indiceActual, posAnterior, posNueva, () -> {
				aplicarCasilla(p, posNueva);
				comprobarSiChocaConFoca(p, posNueva, indiceActual);

				// Pausa y siguiente turno
				PauseTransition pause = new PauseTransition(Duration.millis(1500));
				pause.setOnFinished(e -> {
					verificarFinDeJuego(jugadorActual);
				});
				pause.play();
			});
		}

		eventos.setText(mensaje);
		actualizarInfoJugadores();
	}

	private void verificarFinDeJuego(Jugador j) {
		// Validar si el jugador acaba de ganar para actualizar la Partida
		if (j.getPosicion() >= TOTAL_CELLS - 1) {
			gestorPartida.getPartida().setFinalizado(true);
			gestorPartida.getPartida().setGanador(j);
		}

		if (gestorPartida.getPartida().isFinalizado()) {
			mostrarAnimacionGanador(gestorPartida.getPartida().getGanador());
		} else {
			gestorPartida.siguienteTurno();
			actualizarInfoJugadores();

			int indiceActual = gestorPartida.getPartida().getJugadorActual();
			Jugador nuevoActual = gestorPartida.getPartida().getJugador().get(indiceActual);
			
			if (nuevoActual.estaCongelado()) {
				nuevoActual.pasaTurnoCongelado();
				eventos.setText("❄️ ¡" + nuevoActual.getNom() + " pierde su turno por estar congelado!");
				dado.setDisable(true);
				
				// Saltamos al siguiente jugador automáticamente, con pausa visual
				PauseTransition pause = new PauseTransition(Duration.millis(2000));
				pause.setOnFinished(e -> verificarFinDeJuego(nuevoActual));
				pause.play();
			} else if (nuevoActual instanceof Foca) {
				dado.setDisable(true);
				eventos.setText("¡Turno de la Morsa (Foca)!");
				
				PauseTransition pause = new PauseTransition(Duration.millis(1500));
				pause.setOnFinished(e -> ejecutarTurnoFoca((Foca) nuevoActual, indiceActual));
				pause.play();
			} else {
				dado.setDisable(false);
			}
		}
	}
	
	private void ejecutarTurnoFoca(Foca foca, int indiceActual) {
		Dado d = new Dado();
		int resultado = d.tirar();
		dadoResultText.setText("Saca: " + resultado);

		int posAnterior = foca.getPosicion();
		foca.setPosicion(posAnterior + resultado);
		if (foca.getPosicion() >= TOTAL_CELLS) foca.setPosicion(TOTAL_CELLS - 1);
		
		int posNueva = foca.getPosicion();
		playerPositions.put(indiceActual, posNueva);

		animarMovimiento(indiceActual, posAnterior, posNueva, () -> {
			aplicarCasilla(foca, posNueva);
			Casilla casilla = gestorPartida.getPartida().getTablero().getCasillas().get(posNueva);
			if (!(casilla instanceof Evento)) {
				verificarColisionFoca(foca);
			}
			
			PauseTransition pause = new PauseTransition(Duration.millis(1500));
			pause.setOnFinished(e -> verificarFinDeJuego(foca));
			pause.play();
		});
	}

	private void verificarColisionFoca(Foca foca) {
		boolean colision = false;
		for (Jugador jug : gestorPartida.getPartida().getJugador()) {
			if (jug instanceof Pinguino && jug.getPosicion() == foca.getPosicion()) {
				Pinguino p = (Pinguino) jug;
				foca.aplastarJugador(p);
				
				if (!foca.isSoborno()) {
					eventos.setText("¡La Morsa ha aplastado a " + p.getNom() + "!");
					int idx = gestorPartida.getPartida().getJugador().indexOf(p);
					if (idx >= 0) {
						Circle c = playerCircles.get(idx);
						GridPane.setRowIndex(c, 0);
						GridPane.setColumnIndex(c, 0);
						c.setTranslateX(0); c.setTranslateY(0);
						c.toFront();
					}
				} else {
					eventos.setText("La Morsa iba a aplastar a " + p.getNom() + " pero recordó su soborno.");
				}
				colision = true;
			}
		}
		if (!colision) {
			eventos.setText("La Morsa avanza...");
		}
	}

	private void mostrarAnimacionGanador(Jugador ganador) {
		javafx.scene.layout.StackPane padre = (javafx.scene.layout.StackPane) tablero.getParent();
		javafx.scene.layout.VBox cajaM = new javafx.scene.layout.VBox(20);
		cajaM.setAlignment(javafx.geometry.Pos.CENTER);
		cajaM.setStyle("-fx-background-color: rgba(0, 50, 100, 0.85); -fx-background-radius: 20; -fx-padding: 40;");
		cajaM.setMaxSize(400, 250);

		Text titulo = new Text("¡TENEMOS GANADOR!");
		titulo.setStyle(
				"-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: white; -fx-effect: dropshadow(gaussian, cyan, 10, 0.5, 0, 0);");

		Text subtitulo = new Text("¡Felicidades " + ganador.getNom() + "!");
		subtitulo.setStyle("-fx-font-size: 24px; -fx-fill: #e0f7fa;");

		Button btn = new Button("Volver al Menú");
		btn.setStyle(
				"-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: cyan; -fx-text-fill: #002244; -fx-background-radius: 10;");
		btn.setOnAction(e -> handleBackMenu());

		cajaM.getChildren().addAll(titulo, subtitulo, btn);
		padre.getChildren().add(cajaM);

		cajaM.setOpacity(0);
		cajaM.setScaleX(0.5);
		cajaM.setScaleY(0.5);

		javafx.animation.FadeTransition ft = new javafx.animation.FadeTransition(Duration.millis(800), cajaM);
		ft.setToValue(1.0);

		javafx.animation.ScaleTransition st = new javafx.animation.ScaleTransition(Duration.millis(800), cajaM);
		st.setToX(1.0);
		st.setToY(1.0);
		st.setInterpolator(javafx.animation.Interpolator.EASE_OUT);

		ft.play();
		st.play();
	}
}
