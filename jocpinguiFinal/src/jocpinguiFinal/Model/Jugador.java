package jocpinguiFinal.Model;

/**
 * clase base para cualquier entidad que participe (pinguinos, foca).
 */
public abstract class Jugador implements java.io.Serializable {
	// variable que guarda informacion sobre serialversionuid
	private static final long serialVersionUID = 1L;
	// variable que guarda informacion sobre posicion
	protected int posicion;
	// variable que guarda informacion sobre nombre
	protected String nombre;
	// variable que guarda informacion sobre color
	protected String color;
    // variable que guarda informacion sobre puntuacion
    protected int puntuacion;
    // variable que guarda informacion sobre turnoscongelado
    private int turnosCongelado;

    // constructor base para cualquier tipo de jugador (pinguino o foca)
    public Jugador(int posicion, String nom, String color) {
        this.posicion = 0;
        this.nombre = nom;
        this.color = color;
        this.turnosCongelado = 0;
        this.puntuacion = 0;
    }
    // devuelve la posicion actual
    public int getPosicion() {
        return posicion;
    }
    // actualiza la posicion del jugador
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    // devuelve el nombre del jugador
    public String getNom() {
        return nombre;
    }
    // actualiza el nombre del jugador
    public void setNom(String nom) {
        this.nombre = nom;
    }
    // devuelve el color del jugador
    public String getColor() {
        return color;
    }
    // actualiza el color del jugador
    public void setColor(String color) {
        this.color = color;
    }

    // suma pasos a la posicion actual del jugador
    public void moverPosicion(int p) {
        this.posicion += p;
    }

	// metodo que devuelve el valor de turnoscongelado
	public int getTurnosCongelado() {
		return turnosCongelado;
	}

	// metodo que actualiza o establece el valor de turnoscongelado
	public void setTurnosCongelado(int turnosCongelado) {
		this.turnosCongelado = turnosCongelado;
	}

	// metodo encargado de la funcion congelar recibiendo parametros: int turnos
	public void congelar(int turnos) {
		this.turnosCongelado = turnos;
	}

	// metodo encargado de la funcion estacongelado recibiendo parametros: ninguno
	public boolean estaCongelado() {
		return this.turnosCongelado > 0;
	}

	// metodo encargado de la funcion pasaturnocongelado recibiendo parametros: ninguno
	public void pasaTurnoCongelado() {
		if (this.turnosCongelado > 0) {
			this.turnosCongelado--;
		}
	}

	// metodo que devuelve el valor de puntuacion
	public int getPuntuacion() {
		return puntuacion;
	}

	// metodo que actualiza o establece el valor de puntuacion
	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	//Este metodo sobreescribe el jugador y pone el nombre color y posicion del jugador
	@Override
	// metodo encargado de la funcion tostring recibiendo parametros: ninguno
	public String toString() {
		return "Jugador: " + nombre + 
		       " | Color: " + color + 
		       " | Posición: " + posicion +
		       " | Congelado: " + turnosCongelado;
	}
}