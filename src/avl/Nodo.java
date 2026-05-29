package avl;

import modelo.Registro;

public class Nodo {

	int clave;
	Registro info;
	int altura;
	Nodo izq, der;

	//coordenadas para dibujar
	public int px, py;

	//constructor
	public Nodo(int clave, Registro info) {

		this.clave = clave;
		this.info = info;
		this.altura = 1;
	}

	//getter clave
	public int getClave() {

		return clave;
	}

	//getter altura
	public int getAltura() {

		return altura;
	}

	//getter izquierda
	public Nodo getIzq() {

		return izq;
	}

	//getter derecha
	public Nodo getDer() {

		return der;
	}

	//getter info
	public Registro getInfo() {

		return info;
	}
}