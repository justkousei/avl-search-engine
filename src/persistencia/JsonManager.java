package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import avl.AVL;
import modelo.Registro;

/**
 * Guarda y carga los registros de una tabla en formato JSON Lines:
 * un objeto JSON por linea, por ejemplo
 *
 *   {"clave":10,"datos":{"nombre":"Ana","nota":"5"}}
 *
 * Tambien sabe leer el formato antiguo ("10;nombre=Ana;nota=5;") para no
 * perder datos guardados con versiones anteriores; al reescribir la tabla
 * queda convertida a JSON.
 */
public class JsonManager {

	private final String archivo;

	public JsonManager(String nombreTabla) {

		this.archivo = nombreTabla + ".json";
	}

	// ---------------------------------------------------------------- guardar

	/** Agrega un registro al final del archivo. */
	public void guardarRegistro(int clave, Registro r) {

		try(BufferedWriter bw = new BufferedWriter(
				new java.io.OutputStreamWriter(
						new java.io.FileOutputStream(archivo, true),
						StandardCharsets.UTF_8))) {

			bw.write(aJson(clave, r));
			bw.newLine();

		}catch(IOException e) {

			System.out.println("Error guardando archivo: " + e.getMessage());
		}
	}

	/** Deja el archivo vacio. */
	public void limpiarArchivo() {

		try {

			Files.write(new File(archivo).toPath(), new byte[0]);

		}catch(IOException e) {

			System.out.println("Error limpiando archivo: " + e.getMessage());
		}
	}

	/**
	 * Reescribe el archivo completo con el contenido actual del arbol,
	 * abriendolo una sola vez. Escribe primero a un archivo temporal para no
	 * dejar el archivo a medias si algo falla.
	 */
	public void reescribir(AVL arbol) {

		File temporal = new File(archivo + ".tmp");

		try {

			try(BufferedWriter bw = new BufferedWriter(
					new java.io.OutputStreamWriter(
							new java.io.FileOutputStream(temporal),
							StandardCharsets.UTF_8))) {

				arbol.recorrerInorder((clave, registro) -> {

					try {

						bw.write(aJson(clave, registro));
						bw.newLine();

					}catch(IOException e) {

						throw new java.io.UncheckedIOException(e);
					}
				});
			}

			Files.move(temporal.toPath(), new File(archivo).toPath(),
					StandardCopyOption.REPLACE_EXISTING);

		}catch(IOException | java.io.UncheckedIOException e) {

			System.out.println("Error guardando archivo: " + e.getMessage());
		}
	}

	// ----------------------------------------------------------------- cargar

	/** Carga el archivo en el arbol. Las lineas danadas se ignoran con aviso. */
	public void cargarRegistros(AVL arbol) {

		File f = new File(archivo);

		if(!f.exists()) {

			return;
		}

		try(BufferedReader br = new BufferedReader(
				new java.io.InputStreamReader(
						new java.io.FileInputStream(f),
						StandardCharsets.UTF_8))) {

			String linea;
			int numero = 0;

			while((linea = br.readLine()) != null) {

				numero++;

				linea = linea.trim();

				if(linea.isEmpty()) {

					continue;
				}

				try {

					Object[] par = linea.startsWith("{")
							? leerJson(linea)
							: leerFormatoViejo(linea);

					arbol.insertar((Integer) par[0], (Registro) par[1]);

				}catch(RuntimeException e) {

					System.out.println("Aviso: linea " + numero + " de " + archivo
							+ " ignorada (" + e.getMessage() + ").");
				}
			}

		}catch(IOException e) {

			System.out.println("Error leyendo " + archivo + ": " + e.getMessage());
		}
	}

	// ------------------------------------------------------------------- JSON

	private static String aJson(int clave, Registro r) {

		StringBuilder sb = new StringBuilder();

		sb.append("{\"clave\":").append(clave).append(",\"datos\":{");

		boolean primero = true;

		for(Map.Entry<String, String> e : r.getDatos().entrySet()) {

			if(!primero) {

				sb.append(',');
			}

			primero = false;

			sb.append('"').append(escapar(e.getKey())).append("\":\"")
					.append(escapar(e.getValue())).append('"');
		}

		return sb.append("}}").toString();
	}

