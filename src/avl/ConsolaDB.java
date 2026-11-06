package avl;

import gestor.DatabaseManager;
import modelo.Registro;
import modelo.Tabla;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsolaDB {

	private final DatabaseManager db;
	private final Scanner entrada;

	public ConsolaDB() {

		db = new DatabaseManager();

		entrada = new Scanner(System.in);

		System.out.println(" MINI GESTOR DB AVL - CONSOLA");
		System.out.println(" Escribe HELP para ver los comandos.");

		iniciar();
	}

	private void iniciar() {

		while(true) {

			System.out.print("\n>> ");

			if(!entrada.hasNextLine()) {

				break; // fin de la entrada (Ctrl+Z / Ctrl+D o archivo terminado)
			}

			String linea = entrada.nextLine().trim();

			if(linea.isEmpty()) {

				continue;
			}

			if(linea.equalsIgnoreCase("EXIT")) {

				System.out.println("Cerrando gestor...");
				break;
			}

			ejecutarComando(linea);
		}
	}

	// ------------------------------------------------------------- utilidades

	/** Separa por espacios; respeta comillas dobles: campo="Juan Perez". */
	static List<String> separar(String linea) {

		List<String> partes = new ArrayList<>();

		StringBuilder actual = new StringBuilder();

		boolean entreComillas = false;
		boolean hayToken = false;

		for(char c : linea.toCharArray()) {

			if(c == '"') {

				entreComillas = !entreComillas;
				hayToken = true;

			}else if(Character.isWhitespace(c) && !entreComillas) {

				if(hayToken) {

					partes.add(actual.toString());
					actual.setLength(0);
					hayToken = false;
				}

			}else {

				actual.append(c);
				hayToken = true;
			}
		}

		if(hayToken) {

			partes.add(actual.toString());
		}

		return partes;
	}

	private void requerir(List<String> partes, int minimo, String uso) {

		if(partes.size() < minimo) {

			throw new IllegalArgumentException("Uso: " + uso);
		}
	}

	private int clave(String texto) {

		try {

			return Integer.parseInt(texto);

		}catch(NumberFormatException e) {

			throw new IllegalArgumentException("La clave debe ser un numero entero: '" + texto + "'");
		}
	}

	private Tabla tabla(String nombre) {

		Tabla t = db.obtenerTabla(nombre);

		if(t == null) {

			throw new IllegalArgumentException("La tabla '" + nombre + "' no existe.");
		}

		return t;
	}

	/** Guarda toda la tabla en su archivo. */
	private void guardar(String nombreTabla, Tabla tabla) {

		tabla.getArbol().guardarEnArchivo(db.obtenerArchivo(nombreTabla));
	}

	/** Aplica los pares campo=valor (a partir de 'desde') sobre el registro. */
	private void aplicarCampos(Tabla tabla, Registro r, List<String> partes, int desde) {

		for(int i = desde; i < partes.size(); i++) {

			String token = partes.get(i);

			int igual = token.indexOf('=');

			if(igual <= 0) {

				throw new IllegalArgumentException(
						"Formato invalido '" + token + "'. Use campo=valor (o campo=\"valor con espacios\").");
			}

			String campo = token.substring(0, igual);

			if(!tabla.getCampos().contains(campo)) {

				throw new IllegalArgumentException(
						"El campo '" + campo + "' no existe en la tabla. Campos: " + tabla.getCampos());
			}

			r.agregarCampo(campo, token.substring(igual + 1));
		}
	}

	private void ayuda() {

		System.out.println("\nCOMANDOS:");
		System.out.println("  CREATE <tabla> <campo1> <campo2> ...");
		System.out.println("  DROP <tabla>");
		System.out.println("  SHOW");
		System.out.println("  INSERT <tabla> <clave> campo=valor campo=valor ...");
		System.out.println("  FIND <tabla> <clave>");
		System.out.println("  UPDATE <tabla> <clave> campo=valor ...");
		System.out.println("  DELETE <tabla> <clave>");
		System.out.println("  RANGE <tabla> <min> <max>");
		System.out.println("  INORDER <tabla>");
		System.out.println("  LOAD DATASET <tabla> <ruta>");
		System.out.println("  EXIT");
		System.out.println("\nEn los valores con espacios use comillas: nombre=\"Ana Maria\"");
	}

	// ---------------------------------------------------------------- dataset

	/** Cada linea del archivo: <clave> campo=valor campo=valor ... */
	public void cargarDataset(String nombreTabla, String ruta) {

		Tabla tabla = tabla(nombreTabla);

		File archivo = new File(ruta);

		if(!archivo.isFile()) {

			throw new IllegalArgumentException("No se encontro el archivo: " + ruta);
		}

		int insertados = 0;
		int omitidos = 0;

		try(Scanner lector = new Scanner(archivo, "UTF-8")) {

			int numero = 0;

			while(lector.hasNextLine()) {

				numero++;

				String linea = lector.nextLine().trim();

				if(linea.isEmpty() || linea.startsWith("#")) {

					continue;
				}

				try {

					// "false": se guarda todo junto al final, no registro por registro
					if(insertarRegistro(tabla, separar(linea), false)) {

						insertados++;

					}else {

						omitidos++;
						System.out.println("Linea " + numero + ": clave duplicada, omitida.");
					}

				}catch(IllegalArgumentException e) {

					omitidos++;
					System.out.println("Linea " + numero + ": " + e.getMessage());
				}
			}

		}catch(java.io.FileNotFoundException e) {

			throw new IllegalArgumentException("No se pudo abrir: " + ruta);
		}

		guardar(nombreTabla, tabla);

		System.out.println("Dataset cargado: " + insertados + " insertados, " + omitidos + " omitidos.");
	}

	/** partes = {clave, campo=valor, ...}. Devuelve false si la clave ya existe. */
	private boolean insertarRegistro(Tabla tabla, List<String> partes, boolean guardarAhora) {

		if(partes.isEmpty()) {

			throw new IllegalArgumentException("Falta la clave.");
		}

		int clave = clave(partes.get(0));

		// todos los campos de la tabla existen siempre, vacios por defecto
		Registro r = new Registro();

		for(String campo : tabla.getCampos()) {

			r.agregarCampo(campo, "");
		}

		aplicarCampos(tabla, r, partes, 1);

		if(!tabla.getArbol().insertar(clave, r)) {

			return false;
		}

		if(guardarAhora) {

			db.obtenerArchivo(tabla.getNombre()).guardarRegistro(clave, r);
		}

		return true;
	}

	// --------------------------------------------------------------- comandos

	private void ejecutarComando(String comando) {

		try {

			List<String> p = separar(comando);

			String cmd = p.get(0).toUpperCase();

			switch(cmd) {

				case "HELP":

					ayuda();
					break;

				case "CREATE": {

					requerir(p, 2, "CREATE <tabla> <campo1> <campo2> ...");

					String nombre = p.get(1);

					if(!DatabaseManager.nombreValido(nombre)) {

						throw new IllegalArgumentException(
								"Nombre invalido: use solo letras, numeros y _ .");
					}

					ArrayList<String> campos = new ArrayList<>();

					for(String campo : p.subList(2, p.size())) {

						if(!DatabaseManager.nombreValido(campo)) {

							throw new IllegalArgumentException(
									"Campo invalido '" + campo + "': use solo letras, numeros y _ .");
						}

						if(campos.contains(campo)) {

							throw new IllegalArgumentException("Campo repetido: " + campo);
						}

						campos.add(campo);
					}

					if(db.crearTabla(nombre, campos)) {

						System.out.println("Tabla '" + nombre + "' creada.");

					}else {

						System.out.println("La tabla '" + nombre + "' ya existe.");
					}

					break;
				}

				case "DROP":

					requerir(p, 2, "DROP <tabla>");

					if(db.eliminarTabla(p.get(1))) {

						System.out.println("Tabla eliminada.");

					}else {

						System.out.println("La tabla '" + p.get(1) + "' no existe.");
					}

					break;

				case "SHOW": {

					String[] nombres = db.obtenerNombresTablas();

					if(nombres.length == 0) {

						System.out.println("No hay tablas.");
					}

					for(String t : nombres) {

						System.out.println("- " + t + " " + db.obtenerTabla(t).getCampos());
					}

					break;
				}

				case "INSERT": {

					requerir(p, 3, "INSERT <tabla> <clave> campo=valor ...");

					Tabla t = tabla(p.get(1));

					if(insertarRegistro(t, p.subList(2, p.size()), true)) {

						System.out.println("Registro insertado.");

					}else {

						System.out.println("Ya existe un registro con la clave " + p.get(2) + ".");
					}

					break;
				}

				case "FIND": {

					requerir(p, 3, "FIND <tabla> <clave>");

					Registro r = tabla(p.get(1)).getArbol().buscar(clave(p.get(2)));

					if(r == null) {

						System.out.println("No encontrado.");

					}else {

						System.out.println("\n" + r);
					}

					break;
				}

				case "DELETE": {

					requerir(p, 3, "DELETE <tabla> <clave>");

					Tabla t = tabla(p.get(1));

					if(t.getArbol().eliminar(clave(p.get(2)))) {

						guardar(p.get(1), t);

						System.out.println("Registro eliminado.");

					}else {

						System.out.println("No existe un registro con esa clave.");
					}

					break;
				}

				case "UPDATE": {

					requerir(p, 4, "UPDATE <tabla> <clave> campo=valor ...");

					Tabla t = tabla(p.get(1));

					Registro r = t.getArbol().buscar(clave(p.get(2)));

					if(r == null) {

						System.out.println("Registro no encontrado.");
						break;
					}

					aplicarCampos(t, r, p, 3);

					guardar(p.get(1), t);

					System.out.println("Registro actualizado.");

					break;
				}

				case "INORDER": {

					requerir(p, 2, "INORDER <tabla>");

					System.out.println(tabla(p.get(1)).getArbol().inorderTexto());

					break;
				}

				case "RANGE": {

					requerir(p, 4, "RANGE <tabla> <min> <max>");

					int min = clave(p.get(2));
					int max = clave(p.get(3));

					if(min > max) {

						throw new IllegalArgumentException("El minimo no puede ser mayor que el maximo.");
					}

					ArrayList<Registro> lista = tabla(p.get(1)).getArbol().buscarRango(min, max);

					for(Registro r : lista) {

						System.out.println(r);
						System.out.println("----------------");
					}

					System.out.println(lista.size() + " registro(s) en el rango.");

					break;
				}

				case "LOAD":

					if(p.size() >= 4 && p.get(1).equalsIgnoreCase("DATASET")) {

						cargarDataset(p.get(2), p.get(3));

					}else {

						System.out.println("Uso: LOAD DATASET <tabla> <ruta>");
					}

					break;

				default:

					System.out.println("Comando no valido. Escribe HELP para ver la lista.");
			}

		}catch(IllegalArgumentException e) {

			System.out.println(e.getMessage());

		}catch(RuntimeException e) {

			System.out.println("Error inesperado: " + e);
		}
	}
}
