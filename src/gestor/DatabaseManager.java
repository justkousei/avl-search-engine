package gestor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import modelo.Tabla;
import persistencia.JsonManager;

public class DatabaseManager {

	private final Map<String, Tabla> tablas = new LinkedHashMap<>();

	private final Map<String, JsonManager> archivos = new LinkedHashMap<>();

	private final String archivoTablas = "tablas.txt";

	//constructor
	public DatabaseManager() {

		cargarTablasGuardadas();
	}

	/**
	 * Nombres validos para tablas y campos: letras, numeros y guion bajo.
	 * Evita nombres que rompan los archivos (";", ",", "/", "..") o que
	 * apunten fuera de la carpeta de trabajo.
	 */
	public static boolean nombreValido(String nombre) {

		return nombre != null && nombre.matches("[\\p{L}\\p{N}_]+");
	}

	//crear tabla: false si el nombre es invalido o la tabla ya existe
	public boolean crearTabla(String nombre, ArrayList<String> campos) {

		if(!nombreValido(nombre) || tablas.containsKey(nombre)) {

			return false;
		}

		Tabla tabla = new Tabla(nombre, campos);

		JsonManager json = new JsonManager(nombre);

		json.cargarRegistros(tabla.getArbol());

		tablas.put(nombre, tabla);

		archivos.put(nombre, json);

		actualizarArchivoTablas();

		return true;
	}

	//eliminar tabla: false si no existia
	public boolean eliminarTabla(String nombre) {

		if(!tablas.containsKey(nombre)) {

			return false;
		}

		tablas.remove(nombre);

		archivos.remove(nombre);

		File archivo = new File(nombre + ".json");

		if(archivo.exists() && !archivo.delete()) {

			System.out.println("No se pudo borrar " + archivo.getName());
		}

		actualizarArchivoTablas();

		return true;
	}

	//obtener tabla
	public Tabla obtenerTabla(String nombre) {

		return tablas.get(nombre);
	}

	//obtener archivo
	public JsonManager obtenerArchivo(String nombre) {

		return archivos.get(nombre);
	}

	//existe tabla
	public boolean existeTabla(String nombre) {

		return tablas.containsKey(nombre);
	}

	//obtener nombres
	public String[] obtenerNombresTablas() {

		return tablas.keySet().toArray(new String[0]);
	}

	//cargar tablas
	private void cargarTablasGuardadas() {

		File f = new File(archivoTablas);

		if(!f.exists()) {

			return;
		}

		try(BufferedReader br = Files.newBufferedReader(f.toPath(), StandardCharsets.UTF_8)) {

			String linea;

			while((linea = br.readLine()) != null) {

				if(linea.trim().isEmpty()) {

					continue;
				}

				String[] partes = linea.split(";");

				String nombre = partes[0];

				ArrayList<String> campos = new ArrayList<>();

				if(partes.length > 1) {

					for(String campo : partes[1].split(",")) {

						if(!campo.isEmpty()) {

							campos.add(campo);
						}
					}
				}

				Tabla tabla = new Tabla(nombre, campos);

				JsonManager json = new JsonManager(nombre);

				json.cargarRegistros(tabla.getArbol());

				tablas.put(nombre, tabla);

				archivos.put(nombre, json);
			}

		}catch(IOException e) {

			System.out.println("No se pudieron leer las tablas guardadas: " + e.getMessage());
		}
	}

	//actualizar txt tablas
	private void actualizarArchivoTablas() {

		try(BufferedWriter bw = Files.newBufferedWriter(
				new File(archivoTablas).toPath(), StandardCharsets.UTF_8)) {

			for(Tabla tabla : tablas.values()) {

				StringBuilder linea = new StringBuilder(tabla.getNombre()).append(';');

				for(String campo : tabla.getCampos()) {

					linea.append(campo).append(',');
				}

				bw.write(linea.toString());

				bw.newLine();
			}

		}catch(IOException e) {

			System.out.println("Error actualizando tablas: " + e.getMessage());
		}
	}

	//agregar campo
	public void agregarCampoTabla(String tablaNombre, String campo) {

		Tabla tabla = tablas.get(tablaNombre);

		if(tabla == null) {

			return;
		}

		tabla.agregarCampo(campo);

		tabla.getArbol().agregarCampoATodos(campo);

		actualizarArchivoTablas();

		tabla.getArbol().guardarEnArchivo(archivos.get(tablaNombre));
	}

	//eliminar campo
	public void eliminarCampoTabla(String tablaNombre, String campo) {

		Tabla tabla = tablas.get(tablaNombre);

		if(tabla == null) {

			return;
		}

		tabla.eliminarCampo(campo);

		tabla.getArbol().eliminarCampoATodos(campo);

		actualizarArchivoTablas();

		tabla.getArbol().guardarEnArchivo(archivos.get(tablaNombre));
	}
}
