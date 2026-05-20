package jocpinguiFinal.Vista;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
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

/**
 * vista principal donde se dibuja el tablero, los pinguinos y se interactua durante el juego.
 */
public class PantallaJuego {

	// Elementos del menú principal
	@FXML
	// variable que guarda informacion sobre newgame
	private MenuItem newGame;
	@FXML
	// variable que guarda informacion sobre savegame
	private MenuItem saveGame;
	@FXML
	// variable que guarda informacion sobre loadgame
	private MenuItem loadGame;
	@FXML
	// variable que guarda informacion sobre quitgame
	private MenuItem quitGame;

	// Botones de acción en la interfaz
	@FXML
	// variable que guarda informacion sobre dado
	private Button dado;
	@FXML
	// variable que guarda informacion sobre rapido
	private Button rapido;
	@FXML
	// variable que guarda informacion sobre lento
	private Button lento;
	@FXML
	// variable que guarda informacion sobre peces
	private Button peces;
	@FXML
	// variable que guarda informacion sobre nieve
	private Button nieve;
	@FXML
	// variable que guarda informacion sobre savegamebutton
	private Button saveGameButton;
	@FXML
	// variable que guarda informacion sobre loadgamebutton
	private Button loadGameButton;
	@FXML
	// variable que guarda informacion sobre backmenubutton
	private Button backMenuButton;

	// Textos informativos en pantalla
	@FXML
	// variable que guarda informacion sobre dadoresulttext
	private Text dadoResultText;
	@FXML
	// variable que guarda informacion sobre rapido_t
	private Text rapido_t;
	@FXML
	// variable que guarda informacion sobre lento_t
	private Text lento_t;
	@FXML
	// variable que guarda informacion sobre peces_t
	private Text peces_t;
	@FXML
	// variable que guarda informacion sobre nieve_t
	private Text nieve_t;
	@FXML
	private javafx.scene.text.TextFlow eventosFlow;

	// Etiquetas de la interfaz
	@FXML
	// variable que guarda informacion sobre jugadoractuallabel
	private Label jugadorActualLabel;
	@FXML
	private javafx.scene.layout.VBox topJugadoresBox;
	@FXML
	private javafx.scene.image.ImageView imagenDado;

	// Tablero de juego y piezas de los jugadores
	@FXML
	// variable que guarda informacion sobre tablero
	private GridPane tablero;
	@FXML
	// variable que guarda informacion sobre p1
	private Circle P1;
	@FXML
	// variable que guarda informacion sobre p2
	private Circle P2;
	@FXML
	// variable que guarda informacion sobre p3
	private Circle P3;
	@FXML
	// variable que guarda informacion sobre p4
	private Circle P4;
	@FXML
	// variable que guarda informacion sobre p5
	private Circle P5;

	// variable que guarda informacion sobre gestorpartida
	private GestorPartida gestorPartida;
	private Map<Integer, Circle> playerCircles; // Mapeo de índice de jugador a círculo
	private Map<Integer, Integer> playerPositions; // Mapeo de índice de jugador a posición
	// variable que guarda informacion sobre conexionbd
	private Connection conexionBD;
	// variable que guarda informacion sobre usuarioactual
	private String usuarioActual;

	// variable que guarda informacion sobre columns
	private static final int COLUMNS = 5;
	// variable que guarda informacion sobre total_cells
	private static final int TOTAL_CELLS = 50;
	// variable que guarda informacion sobre tag_casilla_text
	private static final String TAG_CASILLA_TEXT = "CASILLA_TEXT";

	// variable que guarda informacion sobre dicesound
	private Object diceSound;
	// variable que guarda informacion sobre agujerosound
	private Object agujeroSound;
	// variable que guarda informacion sobre sealsound
	private Object sealSound;
	// variable que guarda informacion sobre victoriasound
	private Object victoriaSound;
	// variable que guarda informacion sobre trineosound
	private Object trineoSound;
	// variable que guarda informacion sobre hielosound
	private Object hieloSound;
	// variable que guarda informacion sobre eventosound
	private Object eventoSound;
	// variable que guarda informacion sobre backgroundmusicplayer
	private Object backgroundMusicPlayer;

	private java.util.LinkedList<String> historialEventos = new java.util.LinkedList<>();

	// metodo encargado de la funcion agregarevento recibiendo parametros: String msg
	private void agregarEvento(String msg) {
		if (msg == null || msg.trim().isEmpty())
			return;
		System.out.println("[EVENTO] " + msg); // Consola para depuración

		javafx.application.Platform.runLater(() -> {
			historialEventos.add(msg);
			if (historialEventos.size() > 5) {
				historialEventos.removeFirst();
			}

			if (eventosFlow != null) {
				eventosFlow.getChildren().clear();
				for (int i = 0; i < historialEventos.size(); i++) {
					javafx.scene.text.Text t = new javafx.scene.text.Text(
							historialEventos.get(i) + (i < historialEventos.size() - 1 ? "\n\n" : ""));
					if (i == historialEventos.size() - 1) {
						t.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 15px;");
					} else {
						t.setStyle("-fx-fill: #ef4444; -fx-font-size: 14px; -fx-opacity: 0.9;");
					}
					eventosFlow.getChildren().add(t);
				}
			}
		});
	}

	@FXML
	// metodo encargado de la funcion initialize recibiendo parametros: ninguno
	private void initialize() {
		playerCircles = new HashMap<>();
		playerPositions = new HashMap<>();
		cargarSonidos();
	}

	// metodo que actualiza o establece el valor de gestorpartida
	public void setGestorPartida(GestorPartida gestor) {
		this.gestorPartida = gestor;
		if (conexionBD != null) {
			this.gestorPartida.setConexionBD(conexionBD);
		}
		iniciarLogicaJuego();
	}

	// metodo que actualiza o establece el valor de conexion
	public void setConexion(Connection conexion) {
		this.conexionBD = conexion;
	}

	// metodo que actualiza o establece el valor de usuario
	public void setUsuario(String usuario) {
		this.usuarioActual = usuario;
	}

	// metodo encargado de la funcion cargarsonidos recibiendo parametros: ninguno
	private void cargarSonidos() {
		diceSound = cargarAudioClip("/jocpinguiFinal/resources/sounds/dado.mp3");
		System.out.println("Sonido de dado cargado: " + (diceSound != null));
		agujeroSound = cargarAudioClip("/jocpinguiFinal/resources/sounds/agujero.mp3");
		System.out.println("Sonido de agujero cargado: " + (agujeroSound != null));
		sealSound = cargarAudioClip("/jocpinguiFinal/resources/sounds/foca.mp3");
		System.out.println("Sonido de foca cargado: " + (sealSound != null));
		victoriaSound = cargarAudioClip("/jocpinguiFinal/resources/sounds/victoria.mp3");
		System.out.println("Sonido de victoria cargado: " + (victoriaSound != null));
		trineoSound = cargarAudioClip("/jocpinguiFinal/resources/sounds/trineo.mp3");
		System.out.println("Sonido de trineo cargado: " + (trineoSound != null));
		hieloSound = cargarAudioClip("/jocpinguiFinal/resources/sounds/sueloquebradizo.mp3");
		System.out.println("Sonido de hielo cargado: " + (hieloSound != null));
		eventoSound = cargarAudioClip("/jocpinguiFinal/resources/sounds/ruido evento.mp3");
		System.out.println("Sonido de evento cargado: " + (eventoSound != null));

		// variable que guarda informacion sobre bgmusic
		Object bgMusic = cargarMedia("/jocpinguiFinal/resources/sounds/musica_fondo.mp3");
		System.out.println("Música de fondo cargada: " + (bgMusic != null));
		if (bgMusic != null) {
			backgroundMusicPlayer = crearMediaPlayer(bgMusic);
			System.out.println("Reproductor de música creado: " + (backgroundMusicPlayer != null));
			if (backgroundMusicPlayer != null) {
				invocarMetodo(backgroundMusicPlayer, "setCycleCount", int.class, -1);
				invocarMetodo(backgroundMusicPlayer, "setVolume", double.class, 0.08);
				invocarMetodo(backgroundMusicPlayer, "play");
				System.out.println("Música de fondo iniciada");
			} else {
				System.out.println("No se pudo crear el reproductor de música de fondo");
			}
		} else {
			System.out.println("No se pudo cargar la música de fondo");
		}
	}

