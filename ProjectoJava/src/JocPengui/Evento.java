package JocPengui;
import java.util.Random;
public class Evento extends Casilla{
	private String[] eventos;
	public Evento(int posicion, String[] eventos) {
		super(posicion);
		this.eventos = eventos;
	}
	
	public String[] getEventos() {
		return eventos;
	}

	public void setEventos(String[] eventos) {
		this.eventos = eventos;
	}

	@Override
	public void realizarAccion(Partida partida, Jugador jugador) {
		Random r = new Random();
		int aleatorio = r.nextInt(eventos.length);
		System.out.println(eventos[aleatorio]);
		jugador.moverPosicion(2);
	}
}
