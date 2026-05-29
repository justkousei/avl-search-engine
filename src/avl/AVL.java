package avl;

import java.util.ArrayList;

import modelo.Registro;
import persistencia.JsonManager;

public class AVL {

	private Nodo raiz;

	//altura
	private int altura(Nodo n) {

		if(n == null) {
			return 0;
		}else {
			return n.altura;
		}
	}

	//balanceo
	private int paraBalancear(Nodo n) {

		if(n == null) {
			return 0;
		}else {
			return altura(n.der) - altura(n.izq);
		}
	}

	//rotar derecha
	private Nodo rotarDer(Nodo y) {

		Nodo x = y.izq;
		Nodo t2 = x.der;

		x.der = y;
		y.izq = t2;

		y.altura = 1 + Math.max(altura(y.izq), altura(y.der));
		x.altura = 1 + Math.max(altura(x.izq), altura(x.der));

		return x;
	}

	//rotar izquierda
	private Nodo rotarIzq(Nodo x) {

		Nodo y = x.der;
		Nodo t2 = y.izq;

		y.izq = x;
		x.der = t2;

		x.altura = 1 + Math.max(altura(x.izq), altura(x.der));
		y.altura = 1 + Math.max(altura(y.izq), altura(y.der));

		return y;
	}

	//insertar general
	public void insertar(int clave, Registro info) {

		raiz = insertarRecursivo(raiz, clave, info);
	}

