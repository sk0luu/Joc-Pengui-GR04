package JocPengui;

import java.util.Scanner;
public class JugadorModelo {
	protected int posicion;
	protected String nom;
	protected String color;
	public JugadorModelo(int posicion, String nom, String color) {
		this.posicion = posicion;
		this.nom = nom;
		this.color = color;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void moverPosicion(int p) {
		this.posicion += p;
	}
}
