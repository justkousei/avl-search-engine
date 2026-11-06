package avl;

import java.util.Random;
import java.util.TreeSet;

import modelo.Registro;

/**
 * Prueba de humo del arbol AVL (sin librerias externas).
 * Ejecutar:  java -cp bin avl.PruebaAVL
 * Verifica, despues de cada operacion, que el arbol sigue siendo un ABB valido,
 * que las alturas guardadas son correctas y que el balance esta en [-1, 1].
 */
public class PruebaAVL {

	public static void main(String[] args) {

		Random azar = new Random(42);

		for(int ronda = 0; ronda < 20; ronda++) {

			AVL arbol = new AVL();
			TreeSet<Integer> referencia = new TreeSet<>();

			for(int op = 0; op < 3000; op++) {

				int clave = azar.nextInt(500);

				if(azar.nextInt(100) < 55) {

					boolean nuevo = referencia.add(clave);
					comprobar(arbol.insertar(clave, new Registro()) == nuevo, "insertar " + clave);

				}else {

					boolean existia = referencia.remove(clave);
					comprobar(arbol.eliminar(clave) == existia, "eliminar " + clave);
				}

				verificar(arbol.getRaiz(), referencia.size());
			}
		}

		// peor caso para un ABB normal: claves ordenadas
		AVL arbol = new AVL();
		int n = 100000;

		for(int i = 0; i < n; i++) {

			arbol.insertar(i, new Registro());
		}

		verificar(arbol.getRaiz(), n);

		int h = arbol.getRaiz().getAltura();

		comprobar(h <= 1.45 * Math.log(n + 2) / Math.log(2), "altura demasiado grande: " + h);

		System.out.println("OK: AVL correcto. 100000 claves ordenadas -> altura " + h);
	}

	private static void comprobar(boolean condicion, String mensaje) {

		if(!condicion) {

			throw new AssertionError("FALLO: " + mensaje);
		}
	}

	/** Devuelve la altura real; lanza error si algo esta mal. */
	private static int verificar(Nodo raiz, int esperados) {

		int[] contador = {0};

		int h = revisar(raiz, Integer.MIN_VALUE, Integer.MAX_VALUE, contador);

		comprobar(contador[0] == esperados, "cantidad de nodos " + contador[0] + " != " + esperados);

		return h;
	}

	private static int revisar(Nodo n, long minimo, long maximo, int[] contador) {

		if(n == null) {

			return 0;
		}

		contador[0]++;

		comprobar(n.clave > minimo && n.clave < maximo, "orden ABB roto en " + n.clave);

		int hi = revisar(n.izq, minimo, n.clave, contador);
		int hd = revisar(n.der, n.clave, maximo, contador);

		comprobar(Math.abs(hd - hi) <= 1, "desbalanceado en " + n.clave);
		comprobar(n.altura == 1 + Math.max(hi, hd), "altura guardada incorrecta en " + n.clave);

		return 1 + Math.max(hi, hd);
	}
}