	//insertar recursivo
	private Nodo insertarRecursivo(Nodo nodo, int clave, Registro info) {

		//crear nodo
		if(nodo == null) {

			return new Nodo(clave, info);
		}

		//insertar izquierda
		if(clave < nodo.clave) {

			nodo.izq = insertarRecursivo(nodo.izq, clave, info);

		}else if(clave > nodo.clave) {

			//insertar derecha
			nodo.der = insertarRecursivo(nodo.der, clave, info);

		}else {

			//no permitir duplicados
			return nodo;
		}

		//actualizar altura
		nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));

		int balance = paraBalancear(nodo);

		//caso derecha derecha
		if(balance > 1 && clave > nodo.der.clave) {

			return rotarIzq(nodo);
		}

		//caso izquierda izquierda
		if(balance < -1 && clave < nodo.izq.clave) {

			return rotarDer(nodo);
		}

		//caso derecha izquierda
		if(balance > 1 && clave < nodo.der.clave) {

			nodo.der = rotarDer(nodo.der);

			return rotarIzq(nodo);
		}

		//caso izquierda derecha
		if(balance < -1 && clave > nodo.izq.clave) {

			nodo.izq = rotarIzq(nodo.izq);

			return rotarDer(nodo);
		}

		return nodo;
	}

	//buscar
	public Registro buscar(int clave) {

		return buscarRecursivo(raiz, clave);
	}

	//buscar recursivo
	private Registro buscarRecursivo(Nodo nodo, int clave) {

		if(nodo == null) {

			return null;
		}

		if(clave == nodo.clave) {

			return nodo.info;
		}

		if(clave < nodo.clave) {

			return buscarRecursivo(nodo.izq, clave);

		}else {

			return buscarRecursivo(nodo.der, clave);
		}
	}

	//buscar rango
	public ArrayList<Registro> buscarRango(
			int minimo,
			int maximo
	) {

		ArrayList<Registro> lista =
				new ArrayList<>();

		buscarRangoRecursivo(
				raiz,
				minimo,
				maximo,
				lista
		);

		return lista;
	}
	
	//obtener inorder texto
	public String inorderTexto() {

		StringBuilder sb =
				new StringBuilder();

		inorderRecursivo(
				raiz,
				sb
		);

		return sb.toString();
	}

	//inorder recursivo
	private void inorderRecursivo(
			Nodo nodo,
			StringBuilder sb
	) {

		if(nodo == null) {

			return;
		}

		inorderRecursivo(
				nodo.izq,
				sb
		);

		sb.append(
				"CLAVE: "
				+ nodo.clave
				+ "\n"
		);

		sb.append(
				nodo.info.toString()
		);

		sb.append(
				"\n------------------\n"
		);

		inorderRecursivo(
				nodo.der,
				sb
		);
	}

	//buscar rango recursivo
	private void buscarRangoRecursivo(
			Nodo nodo,
			int minimo,
			int maximo,
			ArrayList<Registro> lista
	) {

		if(nodo == null) {

			return;
		}

		//izquierda
		if(minimo < nodo.clave) {

			buscarRangoRecursivo(
					nodo.izq,
					minimo,
					maximo,
					lista
			);
		}

		//guardar
		if(nodo.clave >= minimo &&
				nodo.clave <= maximo) {

			lista.add(
					nodo.info
			);
		}

		//derecha
		if(maximo > nodo.clave) {

			buscarRangoRecursivo(
					nodo.der,
					minimo,
					maximo,
					lista
			);
		}
	}

	//actualizar
	public boolean actualizar(int clave, Registro nuevaInfo) {

		Nodo encontrado = buscarNodo(raiz, clave);

		//si existe
		if(encontrado != null) {

			encontrado.info = nuevaInfo;

			return true;
		}

		return false;
	}

	//buscar nodo
	private Nodo buscarNodo(Nodo nodo, int clave) {

		if(nodo == null) {

			return null;
		}

		if(clave == nodo.clave) {

			return nodo;
		}

		if(clave < nodo.clave) {

			return buscarNodo(nodo.izq, clave);

		}else {

			return buscarNodo(nodo.der, clave);
		}
	}

	//eliminar
	public void eliminar(int clave) {

		raiz = eliminarRecursivo(raiz, clave);
	}

	//eliminar recursivo
	public Nodo eliminarRecursivo(Nodo nodo, int clave) {

		if(nodo == null) {

			return null;
		}

		//buscar izquierda
		if(clave < nodo.clave) {

			nodo.izq = eliminarRecursivo(nodo.izq, clave);

		}else if(clave > nodo.clave) {

			//buscar derecha
			nodo.der = eliminarRecursivo(nodo.der, clave);

		}else {

			//1 hijo o ninguno
			if(nodo.izq == null || nodo.der == null) {

				Nodo temp = (nodo.izq != null) ? nodo.izq : nodo.der;

				if(temp == null) {

					nodo = null;

				}else {

					nodo = temp;
				}

			}else {

				//2 hijos
				Nodo sucesor = ultimoValor(nodo.der);

				nodo.clave = sucesor.clave;
				nodo.info = sucesor.info;

				nodo.der = eliminarRecursivo(nodo.der, sucesor.clave);
			}
		}

		if(nodo == null) {

			return null;
		}

		//actualizar altura
		nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));

		int balance = paraBalancear(nodo);

		//rebalancear derecha
		if(balance > 1) {

			if(paraBalancear(nodo.der) >= 0) {

				return rotarIzq(nodo);

			}else {

				nodo.der = rotarDer(nodo.der);

				return rotarIzq(nodo);
			}
		}

		//rebalancear izquierda
		if(balance < -1) {

			if(paraBalancear(nodo.izq) <= 0) {

				return rotarDer(nodo);

			}else {

				nodo.izq = rotarIzq(nodo.izq);

				return rotarDer(nodo);
			}
		}

		return nodo;
	}

	//ultimo valor
	private Nodo ultimoValor(Nodo nodo) {

		Nodo actual = nodo;

		while(actual.izq != null) {

			actual = actual.izq;
		}

		return actual;
	}

	//guardar archivo
	public void guardarEnArchivo(JsonManager archivos) {

		recorrerYGuardar(raiz, archivos);
	}

	//recorrido inorder
	private void recorrerYGuardar(Nodo nodo, JsonManager archivos) {

		if(nodo != null) {

			recorrerYGuardar(nodo.izq, archivos);

			archivos.guardarRegistro(nodo.clave, nodo.info);

			recorrerYGuardar(nodo.der, archivos);
		}
	}

	//getter raiz
	public Nodo getRaiz() {

		return raiz;
	}

	//agregar campo
	public void agregarCampoATodos(
			String campo
	) {

		agregarCampoRecursivo(
				raiz,
				campo
		);
	}

	//recursivo agregar
	private void agregarCampoRecursivo(
			Nodo nodo,
			String campo
	) {

		if(nodo == null) {

			return;
		}

		nodo.info.agregarCampo(
				campo,
				""
		);

		agregarCampoRecursivo(
				nodo.izq,
				campo
		);

		agregarCampoRecursivo(
				nodo.der,
				campo
		);
	}

	//eliminar campo todos
	public void eliminarCampoATodos(
			String campo
	) {

		eliminarCampoRecursivo(
				raiz,
				campo
		);
	}

	//recursivo eliminar
	private void eliminarCampoRecursivo(
			Nodo nodo,
			String campo
	) {

		if(nodo == null) {

			return;
		}

		nodo.info.eliminarCampo(
				campo
		);

		eliminarCampoRecursivo(
				nodo.izq,
				campo
		);

		eliminarCampoRecursivo(
				nodo.der,
				campo
		);
	}
}