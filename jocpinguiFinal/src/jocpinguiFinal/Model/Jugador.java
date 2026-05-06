package jocpinguiFinal.Model;

public abstract class Jugador implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	protected int posicion;
	protected String nombre;
	protected String color;
    protected int puntuacion;
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

	public int getTurnosCongelado() {
		return turnosCongelado;
	}

	public void setTurnosCongelado(int turnosCongelado) {
		this.turnosCongelado = turnosCongelado;
	}

	public void congelar(int turnos) {
		this.turnosCongelado = turnos;
	}

	public boolean estaCongelado() {
		return this.turnosCongelado > 0;
	}

	public void pasaTurnoCongelado() {
		if (this.turnosCongelado > 0) {
			this.turnosCongelado--;
		}
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	//Este metodo sobreescribe el jugador y pone el nombre color y posicion del jugador
	@Override
	public String toString() {
		return "Jugador: " + nombre + 
		       " | Color: " + color + 
		       " | Posición: " + posicion +
		       " | Congelado: " + turnosCongelado;
	}
}