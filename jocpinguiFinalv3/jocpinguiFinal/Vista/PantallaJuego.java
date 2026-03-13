package jocpinguiFinal.Vista;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import jocpinguiFinal.Controlador.GestorPartida;
import jocpinguiFinal.Model.Casilla;
import jocpinguiFinal.Model.Dado;
import jocpinguiFinal.Model.Inventario;
import jocpinguiFinal.Model.Jugador;
import jocpinguiFinal.Model.Pinguino;
import jocpinguiFinal.Model.PinguinoJugador;
import jocpinguiFinal.Model.Tablero;

public class PantallaJuego {

	// Menu items
	@FXML
	private MenuItem newGame;
	@FXML
	private MenuItem saveGame;
	@FXML
	private MenuItem loadGame;
	@FXML
	private MenuItem quitGame;

	// Buttons
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

	// Texts
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

	// Game board and player pieces
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

	private GestorPartida gestorPartida;
	// ONLY FOR TESTING!!!
	private int p1Position = 0; // Tracks current position (from 0 to 49 in a 5x10 grid)
	private static final int COLUMNS = 5;

	private static final String TAG_CASILLA_TEXT = "CASILLA_TEXT";
	private final Random rand = new Random();

	@FXML
	private void initialize() {
	    eventos.setText("¡El juego ha comenzado!");
	    gestorPartida = new GestorPartida();
	    
	    ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
	    Inventario inventario = new Inventario();
	    
	    // CORRECCIÓN: Tu clase Dado no recibe parámetros en el constructor
	    Dado dadoNormal = new Dado(); 
	    
	    // IMPORTANTE: Dado no es un "Item", así que no lo metas en el inventario.
	    // El inventario de tu juego guarda Items (Pez, Escudo, etc.)
	    
	    // Usamos PinguinoJugador porque Pinguino es abstracta
	    PinguinoJugador p1 = new PinguinoJugador("Jugador1", "Azul", 0);
	    p1.setInv(inventario);
	    jugadores.add(p1);

	    // GestorPartida espera una lista de nombres
	    ArrayList<String> nombres = new ArrayList<>();
	    nombres.add("Jugador1");
	    gestorPartida.nuevaPartida(nombres);
	    
	    // Ajuste según los nombres de tus métodos en Partida.java
	    gestorPartida.getPartida().setJugador(jugadores);

	    mostrarTiposDeCasillasEnTablero(gestorPartida.getPartida().getTablero());
	}

	private void mostrarTiposDeCasillasEnTablero(Tablero t) {
		// Clear only the labels we generated in previous calls
		tablero.getChildren().removeIf(node -> TAG_CASILLA_TEXT.equals(node.getUserData()));

		for (int i = 0; i < t.getCasillas().size(); i++) {
			Casilla casilla = t.getCasillas().get(i);

			// Skip position 0 and 49 if you want them to be special (start/end)
			if (i > 0 && i < 49) {
			String tipo = casilla.getClass().getSimpleName();

			Text texto = new Text(tipo);
			texto.setUserData(TAG_CASILLA_TEXT);
			texto.getStyleClass().add("cell-type");

			int row = i / COLUMNS;
			int col = i % COLUMNS;

			GridPane.setRowIndex(texto, row);
			GridPane.setColumnIndex(texto, col);

			tablero.getChildren().add(texto);
			}
		}
	}

	// Menu actions
	@FXML
	private void handleNewGame() {
		System.out.println("New game.");
		// TODO
	}

	@FXML
	private void handleSaveGame() {
		System.out.println("Saved game.");
		// TODO
	}

	@FXML
	private void handleLoadGame() {
		System.out.println("Loaded game.");
		// TODO
	}

	@FXML
	private void handleQuitGame() {
		System.out.println("Exit...");
		// TODO
	}

	// Button actions
	@FXML
	private void handleDado(ActionEvent event) {
	    // getJugador() es el nombre en tu clase Partida
	    Pinguino pingu = (Pinguino) gestorPartida.getPartida().getJugador().get(0);
	    
	    // Creamos el dado sin parámetros
	    Dado d = new Dado();
	    
	    System.out.println("Pos pingu previa:" + pingu.getPosicion());
	    
	    // Llamamos al gestor para que gestione la tirada
	    int resultado = gestorPartida.tiraDado((Jugador) pingu, d);
	    
	    System.out.println("Pos pingu actual:" + pingu.getPosicion());

	    dadoResultText.setText("Ha salido: " + resultado);

	    moveP1(resultado);
	}

	
/*	Old simple version
 * private void moveP1(int steps) {
		p1Position += steps;

		// Bound player
		if (p1Position >= 50) {
			p1Position = 49; // 5 columns * 10 rows = 50 cells (index 0 to 49)
		}
		
		if (p1Position < 0) {
			p1Position = 0;
		}

		// Check row and column
		int row = p1Position / COLUMNS;
		int col = p1Position % COLUMNS;

		// Change P1 property to match row and column
		GridPane.setRowIndex(P1, row);
		GridPane.setColumnIndex(P1, col);
	}*/
	
	private void moveP1(int steps) {

	    // Evita spam del botón
	    dado.setDisable(true);

	    int oldPosition = p1Position;

	    p1Position += steps;

	    // Bound player
	    if (p1Position >= 50) {
	        p1Position = 49;
	    }

	    if (p1Position < 0) {
	        p1Position = 0;
	    }

	    // OLD position
	    int oldRow = oldPosition / COLUMNS;
	    int oldCol = oldPosition % COLUMNS;

	    // NEW position
	    int newRow = p1Position / COLUMNS;
	    int newCol = p1Position % COLUMNS;

	    // Cell size (aproximado)
	    double cellWidth = tablero.getWidth() / COLUMNS;
	    double cellHeight = tablero.getHeight() / 10;

	    double dx = (newCol - oldCol) * cellWidth;
	    double dy = (newRow - oldRow) * cellHeight;

	    TranslateTransition slide = new TranslateTransition(Duration.millis(350), P1);

	    slide.setByX(dx);
	    slide.setByY(dy);

	    slide.setOnFinished(e -> {

	        // reset translation
	        P1.setTranslateX(0);
	        P1.setTranslateY(0);

	        // set real position in grid
	        GridPane.setRowIndex(P1, newRow);
	        GridPane.setColumnIndex(P1, newCol);

	        // volver a activar el botón
	        dado.setDisable(false);
	    });

	    slide.play();
	}

	@FXML
	private void handleRapido() {
		System.out.println("Fast.");
		// TODO
	}

	@FXML
	private void handleLento() {
		System.out.println("Slow.");
		// TODO
	}

	@FXML
	private void handlePeces() {
		System.out.println("Fish.");
		// TODO
	}

	@FXML
	private void handleNieve() {
		System.out.println("Snow.");
		// TODO
	}

	public void setGestorPartida(GestorPartida gestorPartida) {
		this.gestorPartida = gestorPartida;
	}
}
