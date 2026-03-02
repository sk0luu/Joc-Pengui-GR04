package JocPengui;

import java.util.ArrayList;

public class Partida {
	protected Tablero tablero;
	private ArrayList<Jugador> jugadores;
	protected int turnos;
	protected int jugadorActual;
	protected boolean finalizado;
	protected Jugador ganador;
	
	public Partida(Tablero tablero, ArrayList<Jugador> jugadores) {
		this.tablero = tablero;
		this.jugadores = jugadores;
		this.turnos = 0;
		this.jugadorActual = 0;
		this.finalizado = false;
		this.ganador = null;
	}

	public Tablero getTablero() {
		return tablero;
	}

	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	public ArrayList<Jugador> getJugador() {
		return jugadores;
	}

	public void setJugador(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	public int getTurnos() {
		return turnos;
	}

	public void setTurnos(int turnos) {
		this.turnos = turnos;
	}

	public int getJugadorActual() {
		return jugadorActual;
	}

	public void setJugadorActual(int jugadorActual) {
		this.jugadorActual = jugadorActual;
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	public Jugador getGanador() {
		return ganador;
	}

	public void setGanador(Jugador ganador) {
		this.ganador = ganador;
	}
	
}
