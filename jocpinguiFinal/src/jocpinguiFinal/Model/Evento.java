package jocpinguiFinal.Model;
import java.util.Random;
public class Evento extends Casilla{
	private String[] eventos;
    // constructor para situar la casilla de evento con su lista de posibles sucesos
    public Evento(int posicion, String[] eventos) {
        super(posicion);
        this.eventos = eventos;
    }
    // devuelve la lista de eventos
    public String[] getEventos() {
        return eventos;
    }
    // actualiza la lista de eventos
    public void setEventos(String[] eventos) {
        this.eventos = eventos;
    }
    // al caer en evento, se elige uno al azar y se aplica su efecto (movimiento, items o congelacion)
    @Override
    public void realizarAccion(Partida partida, Jugador jugador) {
		Random r = new Random();
		int aleatorio = r.nextInt(eventos.length);
		String ev = eventos[aleatorio];
		System.out.println("Evento: " + ev);

		if (ev.contains("+2")) {
			jugador.moverPosicion(2);
		} else if (ev.contains("-2")) {
			jugador.moverPosicion(-2);
		} else if (ev.contains("Pierdes turno")) {
			jugador.congelar(1);
		} else if (ev.contains("objeto")) {
			if (jugador instanceof Pinguino) {
				Pinguino p = (Pinguino) jugador;
				if (!p.getInv().getItems().isEmpty()) {
					Item it = p.getInv().getItems().get(r.nextInt(p.getInv().getItems().size()));
					it.setCantidad(it.getCantidad() - 1);
					if (it.getCantidad() <= 0) p.getInv().eliminarItem(it);
					System.out.println("Has perdido un(a) " + it.getNombre());
				}
			}
		} else if (ev.toLowerCase().contains("pez")) {
			if (jugador instanceof Pinguino) {
				((Pinguino) jugador).getInv().añadirItem(new ItemConcreto("Pez", 1));
			}
		} else if (ev.toLowerCase().contains("nieve")) {
			if (jugador instanceof Pinguino) {
				int cant = r.nextInt(3) + 1;
				((Pinguino) jugador).getInv().añadirItem(new ItemConcreto("Nieve", cant));
			}
		} else if (ev.toLowerCase().contains("rápido")) {
			if (jugador instanceof Pinguino) {
				((Pinguino) jugador).getInv().añadirItem(new ItemConcreto("Dado Rápido", 1));
			}
		} else if (ev.toLowerCase().contains("lento")) {
			if (jugador instanceof Pinguino) {
				((Pinguino) jugador).getInv().añadirItem(new ItemConcreto("Dado Lento", 1));
			}
		} else {
			jugador.moverPosicion(1);
		}
	}
}