	private static String escapar(String s) {

		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < s.length(); i++) {

			char c = s.charAt(i);

			switch(c) {

				case '"':  sb.append("\\\""); break;
				case '\\': sb.append("\\\\"); break;
				case '\n': sb.append("\\n");  break;
				case '\r': sb.append("\\r");  break;
				case '\t': sb.append("\\t");  break;
				default:

					if(c < 0x20) {

						sb.append(String.format("\\u%04x", (int) c));

					}else {

						sb.append(c);
					}
			}
		}

		return sb.toString();
	}

	/** Lee {"clave":N,"datos":{"campo":"valor",...}} y devuelve {clave, registro}. */
	private static Object[] leerJson(String linea) {

		Lector l = new Lector(linea);

		Integer clave = null;
		Registro r = new Registro();

		l.esperar('{');

		if(!l.siguiente('}')) {

			do {

				String nombre = l.texto();

				l.esperar(':');

				if(nombre.equals("clave")) {

					clave = l.entero();

				}else if(nombre.equals("datos")) {

					l.esperar('{');

					if(!l.siguiente('}')) {

						do {

							String campo = l.texto();

							l.esperar(':');

							r.agregarCampo(campo, l.texto());

						}while(l.siguiente(','));

						l.esperar('}');
					}

				}else {

					throw new IllegalArgumentException("propiedad desconocida: " + nombre);
				}

			}while(l.siguiente(','));

			l.esperar('}');
		}

		if(clave == null) {

			throw new IllegalArgumentException("falta la clave");
		}

		return new Object[] { clave, r };
	}

	/** Formato antiguo: clave;campo=valor;campo=valor; */
	private static Object[] leerFormatoViejo(String linea) {

		String[] partes = linea.split(";");

		Registro r = new Registro();

		for(int i = 1; i < partes.length; i++) {

			int igual = partes[i].indexOf('=');

			if(igual > 0) {

				r.agregarCampo(partes[i].substring(0, igual), partes[i].substring(igual + 1));
			}
		}

		return new Object[] { Integer.parseInt(partes[0].trim()), r };
	}

	/** Lector minimo para el JSON plano que escribe esta clase. */
	private static class Lector {

		private final String s;
		private int i = 0;

		Lector(String s) {

			this.s = s;
		}

		private void espacios() {

			while(i < s.length() && Character.isWhitespace(s.charAt(i))) {

				i++;
			}
		}

		void esperar(char c) {

			espacios();

			if(i >= s.length() || s.charAt(i) != c) {

				throw new IllegalArgumentException("se esperaba '" + c + "' en la posicion " + i);
			}

			i++;
		}

		boolean siguiente(char c) {

			espacios();

			if(i < s.length() && s.charAt(i) == c) {

				i++;
				return true;
			}

			return false;
		}

		int entero() {

			espacios();

			int inicio = i;

			if(i < s.length() && s.charAt(i) == '-') {

				i++;
			}

			while(i < s.length() && Character.isDigit(s.charAt(i))) {

				i++;
			}

			return Integer.parseInt(s.substring(inicio, i));
		}

		String texto() {

			esperar('"');

			StringBuilder sb = new StringBuilder();

			while(true) {

				if(i >= s.length()) {

					throw new IllegalArgumentException("texto sin cerrar");
				}

				char c = s.charAt(i++);

				if(c == '"') {

					return sb.toString();
				}

				if(c != '\\') {

					sb.append(c);
					continue;
				}

				if(i >= s.length()) {

					throw new IllegalArgumentException("escape incompleto");
				}

				char n = s.charAt(i++);

				switch(n) {

					case 'n': sb.append('\n'); break;
					case 'r': sb.append('\r'); break;
					case 't': sb.append('\t'); break;
					case 'u':

						if(i + 4 > s.length()) {

							throw new IllegalArgumentException("escape \\u incompleto");
						}

						sb.append((char) Integer.parseInt(s.substring(i, i + 4), 16));
						i += 4;
						break;

					default: sb.append(n);
				}
			}
		}
	}
}
