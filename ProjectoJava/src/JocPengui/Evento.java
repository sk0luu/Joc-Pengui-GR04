package JocPengui;
import java.util.Random;
public class Evento extends Casilla{
	private String[] eventos;
	//Creamos el constructor de evento y agarramos de la clase padre la posicion
	public Evento(int posicion, String[] eventos) {
		super(posicion);
		this.eventos = eventos;
	}
	//Este metodo devuelve los eventos
	public String[] getEventos() {
		return eventos;
	}
	//Este metodo actualiza los eventos
	public void setEventos(String[] eventos) {
		this.eventos = eventos;
	}
	//Este metodo realiza la accion del evento y sobreescribe en la clase padre de casilla
	@Override
	public void realizarAccion(Partida partida, Jugador jugador) {
		Random r = new Random();
		int aleatorio = r.nextInt(eventos.length);
		System.out.println(eventos[aleatorio]);
		jugador.moverPosicion(2);
	}
}