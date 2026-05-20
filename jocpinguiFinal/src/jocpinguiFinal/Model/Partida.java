package jocpinguiFinal.Model;

import java.util.ArrayList;

/**
 * guarda todo el estado de la partida actual: turno, jugadores y configuracion del tablero.
 */
public class Partida implements java.io.Serializable {
	// variable que guarda informacion sobre serialversionuid
	private static final long serialVersionUID = 1L;
	// variable que guarda informacion sobre tablero
	protected Tablero tablero;
	// variable que guarda informacion sobre jugadores
	private ArrayList<Jugador> jugadores;
	// variable que guarda informacion sobre turnos
	protected int turnos;
	// variable que guarda informacion sobre jugadoractual
	protected int jugadorActual;
	// variable que guarda informacion sobre finalizado
	protected boolean finalizado;
	// variable que guarda informacion sobre ganador
	protected Jugador ganador;
	//Constructor de Partida para iniciar la partida
	public Partida(Tablero tablero, ArrayList<Jugador> jugadores) {
		this.tablero = tablero;
		this.jugadores = jugadores;
		this.turnos = 0;
		this.jugadorActual = 0;
		this.finalizado = false;
		this.ganador = null;
	}
	//Este metodo sirve para mostrar el tablero
	public Tablero getTablero() {
		return tablero;
	}
	//Este metodo sirve para actualizar el tablero
	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}
	//Este metodo muestra todos los jugadores del arraylist
	public ArrayList<Jugador> getJugador() {
		return jugadores;
	}
	//Este metodo actualiza el jugador del arraylist
	public void setJugador(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}
	//Este metodo muestra los turnos del jugador
	public int getTurnos() {
		return turnos;
	}
	//Este metodo actualiza los turnos
	public void setTurnos(int turnos) {
		this.turnos = turnos;
	}
	//Este metodo muestra el jugador actual
	public int getJugadorActual() {
		return jugadorActual;
	}
	//ESte metodo actualiza el jugador actual
	public void setJugadorActual(int jugadorActual) {
		this.jugadorActual = jugadorActual;
	}
	//Este metodo observa si ha terminado la partida
	public boolean isFinalizado() {
		return finalizado;
	}
	//Este metodo actualiza la partida y la finaliza
	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}
	//Este metodo muestra el ganador
	public Jugador getGanador() {
		return ganador;
	}
	//Este metodo actualiza el ganador
	public void setGanador(Jugador ganador) {
		this.ganador = ganador;
	}
	
	
}
