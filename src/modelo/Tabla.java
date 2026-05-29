package modelo;

import java.util.ArrayList;

import avl.AVL;

public class Tabla {

	//nombre tabla
	private String nombre;

	//campos tabla
	private ArrayList<String> campos;

	//arbol
	private AVL arbol;

	//constructor
	public Tabla(
			String nombre,
			ArrayList<String> campos
	) {

		this.nombre = nombre;

		this.campos = campos;

		this.arbol = new AVL();
	}

	//getter nombre
	public String getNombre() {

		return nombre;
	}

	//getter campos
	public ArrayList<String> getCampos() {

		return campos;
	}

	//getter arbol
	public AVL getArbol() {

		return arbol;
	}

	//setter arbol
	public void setArbol(
			AVL arbol
	) {

		this.arbol = arbol;
	}

	//agregar campo
	public void agregarCampo(
			String campo
	) {

		if(!campos.contains(campo)) {

			campos.add(campo);
		}
	}

	//eliminar campo
	public void eliminarCampo(
			String campo
	) {

		campos.remove(campo);
	}
}