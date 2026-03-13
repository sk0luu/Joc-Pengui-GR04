package jocpinguiFinal.Model;
import java.util.Random;
public class Dado {
	Random r = new Random();
	//Creamos el metodo de tirar el dado para que de un numero random entre el 1 y el 6
	public int tirar() {
		int random = r.nextInt(6) + 1;
		System.out.println("Has sacado un " + random + " en el dado");
		return random;
	}
}