	// metodo encargado de la funcion cargaraudioclip recibiendo parametros: String recurso
	private Object cargarAudioClip(String recurso) {
		// variable que guarda informacion sobre url
		URL url = getClass().getResource(recurso);
		if (url == null) {
			System.out.println("Audio no encontrado: " + recurso);
			return null;
		}
		// metodo encargado de la funcion crearinstancia recibiendo parametros: "javafx.scene.media.AudioClip", url.toExternalForm()
		return crearInstancia("javafx.scene.media.AudioClip", url.toExternalForm());
	}

	// metodo encargado de la funcion cargarmedia recibiendo parametros: String recurso
	private Object cargarMedia(String recurso) {
		// variable que guarda informacion sobre url
		URL url = getClass().getResource(recurso);
		if (url == null) {
			System.out.println("Media no encontrado: " + recurso);
			return null;
		}
		// metodo encargado de la funcion crearinstancia recibiendo parametros: "javafx.scene.media.Media", url.toExternalForm()
		return crearInstancia("javafx.scene.media.Media", url.toExternalForm());
	}

	// metodo encargado de la funcion crearmediaplayer recibiendo parametros: Object media
	private Object crearMediaPlayer(Object media) {
		if (media == null)
			return null;
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

	// metodo encargado de la funcion crearinstancia recibiendo parametros: String className, String parametro
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

	// metodo encargado de la funcion invocarmetodo recibiendo parametros: Object objeto, String metodo, Class<?> paramType, Object arg
	private void invocarMetodo(Object objeto, String metodo, Class<?> paramType, Object arg) {
		if (objeto == null)
			return;
		try {
			Class<?> clazz = objeto.getClass();
			clazz.getMethod(metodo, paramType).invoke(objeto, arg);
		} catch (Exception e) {
			System.out.println("Error invocando método " + metodo + ": " + e.getMessage());
		}
	}

	// metodo encargado de la funcion invocarmetodo recibiendo parametros: Object objeto, String metodo
	private void invocarMetodo(Object objeto, String metodo) {
		if (objeto == null)
			return;
		try {
			Class<?> clazz = objeto.getClass();
			clazz.getMethod(metodo).invoke(objeto);
		} catch (Exception e) {
			System.out.println("Error invocando método " + metodo + ": " + e.getMessage());
		}
	}

	// Método auxiliar para limpiar el flujo de eventos si es necesario
	private void limpiarEventos() {
		if (eventosFlow != null) {
			eventosFlow.getChildren().clear();
			historialEventos.clear();
		}
	}

	// metodo encargado de la funcion obtenercolorhex recibiendo parametros: String color
	private String obtenerColorHex(String color) {
		switch (color) {
			case "rojo":
				return "#ef4444";
			case "verde":
				return "#22c55e";
			case "amarillo":
				return "#facc15";
			case "naranja":
				return "#f97316";
			case "purpura":
				return "#a855f7";
			case "gris":
				return "#64748b"; // Foca NPC
			default:
				return "#2f6fed"; // azul
		}
	}

	// metodo encargado de la funcion mostrartiposdecasillasentablero recibiendo parametros: Tablero t
	private void mostrarTiposDeCasillasEnTablero(Tablero t) {
		tablero.getChildren().removeIf(node -> TAG_CASILLA_TEXT.equals(node.getUserData()));

		// variable que guarda informacion sobre insertindex
		int insertIndex = 0;
		// Añadir fondo de cristal a cada celda del grid
		for (int i = 0; i < TOTAL_CELLS; i++) {
			// variable que guarda informacion sobre row
			int row = i / COLUMNS;
			// variable que guarda informacion sobre col
			int col = i % COLUMNS;
			javafx.scene.layout.Pane bgCell = new javafx.scene.layout.Pane();
			bgCell.getStyleClass().add("cell-bg");
			bgCell.setUserData(TAG_CASILLA_TEXT);
			GridPane.setRowIndex(bgCell, row);
			GridPane.setColumnIndex(bgCell, col);
			tablero.getChildren().add(insertIndex++, bgCell);
		}

		for (int i = 0; i < t.getCasillas().size(); i++) {
			// variable que guarda informacion sobre casilla
			Casilla casilla = t.getCasillas().get(i);

			if (i > 0 && i < TOTAL_CELLS - 1) {
				// variable que guarda informacion sobre tipo
				String tipo = casilla.getClass().getSimpleName();

				// variable que guarda informacion sobre row
				int row = i / COLUMNS;
				// variable que guarda informacion sobre col
				int col = i % COLUMNS;

				javafx.scene.Node nodoCelda;

				if (casilla instanceof Oso) {
					try {
						// variable que guarda informacion sobre img
						Image img = new Image(getClass().getResourceAsStream("/jocpinguiFinal/Vista/images/oso.png"));
						javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(img);
						iv.setFitWidth(60);
						iv.setFitHeight(60);
						iv.setPreserveRatio(true);
						nodoCelda = iv;
					} catch (Exception e) {
						// variable que guarda informacion sobre texto
						Text texto = new Text("Oso");
						texto.getStyleClass().add("cell-type");
						nodoCelda = texto;
					}
				} else if (casilla instanceof Agujero) {
					try {
						Image img = new Image(
								getClass().getResourceAsStream("/jocpinguiFinal/Vista/images/agujero.png"));
						javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(img);
						iv.setFitWidth(60);
						iv.setFitHeight(60);
						iv.setPreserveRatio(true);
						nodoCelda = iv;
					} catch (Exception e) {
						// variable que guarda informacion sobre texto
						Text texto = new Text("Agujero");
						texto.getStyleClass().add("cell-type");
						nodoCelda = texto;
					}
				} else if (casilla instanceof Normal) {
					try {
						Image img = new Image(
								getClass().getResourceAsStream("/jocpinguiFinal/Vista/images/casilla_normal.png"));
						javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(img);
						iv.setFitWidth(60);
						iv.setFitHeight(60);
						iv.setPreserveRatio(true);
						nodoCelda = iv;
					} catch (Exception e) {
						// variable que guarda informacion sobre texto
						Text texto = new Text("Normal");
						texto.getStyleClass().add("cell-type");
						nodoCelda = texto;
					}
				} else if (casilla instanceof Trineo) {
					try {
						Image img = new Image(
								getClass().getResourceAsStream("/jocpinguiFinal/Vista/images/casilla_trineo.png"));
						javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(img);
						iv.setFitWidth(60);
						iv.setFitHeight(60);
						iv.setPreserveRatio(true);
						nodoCelda = iv;
					} catch (Exception e) {
						// variable que guarda informacion sobre texto
						Text texto = new Text("Trineo");
						texto.getStyleClass().add("cell-type");
						nodoCelda = texto;
					}
				} else if (casilla instanceof Evento) {
					try {
						Image img = new Image(
								getClass().getResourceAsStream("/jocpinguiFinal/Vista/images/casilla_evento.png"));
						javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(img);
						iv.setFitWidth(60);
						iv.setFitHeight(60);
						iv.setPreserveRatio(true);
						nodoCelda = iv;
					} catch (Exception e) {
						// variable que guarda informacion sobre texto
						Text texto = new Text("Evento");
						texto.getStyleClass().add("cell-type");
						nodoCelda = texto;
					}
				} else if (casilla instanceof SueloQuebradizo) {
					try {
						Image img = new Image(
								getClass().getResourceAsStream("/jocpinguiFinal/Vista/images/suelo_quebradizo.png"));
						javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(img);
						iv.setFitWidth(60);
						iv.setFitHeight(60);
						iv.setPreserveRatio(true);
						nodoCelda = iv;
					} catch (Exception e) {
						// variable que guarda informacion sobre texto
						Text texto = new Text("SueloQuebradizo");
						texto.getStyleClass().add("cell-type");
						nodoCelda = texto;
					}
				} else {
					// variable que guarda informacion sobre texto
					Text texto = new Text(tipo);
					texto.getStyleClass().add("cell-type");
					nodoCelda = texto;
				}

				nodoCelda.setUserData(TAG_CASILLA_TEXT);
				GridPane.setRowIndex(nodoCelda, row);
				GridPane.setColumnIndex(nodoCelda, col);
				GridPane.setHalignment(nodoCelda, javafx.geometry.HPos.CENTER);
				GridPane.setValignment(nodoCelda, javafx.geometry.VPos.CENTER);

				tablero.getChildren().add(insertIndex++, nodoCelda);
			}
		}
	}

	// metodo encargado de la funcion actualizarinfojugadores recibiendo parametros: ninguno
	private void actualizarInfoJugadores() {
		if (gestorPartida == null || gestorPartida.getPartida() == null)
			return;

		// variable que guarda informacion sobre indiceactual
		int indiceActual = gestorPartida.getPartida().getJugadorActual();
		// variable que guarda informacion sobre jugadoractual
		Jugador jugadorActual = gestorPartida.getPartida().getJugador().get(indiceActual);

		// variable que guarda informacion sobre infoextra
		String infoExtra = "";

		jugadorActualLabel
				.setText("Turno: " + jugadorActual.getNom() + " | Posic: " + jugadorActual.getPosicion() + infoExtra);

		actualizarInventario(jugadorActual);
		actualizarTopJugadores();
	}

	// metodo encargado de la funcion actualizartopjugadores recibiendo parametros: ninguno
	private void actualizarTopJugadores() {
		if (topJugadoresBox == null || gestorPartida == null || gestorPartida.getPartida() == null)
			return;
		topJugadoresBox.getChildren().clear();
		// variable que guarda informacion sobre jugadores
		ArrayList<Jugador> jugadores = new ArrayList<>(gestorPartida.getPartida().getJugador());
		jugadores.sort((j1, j2) -> Integer.compare(j2.getPosicion(), j1.getPosicion()));
		for (int i = 0; i < jugadores.size(); i++) {
			// variable que guarda informacion sobre j
			Jugador j = jugadores.get(i);
			// variable que guarda informacion sobre label
			Label label = new Label((i + 1) + ". " + j.getNom() + " - Casilla " + j.getPosicion());
			label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #cbd5e1;");
			topJugadoresBox.getChildren().add(label);
		}
	}

	// metodo encargado de la funcion animardado recibiendo parametros: ninguno
	private void animarDado() {
		if (imagenDado != null) {
			javafx.animation.RotateTransition rt = new javafx.animation.RotateTransition(Duration.millis(350),
					imagenDado);
			rt.setByAngle(360);
			rt.setCycleCount(1);
			rt.play();
		}
	}

	// metodo encargado de la funcion actualizarinventario recibiendo parametros: Jugador jugador
	private void actualizarInventario(Jugador jugador) {
		if (!(jugador instanceof Pinguino)) {
			rapido_t.setText("Dado rápido: 0");
			lento_t.setText("Dado lento: 0");
			peces_t.setText("Peces: 0");
			nieve_t.setText("Bolas de nieve: 0");
		} else {
			// variable que guarda informacion sobre p
			Pinguino p = (Pinguino) jugador;
			// variable que guarda informacion sobre inv
			Inventario inv = p.getInv();

			// variable que guarda informacion sobre pezcount
			int pezCount = 0;
			// variable que guarda informacion sobre nievecount
			int nieveCount = 0;
			// variable que guarda informacion sobre rapidocount
			int rapidoCount = 0;
			// variable que guarda informacion sobre lentocount
			int lentoCount = 0;

			for (Item item : inv.getItems()) {
				// variable que guarda informacion sobre nombre
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
	}

	// Acciones del menú principal
	@FXML
	// metodo encargado de la funcion handlenewgame recibiendo parametros: ninguno
	private void handleNewGame() {
		System.out.println("New game.");
	}

	@FXML
	// metodo encargado de la funcion handlesavegame recibiendo parametros: ninguno
	private void handleSaveGame() {
		if (gestorPartida == null || gestorPartida.getPartida() == null) {
			agregarEvento("Error: No hay partida para guardar");
		} else {
			// Mostrar diálogo para ingreso del nombre de la partida
			TextInputDialog dialog = new TextInputDialog("Mi Partida");
			dialog.initOwner(AppState.getInstance().getVentanaPrincipal());
			dialog.setTitle("Guardar Partida");
			dialog.setHeaderText("Ingresa un nombre para la partida");
			dialog.setContentText("Nombre:");

			// variable que guarda informacion sobre resultado
			Optional<String> resultado = dialog.showAndWait();
			if (resultado.isPresent() && !resultado.get().trim().isEmpty()) {
				// variable que guarda informacion sobre nombrepartida
				String nombrePartida = resultado.get().trim();

				if (gestorPartida.guardarPartidaBD(nombrePartida, usuarioActual)) {
					agregarEvento("Partida guardada en BB.DD: " + nombrePartida);
					System.out.println("Partida guardada en BB.DD");
				} else {
					agregarEvento("Error al guardar la partida en BB.DD");
				}
			}
		}
	}

	@FXML
	// metodo encargado de la funcion handleloadgame recibiendo parametros: ninguno
	private void handleLoadGame() {
		// variable que guarda informacion sobre partidas
		ArrayList<String[]> partidas = gestorPartida.listarPartidasBD(usuarioActual);

		if (partidas.isEmpty()) {
			agregarEvento("No hay partidas guardadas");
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
					iniciarLogicaJuego();
					agregarEvento("Partida cargada: " + resultado.get());
					System.out.println("Partida cargada desde BB.DD");
				} else {
					agregarEvento("Error al cargar la partida");
				}
			}
		}
	}

	@FXML
	// metodo encargado de la funcion handlebackmenu recibiendo parametros: ninguno
	private void handleBackMenu() {
		// variable que guarda informacion sobre alerta
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.initOwner(AppState.getInstance().getVentanaPrincipal());
		alerta.setTitle("Volver al Menú");
		alerta.setHeaderText("¿Deseas volver al menú?");
		alerta.setContentText("Si vuelves sin guardar, perderás la partida actual.\n¿Qué deseas hacer?");

		// variable que guarda informacion sobre btnguardar
		ButtonType btnGuardar = new ButtonType("Guardar y Volver");
		// variable que guarda informacion sobre btnsinguardar
		ButtonType btnSinGuardar = new ButtonType("Volver sin Guardar");
		// variable que guarda informacion sobre btncancelar
		ButtonType btnCancelar = new ButtonType("Cancelar");

		alerta.getButtonTypes().setAll(btnGuardar, btnSinGuardar, btnCancelar);

		// variable que guarda informacion sobre resultado
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

	// metodo encargado de la funcion volveralmenu recibiendo parametros: ninguno
	private void volverAlMenu() {
		try {
			// Limpiar el guard de animación para que futuras partidas puedan registrar victorias
			Stage stage = AppState.getInstance().getVentanaPrincipal();
			if (stage != null) {
				stage.getProperties().remove("ANIMACION_MOSTRADA");
			}

			// variable que guarda informacion sobre loader
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/jocpinguiFinal/Vista/PantallaPartida.fxml"));
			// variable que guarda informacion sobre root
			Parent root = loader.load();

			// Pasar la conexión y el usuario al controlador para que el ranking
			// se actualice con los datos frescos de la BD
			PantallaPartida controllerPartida = loader.getController();
			controllerPartida.setConexion(conexionBD, usuarioActual);

			// variable que guarda informacion sobre scene
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Pinguino Game - Configuración");
			stage.setFullScreen(true);
			stage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);
			stage.show();

		} catch (Exception e) {
			System.out.println("Error al volver al menú: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Vuelve al menú directamente sin diálogo (usada desde la pantalla de victoria)
	private void volverAlMenuDirecto() {
		invocarMetodo(backgroundMusicPlayer, "stop");
		volverAlMenu();
	}

	@FXML
	// metodo encargado de la funcion handlequitgame recibiendo parametros: ninguno
	private void handleQuitGame() {
		System.out.println("Exit...");
		System.exit(0);
	}

	// Acciones de los botones del juego
	@FXML
	// metodo encargado de la funcion handledado recibiendo parametros: ActionEvent event
	private void handleDado(ActionEvent event) {
		if (gestorPartida == null || gestorPartida.getPartida() == null) {
			agregarEvento("Error: Partida no inicializada");
		} else {
			dado.setDisable(true);
			// variable que guarda informacion sobre indiceactual
			int indiceActual = gestorPartida.getPartida().getJugadorActual();
			// variable que guarda informacion sobre jugadoractual
			Jugador jugadorActual = gestorPartida.getPartida().getJugador().get(indiceActual);
			if (jugadorActual.estaCongelado()) {
				agregarEvento("¡" + jugadorActual.getNom() + " está congelado y pierde su turno!");
				// variable que guarda informacion sobre pause
				PauseTransition pause = new PauseTransition(Duration.millis(1500));
				pause.setOnFinished(e -> {
					jugadorActual.pasaTurnoCongelado();
					gestorPartida.siguienteTurno();
					actualizarInfoJugadores();
					dado.setDisable(false);
				});
				pause.play();
			} else {
				invocarMetodo(diceSound, "play");
				System.out.println("Sonido de dado intentado");
				animarDado();
				// Tirar dado
				Dado d = new Dado();
				// variable que guarda informacion sobre resultado
				int resultado = d.tirar();
				if ("admin#67".equals(jugadorActual.getNom())) {
					resultado = 6;
				}
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
				// variable que guarda informacion sobre posnueva
				int posNueva = jugadorActual.getPosicion();
				playerPositions.put(indiceActual, posNueva);
				System.out.println("Jugador " + indiceActual + " movió de " + posAnterior + " a " + posNueva);
				// Animar movimiento
				animarMovimiento(indiceActual, posAnterior, posNueva, () -> {
					try {
						// Después de la animación, aplicar efecto de la casilla
						aplicarCasilla(jugadorActual, posNueva);
						comprobarSiChocaConFoca(jugadorActual, posNueva, indiceActual);
					} catch (Exception e) {
						System.err.println("Error ejecutando casillas: " + e.getMessage());
						e.printStackTrace();
					}
					// Pausa para que el jugador vea el efecto
					PauseTransition pause = new PauseTransition(Duration.millis(2500));
					pause.setOnFinished(e -> {
						verificarFinDeJuego(jugadorActual);
					});
					pause.play();
				});
			}
		}
	}

	// metodo encargado de la funcion animarmovimiento recibiendo parametros: int playerIndex, int posAnterior, int posNueva, Runnable onFinished
	private void animarMovimiento(int playerIndex, int posAnterior, int posNueva, Runnable onFinished) {
		// variable que guarda informacion sobre playercircle
		Circle playerCircle = playerCircles.get(playerIndex);
		if (playerCircle == null)
			return;

		// variable que guarda informacion sobre oldrow
		int oldRow = posAnterior / COLUMNS;
		// variable que guarda informacion sobre oldcol
		int oldCol = posAnterior % COLUMNS;

		// variable que guarda informacion sobre newrow
		int newRow = posNueva / COLUMNS;
		// variable que guarda informacion sobre newcol
		int newCol = posNueva % COLUMNS;

		// variable que guarda informacion sobre cellwidth
		double cellWidth = tablero.getWidth() / COLUMNS;
		// variable que guarda informacion sobre cellheight
		double cellHeight = tablero.getHeight() / 10;

		// variable que guarda informacion sobre dx
		double dx = (newCol - oldCol) * cellWidth;
		// variable que guarda informacion sobre dy
		double dy = (newRow - oldRow) * cellHeight;

		// variable que guarda informacion sobre slide
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

	// metodo encargado de la funcion aplicarcasilla recibiendo parametros: Jugador jugador, int posicion
	private void aplicarCasilla(Jugador jugador, int posicion) {
		// variable que guarda informacion sobre tablero
		Tablero tablero = gestorPartida.getPartida().getTablero();
		// variable que guarda informacion sobre casillas
		ArrayList<Casilla> casillas = tablero.getCasillas();

		if (posicion < 0 || posicion >= casillas.size()) {
			return;
		}

		// ── MODO ADMIN: inmunidad total a efectos de casilla ─────────────────
		if ("admin#67".equals(jugador.getNom())) {
			agregarEvento("[ADMIN] Inmune a los efectos de la casilla.");
		} else {

		// Mandar a este pingüino al frente para que no quede detrás del texto

		// variable que guarda informacion sobre idx
		int idx = gestorPartida.getPartida().getJugador().indexOf(jugador);
		if (idx >= 0 && playerCircles.containsKey(idx)) {
			playerCircles.get(idx).toFront();
		}

		// variable que guarda informacion sobre casilla
		Casilla casilla = casillas.get(posicion);
		// variable que guarda informacion sobre mensaje
		String mensaje = "";
		// variable que guarda informacion sobre posicionantes
		int posicionAntes = jugador.getPosicion();
		// variable que guarda informacion sobre posiciondespues
		int posicionDespues = posicionAntes;

		try {
			// Aplicar efecto según el tipo de casilla
			if (casilla instanceof Agujero) {
				invocarMetodo(agujeroSound, "play");

				// Delegar al modelo: Agujero.realizarAccion() aplica la mecánica correcta:
				//   · primer agujero (sin agujero previo) → posición 0 (inicio)
				//   · cualquier otro agujero → agujero anterior más cercano
				int posAntesDelAgujero = jugador.getPosicion();
				casilla.realizarAccion(gestorPartida.getPartida(), jugador);
				// variable que guarda informacion sobre destino
				int destino = jugador.getPosicion();
				posicionDespues = destino;

				System.out.println("[DEBUG] Jugador cae en hoyo en " + posAntesDelAgujero + ", destino: " + destino);

				if (destino == 0) {
					mensaje = "¡" + jugador.getNom() + " cayó en el primer agujero y volvió al inicio!";
				} else {
					mensaje = "¡" + jugador.getNom() + " cayó en un agujero y volvió al anterior en casilla " + destino + "!";
				}

				// Actualizar posición visual
				int jugadorIndex = gestorPartida.getPartida().getJugador().indexOf(jugador);
				if (jugadorIndex >= 0) {
					playerPositions.put(jugadorIndex, destino);
					// variable que guarda informacion sobre circle
					Circle circle = playerCircles.get(jugadorIndex);
					if (circle != null) {
						// variable que guarda informacion sobre newrow
						int newRow = destino / COLUMNS;
						// variable que guarda informacion sobre newcol
						int newCol = destino % COLUMNS;
						GridPane.setRowIndex(circle, newRow);
						GridPane.setColumnIndex(circle, newCol);
						circle.setTranslateX(0);
						circle.setTranslateY(0);
					}
				}

				agregarEvento(mensaje);
			} else if (casilla instanceof Trineo) {
				invocarMetodo(trineoSound, "play");
				System.out.println("Sonido de trineo intentado");
				// variable que guarda informacion sobre posicionnueva
				int posicionNueva = -1;
				// variable que guarda informacion sobre trineoencontrado
				boolean trineoEncontrado = false;
				for (int i = 0; i < casillas.size() && !trineoEncontrado; i++) {
					// variable que guarda informacion sobre c
					Casilla c = casillas.get(i);
					if (c instanceof Trineo && c.getPosicion() > posicion) {
						posicionNueva = c.getPosicion();
						trineoEncontrado = true;
					}
				}

				if (posicionNueva != -1) {
					mensaje = "¡" + jugador.getNom() + " encontró un trineo y avanzó al siguiente en la casilla "
							+ posicionNueva + "!";
					jugador.setPosicion(posicionNueva);
					posicionDespues = posicionNueva;

					// Actualizar posición visual
					int jugadorIndex = gestorPartida.getPartida().getJugador().indexOf(jugador);
					if (jugadorIndex >= 0) {
						playerPositions.put(jugadorIndex, posicionNueva);
						// variable que guarda informacion sobre circle
						Circle circle = playerCircles.get(jugadorIndex);
						if (circle != null) {
							// variable que guarda informacion sobre newrow
							int newRow = posicionNueva / COLUMNS;
							// variable que guarda informacion sobre newcol
							int newCol = posicionNueva % COLUMNS;
							GridPane.setRowIndex(circle, newRow);
							GridPane.setColumnIndex(circle, newCol);
							circle.setTranslateX(0);
							circle.setTranslateY(0);
						}
					}

					agregarEvento(mensaje);
					// NO llamar recursivamente para evitar ir de trineo en trineo hasta el final
				} else {
					mensaje = "¡" + jugador.getNom() + " encontró el último trineo!";
					agregarEvento(mensaje);
				}
			} else if (casilla instanceof Oso) {
				if (jugador instanceof Foca) {
					invocarMetodo(sealSound, "play");
				}

				// Dejar que el modelo decida: si tiene pez lo consume (soborno),
				// si no lo tiene lo manda al inicio.
				int posAntesDelOso = jugador.getPosicion();
				casilla.realizarAccion(gestorPartida.getPartida(), jugador);
				// variable que guarda informacion sobre posicionnueva
				int posicionNueva = jugador.getPosicion();

				if (posicionNueva == posAntesDelOso) {
					// El pez fue consumido; el jugador se queda donde está
					mensaje = "¡" + jugador.getNom() + " sobornó al oso con un pez! Sigue en la casilla " + posicionNueva + ".";
					// Actualizar panel de inventario para reflejar el pez consumido
					if (jugador instanceof Pinguino) {
						actualizarInventario((Pinguino) jugador);
					}
					agregarEvento(mensaje);
				} else {
					// No tenía pez; el modelo ya lo mandó al inicio (posición 0)
					mensaje = "¡El oso ataca a " + jugador.getNom() + "! Vuelve al inicio.";
					posicionDespues = posicionNueva;

					// Actualizar posición visual
					int jugadorIndex = gestorPartida.getPartida().getJugador().indexOf(jugador);
					if (jugadorIndex >= 0) {
						playerPositions.put(jugadorIndex, posicionNueva);
						// variable que guarda informacion sobre circle
						Circle circle = playerCircles.get(jugadorIndex);
						if (circle != null) {
							// variable que guarda informacion sobre newrow
							int newRow = posicionNueva / COLUMNS;
							// variable que guarda informacion sobre newcol
							int newCol = posicionNueva % COLUMNS;
							GridPane.setRowIndex(circle, newRow);
							GridPane.setColumnIndex(circle, newCol);
							circle.setTranslateX(0);
							circle.setTranslateY(0);
						}
					}

					agregarEvento(mensaje);
					// Aplicar recursivamente el efecto de la nueva casilla (posición 0 = salida)
					aplicarCasilla(jugador, posicionNueva);
				}
			} else if (casilla instanceof SueloQuebradizo) {
				// variable que guarda informacion sobre cantitems
				int cantItems = 0;
				if (jugador instanceof Pinguino) {
					cantItems = ((Pinguino) jugador).getInv().getTotalItems();
				}

				if (cantItems > 5) {
					invocarMetodo(hieloSound, "play");
					// variable que guarda informacion sobre posicionnueva
					int posicionNueva = 0;
					jugador.setPosicion(posicionNueva);
					posicionDespues = posicionNueva;
					mensaje = "¡" + jugador.getNom() + " tiene demasiado peso! El hielo se rompe y vuelve al inicio.";

					// Actualizar posición visual
					int jugadorIndex = gestorPartida.getPartida().getJugador().indexOf(jugador);
					if (jugadorIndex >= 0) {
						playerPositions.put(jugadorIndex, posicionNueva);
						// variable que guarda informacion sobre circle
						Circle circle = playerCircles.get(jugadorIndex);
						if (circle != null) {
							GridPane.setRowIndex(circle, 0);
							GridPane.setColumnIndex(circle, 0);
							circle.setTranslateX(0);
							circle.setTranslateY(0);
						}
					}
					agregarEvento(mensaje);
				} else if (cantItems > 0) {
					invocarMetodo(hieloSound, "play");
					jugador.congelar(1);
					mensaje = "¡El hielo cruje! " + jugador.getNom() + " se queda atrapado un turno.";
					agregarEvento(mensaje);
				} else {
					mensaje = "¡" + jugador.getNom() + " no lleva peso y pasa el suelo quebradizo con cuidado!";
					agregarEvento(mensaje);
				}
			} else if (casilla instanceof Evento) {
				invocarMetodo(eventoSound, "play");
				// variable que guarda informacion sobre e
				Evento e = (Evento) casilla;
				java.util.Random r = new java.util.Random();
				// variable que guarda informacion sobre eventotexto
				String eventoTexto = e.getEventos()[r.nextInt(e.getEventos().length)];
				agregarEvento("¡Evento! " + jugador.getNom() + ": " + eventoTexto);

				// Lógica para dar objetos al jugador si el evento lo dice
				if (jugador instanceof Pinguino) {
					// variable que guarda informacion sobre p
					Pinguino p = (Pinguino) jugador;
					if (eventoTexto.toLowerCase().contains("pez")) {
						p.getInv().añadirItem(new jocpinguiFinal.Model.ItemConcreto("Pez", 1));
					} else if (eventoTexto.toLowerCase().contains("nieve")) {
						p.getInv().añadirItem(new jocpinguiFinal.Model.ItemConcreto("Bola de Nieve", 1));
					} else if (eventoTexto.toLowerCase().contains("rápido")) {
						p.getInv().añadirItem(new jocpinguiFinal.Model.ItemConcreto("Dado Rápido", 1));
					} else if (eventoTexto.toLowerCase().contains("lento")) {
						p.getInv().añadirItem(new jocpinguiFinal.Model.ItemConcreto("Dado Lento", 1));
					}
					actualizarInventario(p);
				}

				// variable que guarda informacion sobre movimientoextra
				int movimientoExtra = 0;
				if (eventoTexto.contains("+2"))
					movimientoExtra = 2;
				else if (eventoTexto.contains("-2"))
					movimientoExtra = -2;
				else if (eventoTexto.contains("turno"))
					jugador.setTurnosCongelado(1);

				if (movimientoExtra != 0) {
					// variable que guarda informacion sobre pnueva
					int pNueva = posicionAntes + movimientoExtra;
					if (pNueva >= TOTAL_CELLS)
						pNueva = TOTAL_CELLS - 1;
					if (pNueva < 0)
						pNueva = 0;

					// variable que guarda informacion sobre posicionfinal
					final int posicionFinal = pNueva;
					jugador.setPosicion(posicionFinal);
					posicionDespues = posicionFinal;

					// variable que guarda informacion sobre jugadorindex
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
			} else if (casilla instanceof Normal) {
				mensaje = jugador.getNom() + " está en la casilla " + posicion;
			}

		} catch (Exception e) {
			System.err.println("Excepción dentro de aplicarCasilla: " + e.getMessage());
			e.printStackTrace();
		}

		agregarEvento(mensaje);
		}
	}

	// metodo encargado de la funcion comprobarsichocaconfoca recibiendo parametros: Jugador pinguino, int posicion, int indiceActual
	private void comprobarSiChocaConFoca(Jugador pinguino, int posicion, int indiceActual) {
		if (pinguino instanceof Pinguino && !"admin#67".equals(pinguino.getNom())) {
			// variable que guarda informacion sobre p
			Pinguino p = (Pinguino) pinguino;
			for (Jugador jug : gestorPartida.getPartida().getJugador()) {
				if (jug instanceof Foca && jug.getPosicion() == posicion) {
					// variable que guarda informacion sobre foca
					Foca foca = (Foca) jug;

					if (foca.estaCongelado()) {
						agregarEvento("La Foca está bloqueada y no hace nada.");
					} else if (foca.isSoborno()) {
						// Ya fue sobornada manualmente → no ataca
						agregarEvento("La Foca recuerda el soborno y deja pasar a " + p.getNom() + ".");
						// Resetear el soborno para el próximo encuentro
						foca.setSoborno(false);
					} else {
						// No fue sobornada → ataca
						invocarMetodo(sealSound, "play");
						// variable que guarda informacion sobre mejorpos
						int mejorPos = -1;
						for (int i = 0; i < posicion; i++) {
							if (gestorPartida.getPartida().getTablero().getCasillas().get(i) instanceof Agujero) {
								mejorPos = i;
							}
						}
						// variable que guarda informacion sobre destino
						int destino = (mejorPos != -1) ? mejorPos : 0;
						p.setPosicion(destino);
						agregarEvento("¡La Foca golpea a " + p.getNom() + " y lo envía al agujero anterior (casilla "
								+ destino + ")!");

						// Mover visualmente
						playerPositions.put(indiceActual, destino);
						// variable que guarda informacion sobre circle
						Circle circle = playerCircles.get(indiceActual);
						if (circle != null) {
							// variable que guarda informacion sobre r
							int r = destino / COLUMNS;
							// variable que guarda informacion sobre c
							int c = destino % COLUMNS;
							GridPane.setRowIndex(circle, r);
							GridPane.setColumnIndex(circle, c);
							circle.setTranslateX(0);
							circle.setTranslateY(0);
						}
						// Aplicar efecto de la casilla de destino
						aplicarCasilla(p, destino);
					}
				}
			}
		}
	}

	@FXML
	// metodo encargado de la funcion handlerapido recibiendo parametros: ninguno
	private void handleRapido() {
		usarItem("Rápido");
	}

	@FXML
	// metodo encargado de la funcion handlelento recibiendo parametros: ninguno
	private void handleLento() {
		usarItem("Lento");
	}

	@FXML
	// metodo encargado de la funcion handlepeces recibiendo parametros: ninguno
	private void handlePeces() {
		usarItem("Pez");
	}

	@FXML
	// metodo encargado de la funcion handlenieve recibiendo parametros: ninguno
	private void handleNieve() {
		usarItem("Nieve");
	}

	// metodo encargado de la funcion usaritem recibiendo parametros: String tipoItem
	private void usarItem(String tipoItem) {
		if (gestorPartida == null || gestorPartida.getPartida() == null) {
			agregarEvento("Error: Partida no inicializada");
		} else {
			// variable que guarda informacion sobre indiceactual
			int indiceActual = gestorPartida.getPartida().getJugadorActual();
			// variable que guarda informacion sobre jugadoractual
			Jugador jugadorActual = gestorPartida.getPartida().getJugador().get(indiceActual);

			if (!(jugadorActual instanceof Pinguino)) {
				agregarEvento("Error: Jugador no es un pingüino");
			} else {
				// variable que guarda informacion sobre p
				Pinguino p = (Pinguino) jugadorActual;
				// variable que guarda informacion sobre inv
				Inventario inv = p.getInv();

				// variable que guarda informacion sobre itemencontrado
				Item itemEncontrado = null;
				// variable que guarda informacion sobre itembuscado
				boolean itemBuscado = false;
				// variable que guarda informacion sobre listaitemsp
				ArrayList<Item> listaItemsP = inv.getItems();
				for (int i = 0; i < listaItemsP.size() && !itemBuscado; i++) {
					// variable que guarda informacion sobre item
					Item item = listaItemsP.get(i);
					if (item.getNombre().toLowerCase().contains(tipoItem.toLowerCase())) {
						itemEncontrado = item;
						itemBuscado = true;
					}
				}

				if (itemEncontrado == null) {
					agregarEvento(tipoItem + " no disponible en el inventario");
				} else {

		// variable que guarda informacion sobre mensaje
		String mensaje = "";

		if (tipoItem.toLowerCase().contains("pez")) {
			// Buscar la foca NPC en la lista de jugadores
			Foca focaNPC = null;
			for (Jugador jf : gestorPartida.getPartida().getJugador()) {
				if (jf instanceof Foca)
					focaNPC = (Foca) jf;
			}

			if (focaNPC != null && !focaNPC.isSoborno()) {
				focaNPC.esSobornado();
				mensaje = "¡" + p.getNom() + " ha sobornado a la Foca con un Pez!";
			} else {
				mensaje = "¡" + p.getNom() + " comió un pez!";
			}
			// Consumir item
			itemEncontrado.setCantidad(itemEncontrado.getCantidad() - 1);
			if (itemEncontrado.getCantidad() <= 0) {
				inv.eliminarItem(itemEncontrado);
			}

			agregarEvento(mensaje);
			actualizarInfoJugadores();
		} else if (tipoItem.toLowerCase().contains("nieve")) {
			// Obtener lista de posibles objetivos (otros jugadores y foca)
			ArrayList<Jugador> todos = gestorPartida.getPartida().getJugador();
			// variable que guarda informacion sobre nombresobjetivos
			ArrayList<String> nombresObjetivos = new ArrayList<>();
			Map<String, Jugador> mapaObjetivos = new HashMap<>();

			for (Jugador j : todos) {
				if (!j.getNom().equals(p.getNom())) {
					// variable que guarda informacion sobre label
					String label = j.getNom() + (j instanceof Foca ? " (Foca)" : "");
					nombresObjetivos.add(label);
					mapaObjetivos.put(label, j);
				}
			}

			if (nombresObjetivos.isEmpty()) {
				agregarEvento("No hay objetivos para lanzar la bola de nieve");
			} else {
				// variable que guarda informacion sobre dialog
				ChoiceDialog<String> dialog = new ChoiceDialog<>(nombresObjetivos.get(0), nombresObjetivos);
				dialog.initOwner(AppState.getInstance().getVentanaPrincipal());
				dialog.setTitle("Lanzar Bola de Nieve");
				dialog.setHeaderText("¿A quién quieres congelar?");
				dialog.setContentText("Objetivo:");

				// variable que guarda informacion sobre result
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					// variable que guarda informacion sobre objetivo
					Jugador objetivo = mapaObjetivos.get(result.get());
					objetivo.congelar(1);

					// Consumir item ahora que sabemos que se ha usado
					itemEncontrado.setCantidad(itemEncontrado.getCantidad() - 1);
					if (itemEncontrado.getCantidad() <= 0) {
						inv.eliminarItem(itemEncontrado);
					}

					mensaje = "¡" + p.getNom() + " lanzó una bola de nieve a " + objetivo.getNom() + "!";
					agregarEvento(mensaje);
					actualizarInfoJugadores();
				}
				// Si el usuario canceló el diálogo, no se consume el item ni se hace nada
			}
		} else if (tipoItem.toLowerCase().contains("rápido")) {
			// Consumir item
			itemEncontrado.setCantidad(itemEncontrado.getCantidad() - 1);
			if (itemEncontrado.getCantidad() <= 0) {
				inv.eliminarItem(itemEncontrado);
			}

			dado.setDisable(true);

			invocarMetodo(diceSound, "play");
			System.out.println("Sonido de dado rápido intentado");
			animarDado();
			// Tirar dado especial rápido: 5-10
			java.util.Random r = new java.util.Random();
			// variable que guarda informacion sobre total
			int total = r.nextInt(6) + 5; // 5 a 10 (0-5 + 5)
			// variable que guarda informacion sobre tiro1
			int tiro1 = total; // Para el mensaje
			// variable que guarda informacion sobre tiro2
			int tiro2 = 0;

			if ("admin#67".equals(p.getNom())) {
				total = 10;
			}

			// variable que guarda informacion sobre posanterior
			int posAnterior = p.getPosicion();
			p.moverPosicion(total);
			if (p.getPosicion() >= TOTAL_CELLS)
				p.setPosicion(TOTAL_CELLS - 1);
			if (p.getPosicion() < 0)
				p.setPosicion(0);

			// variable que guarda informacion sobre posnueva
			int posNueva = p.getPosicion();
			playerPositions.put(indiceActual, posNueva);

			mensaje = "¡Dado Rápido! Avanzas " + total + " casillas";

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
			// Consumir item
			itemEncontrado.setCantidad(itemEncontrado.getCantidad() - 1);
			if (itemEncontrado.getCantidad() <= 0) {
				inv.eliminarItem(itemEncontrado);
			}

			dado.setDisable(true);

			invocarMetodo(diceSound, "play");
			System.out.println("Sonido de dado lento intentado");
			animarDado();
			// Tirar dado especial lento: 1-3
			java.util.Random r = new java.util.Random();
			// variable que guarda informacion sobre total
			int total = r.nextInt(3) + 1; // 1 a 3 (0-2 + 1)
			// variable que guarda informacion sobre tiro1
			int tiro1 = total; // Para el mensaje
			// variable que guarda informacion sobre tiro2
			int tiro2 = 0;

			if ("admin#67".equals(p.getNom())) {
				total = 1;
			}

			// variable que guarda informacion sobre posanterior
			int posAnterior = p.getPosicion();
			p.moverPosicion(total);
			if (p.getPosicion() >= TOTAL_CELLS)
				p.setPosicion(TOTAL_CELLS - 1);
			if (p.getPosicion() < 0)
				p.setPosicion(0);

			// variable que guarda informacion sobre posnueva
			int posNueva = p.getPosicion();
			playerPositions.put(indiceActual, posNueva);

			mensaje = "¡Dado Lento! Avanzas " + total + " casillas";

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

				agregarEvento(mensaje);
				actualizarInfoJugadores();
			}
		}
		}
	}

	// metodo encargado de la funcion verificarfindejuego recibiendo parametros: Jugador j
	private void verificarFinDeJuego(Jugador j) {
		// Validar si el jugador acaba de ganar
		if (j.getPosicion() >= TOTAL_CELLS - 1) {
			gestorPartida.getPartida().setFinalizado(true);
			gestorPartida.getPartida().setGanador(j);
			// Sumamos el último turno (el de la victoria) antes de guardar
			gestorPartida.getPartida().setTurnos(gestorPartida.getPartida().getTurnos() + 1);
		}

		if (gestorPartida.getPartida().isFinalizado()) {
			// Usamos AppState para acceder a las propiedades de la ventana principal
			Stage vPrincipal = AppState.getInstance().getVentanaPrincipal();
			if (vPrincipal != null && vPrincipal.getProperties().get("ANIMACION_MOSTRADA") == null) {
				vPrincipal.getProperties().put("ANIMACION_MOSTRADA", true);

				// Sumar victoria al nickname del jugador que ha ganado la partida
				Jugador ganador = gestorPartida.getPartida().getGanador();
				if (ganador != null) {
					registrarVictoria(ganador.getNom());
				}

				mostrarAnimacionGanador(gestorPartida.getPartida().getGanador());
			}
		} else {
			gestorPartida.siguienteTurno();
			actualizarInfoJugadores();

			// variable que guarda informacion sobre indiceactual
			int indiceActual = gestorPartida.getPartida().getJugadorActual();
			// variable que guarda informacion sobre nuevoactual
			Jugador nuevoActual = gestorPartida.getPartida().getJugador().get(indiceActual);

			if (nuevoActual.estaCongelado()) {
				nuevoActual.pasaTurnoCongelado();
				agregarEvento("!" + nuevoActual.getNom() + " pierde su turno por estar congelado!");
				dado.setDisable(true);

				// Saltamos al siguiente jugador automáticamente, con pausa visual
				PauseTransition pause = new PauseTransition(Duration.millis(2000));
				pause.setOnFinished(e -> verificarFinDeJuego(nuevoActual));
				pause.play();
			} else if (nuevoActual instanceof Foca) {
				dado.setDisable(true);
				agregarEvento("Turno de la Foca");

				// variable que guarda informacion sobre pause
				PauseTransition pause = new PauseTransition(Duration.millis(1500));
				pause.setOnFinished(e -> ejecutarTurnoFoca((Foca) nuevoActual, indiceActual));
				pause.play();
			} else {
				dado.setDisable(false);
				agregarEvento("Turno de " + nuevoActual.getNom() + " (Casilla " + nuevoActual.getPosicion() + ")");
			}
		}
	}

	// Incrementa el contador de victorias del usuario en la BD
	private void registrarVictoria(String usuario) {
		if (conexionBD == null || usuario == null || usuario.isEmpty()) {
			System.out.println("[Ranking] No hay conexi\u00f3n BD o usuario para registrar victoria.");
			return;
		}
		try {
			// variable que guarda informacion sobre usuariolimpio
			String usuarioLimpio = usuario.trim();

			// Forzamos autoCommit=true para que el UPDATE se confirme inmediatamente,
			// independientemente del estado previo de la transacci\u00f3n
			conexionBD.setAutoCommit(true);

			java.sql.PreparedStatement ps = conexionBD.prepareStatement(
				"UPDATE USUARIO SET VICTORIAS = NVL(VICTORIAS, 0) + 1 WHERE UPPER(NICKNAME) = UPPER(?)");
			ps.setString(1, usuarioLimpio);
			// variable que guarda informacion sobre filas
			int filas = ps.executeUpdate();
			ps.close();

			if (filas == 0) {
				System.out.println("[Ranking] ADVERTENCIA: No se encontr\u00f3 el usuario '" + usuarioLimpio + "' en la BD. Victoria no registrada.");
			} else {
				System.out.println("[Ranking] Victoria registrada para '" + usuarioLimpio + "'. Filas actualizadas: " + filas);
			}
		} catch (Exception e) {
			System.out.println("[Ranking] Error al registrar victoria: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// metodo encargado de la funcion ejecutarturnofoca recibiendo parametros: Foca foca, int indiceActual
	private void ejecutarTurnoFoca(Foca foca, int indiceActual) {
		// variable que guarda informacion sobre d
		Dado d = new Dado();
		// variable que guarda informacion sobre resultado
		int resultado = d.tirar();
		dadoResultText.setText("Saca: " + resultado);
		agregarEvento("La Foca ha sacado un " + resultado + "!");

		// variable que guarda informacion sobre posanterior
		int posAnterior = foca.getPosicion();
		foca.setPosicion(posAnterior + resultado);
		if (foca.getPosicion() >= TOTAL_CELLS)
			foca.setPosicion(TOTAL_CELLS - 1);

		// variable que guarda informacion sobre posnueva
		int posNueva = foca.getPosicion();
		playerPositions.put(indiceActual, posNueva);

		animarMovimiento(indiceActual, posAnterior, posNueva, () -> {
			try {
				aplicarCasilla(foca, posNueva);
				// variable que guarda informacion sobre casilla
				Casilla casilla = gestorPartida.getPartida().getTablero().getCasillas().get(posNueva);
				if (!(casilla instanceof Evento)) {
					verificarColisionFoca(foca);
				}
			} catch (Exception e) {
				System.err.println("Error ejecutando casillas foca: " + e.getMessage());
				e.printStackTrace();
			}

			// variable que guarda informacion sobre pause
			PauseTransition pause = new PauseTransition(Duration.millis(2500));
			pause.setOnFinished(e -> verificarFinDeJuego(foca));
			pause.play();
		});
	}

	// metodo encargado de la funcion verificarcolisionfoca recibiendo parametros: Foca foca
	private void verificarColisionFoca(Foca foca) {
		// variable que guarda informacion sobre colision
		boolean colision = false;
		for (Jugador jug : gestorPartida.getPartida().getJugador()) {
			if (jug instanceof Pinguino && jug.getPosicion() == foca.getPosicion()) {
				// variable que guarda informacion sobre p
				Pinguino p = (Pinguino) jug;
				foca.aplastarJugador(p);

				if (!foca.isSoborno()) {
					agregarEvento("¡La Foca ha aplastado a " + p.getNom() + "!");
					// variable que guarda informacion sobre idx
					int idx = gestorPartida.getPartida().getJugador().indexOf(p);
					if (idx >= 0) {
						// variable que guarda informacion sobre c
						Circle c = playerCircles.get(idx);
						GridPane.setRowIndex(c, 0);
						GridPane.setColumnIndex(c, 0);
						c.setTranslateX(0);
						c.setTranslateY(0);
						c.toFront();
					}
				} else {
					agregarEvento("La Foca iba a aplastar a " + p.getNom() + " pero recordó su soborno.");
					// Consumir el soborno tras usarlo
					foca.setSoborno(false);
				}
				colision = true;
			}
		}
		if (!colision) {
			agregarEvento("La Foca avanza...");
		}
	}

	// metodo encargado de la funcion mostraranimacionganador recibiendo parametros: Jugador ganador
	private void mostrarAnimacionGanador(Jugador ganador) {
		invocarMetodo(backgroundMusicPlayer, "stop");
		invocarMetodo(victoriaSound, "play");

		javafx.stage.Stage principal = AppState.getInstance().getVentanaPrincipal();
		// variable que guarda informacion sobre w
		double w = principal.getWidth();
		// variable que guarda informacion sobre h
		double h = principal.getHeight();

		// ── Stage transparente que ocupa toda la ventana principal ──────────────
		javafx.stage.Stage win = new javafx.stage.Stage();
		win.initStyle(javafx.stage.StageStyle.TRANSPARENT);
		win.initModality(javafx.stage.Modality.APPLICATION_MODAL);
		win.initOwner(principal);

		// ── Raíz ──────────────────────────────────────────────────────────────
		javafx.scene.layout.StackPane raiz = new javafx.scene.layout.StackPane();
		raiz.setStyle("-fx-background-color: transparent;");

		// ── Confeti en toda la pantalla ──────────────────────────────────────
		javafx.scene.layout.Pane confeti = new javafx.scene.layout.Pane();
		confeti.setMouseTransparent(true);
		confeti.setPickOnBounds(false); // Refuerzo para que no bloquee clics
		// variable que guarda informacion sobre paleta
		String[] paleta = { "#FFD700", "#FF6B6B", "#4ECDC4", "#45B7D1", "#A78BFA", "#F9A8D4", "#86EFAC", "#FCD34D" };
		java.util.Random rnd = new java.util.Random();
		for (int i = 0; i < 150; i++) {
			javafx.scene.Node part;
			if (rnd.nextBoolean()) {
				javafx.scene.shape.Rectangle r = new javafx.scene.shape.Rectangle(
						8 + rnd.nextInt(10), 8 + rnd.nextInt(10));
				r.setArcWidth(3);
				r.setArcHeight(3);
				r.setFill(javafx.scene.paint.Color.web(paleta[rnd.nextInt(paleta.length)]));
				part = r;
			} else {
				javafx.scene.shape.Circle c = new javafx.scene.shape.Circle(5 + rnd.nextInt(5));
				c.setFill(javafx.scene.paint.Color.web(paleta[rnd.nextInt(paleta.length)]));
				part = c;
			}
			part.setRotate(rnd.nextDouble() * 360);
			part.setLayoutX(rnd.nextDouble() * w);
			part.setLayoutY(-50 - rnd.nextDouble() * 200);
			part.setOpacity(0.7 + rnd.nextDouble() * 0.3);
			confeti.getChildren().add(part);

			javafx.animation.TranslateTransition fall = new javafx.animation.TranslateTransition(
					Duration.millis(2000 + rnd.nextInt(2500)), part);
			fall.setByY(h + 200);
			fall.setByX((rnd.nextDouble() - 0.5) * 300);
			fall.setDelay(Duration.millis(rnd.nextInt(2000)));
			fall.setCycleCount(javafx.animation.Animation.INDEFINITE);

			javafx.animation.RotateTransition spin = new javafx.animation.RotateTransition(
					Duration.millis(600 + rnd.nextInt(800)), part);
			spin.setByAngle(rnd.nextBoolean() ? 360 : -360);
			spin.setCycleCount(javafx.animation.Animation.INDEFINITE);
			fall.play();
			spin.play();
		}

		// ── Recuadro simple ───────────────────────────────────────────────────
		javafx.scene.layout.VBox card = new javafx.scene.layout.VBox(20);
		card.setAlignment(javafx.geometry.Pos.CENTER);
		card.setMaxWidth(400);
		card.setMaxHeight(200);
		// Recuadro semitransparente oscuro pero simple, sin el cuadrado opaco masivo
		card.setStyle(
				"-fx-background-color: rgba(20, 30, 50, 0.95);" +
						"-fx-background-radius: 15;" +
						"-fx-border-color: #FFD700;" +
						"-fx-border-width: 3;" +
						"-fx-border-radius: 15;" +
						"-fx-padding: 30;");

		javafx.scene.text.Text titulo = new javafx.scene.text.Text("¡TENEMOS GANADOR!");
		titulo.setStyle(
				"-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: #FFD700;" +
						"-fx-font-family: 'Segoe UI Black', 'Arial Black', sans-serif;");

		javafx.scene.text.Text nombreTxt = new javafx.scene.text.Text("El ganador es " + ganador.getNom());
		nombreTxt.setStyle(
				"-fx-font-size: 30px; -fx-font-weight: 900; -fx-fill: white;" +
						"-fx-font-family: 'Segoe UI Black', 'Arial Black', sans-serif;");

		// variable que guarda informacion sobre btn
		Button btn = new Button("Volver al Menú");
		btn.setStyle(
				"-fx-font-size: 16px; -fx-font-weight: bold;" +
						"-fx-background-color: #FFD700;" +
						"-fx-text-fill: black; -fx-background-radius: 10; -fx-padding: 10 20;" +
						"-fx-cursor: hand;");
		btn.setOnAction(e -> {
			win.close();
			volverAlMenuDirecto(); // Vuelve directo al menú (victoria ya registrada)
		});

		card.getChildren().addAll(titulo, nombreTxt, btn);
		raiz.getChildren().addAll(confeti, card);
		card.toFront();

		// ── Escena y posición ────────────────────────────────────────────────
		javafx.scene.Scene sc = new javafx.scene.Scene(raiz, w, h);
		sc.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0.01));
		win.setScene(sc);

		win.setX(principal.getX());
		win.setY(principal.getY());

		win.show();
	}

	// metodo encargado de la funcion iniciarlogicajuego recibiendo parametros: ninguno
	private void iniciarLogicaJuego() {
		// Mapear círculos de jugadores
		ArrayList<Jugador> jugadores = gestorPartida.getPartida().getJugador();
		// variable que guarda informacion sobre circles
		Circle[] circles = { P1, P2, P3, P4, P5 };

		for (int i = 0; i < jugadores.size() && i < 5; i++) {
			if (circles[i] != null) {
				playerCircles.put(i, circles[i]);

				// Cargar imagen de pingüino
				Jugador j = jugadores.get(i);
				String colorBuscado = j.getColor() != null
						? j.getColor().toLowerCase().replace("ú", "u").replace("ó", "o")
						: "azul";

				// variable que guarda informacion sobre colorpath
				String colorPath;
				if (j instanceof Foca) {
					colorPath = "/jocpinguiFinal/Vista/images/foca.png";
				} else if ("nigga".equalsIgnoreCase(j.getNom())) {
					colorPath = "/jocpinguiFinal/Vista/images/nigga.png";
					if (getClass().getResourceAsStream(colorPath) == null) {
						colorPath = "/jocpinguiFinal/Vista/images/nigga.jpg";
					}
				} else {
					if ("negro".equalsIgnoreCase(j.getNom())) {
						colorBuscado = "negro"; // Easter egg
					}
					// variable que guarda informacion sobre basepath
					String basePath = "/jocpinguiFinal/Vista/images/pinguino_" + colorBuscado;
					colorPath = basePath + ".png";
					if (getClass().getResourceAsStream(colorPath) == null) {
						colorPath = basePath + ".jpg";
					}
				}
				try {
					java.io.InputStream is = getClass().getResourceAsStream(colorPath);
					if (is != null) {
						// variable que guarda informacion sobre img
						Image img = new Image(is, 80, 80, true, true);
						circles[i].setFill(new ImagePattern(img));
					} else {
						circles[i].setFill(Color.web(obtenerColorHex(colorBuscado)));
					}
				} catch (Exception e) {
					circles[i].setFill(Color.web(obtenerColorHex(colorBuscado)));
				}

				// Posicionar círculos según la posición actual del jugador
				int posActual = j.getPosicion();
				playerPositions.put(i, posActual);
				// variable que guarda informacion sobre row
				int row = posActual / COLUMNS;
				// variable que guarda informacion sobre col
				int col = posActual % COLUMNS;
				GridPane.setRowIndex(circles[i], row);
				GridPane.setColumnIndex(circles[i], col);
				circles[i].setTranslateX(0);
				circles[i].setTranslateY(0);
				circles[i].setVisible(true);
			}
		}

		// Ocultar círculos no usados
		for (int i = jugadores.size(); i < 5; i++) {
			if (circles[i] != null) {
				circles[i].setVisible(false);
			}
		}

		// Inicializar el tablero visualmente
		mostrarTiposDeCasillasEnTablero(gestorPartida.getPartida().getTablero());

		agregarEvento("¡El juego ha comenzado!");
		actualizarInfoJugadores();
	}
}
