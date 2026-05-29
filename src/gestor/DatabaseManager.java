package gestor;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;

import modelo.Tabla;
import persistencia.JsonManager;

public class DatabaseManager {

	private HashMap<String, Tabla> tablas;

	private HashMap<String, JsonManager> archivos;

	private final String archivoTablas = "tablas.txt";

	//constructor
	public DatabaseManager() {

		tablas = new HashMap<>();

		archivos = new HashMap<>();

		cargarTablasGuardadas();
	}

	//crear tabla
	public void crearTabla(
			String nombre,
			ArrayList<String> campos
	) {

		if(!tablas.containsKey(nombre)) {

			Tabla tabla =
					new Tabla(
							nombre,
							campos
					);

			JsonManager json =
					new JsonManager(nombre);

			json.cargarRegistros(
					tabla.getArbol()
			);

			tablas.put(
					nombre,
					tabla
			);

			archivos.put(
					nombre,
					json
			);

			actualizarArchivoTablas();

			System.out.println(
					"Tabla creada."
			);

		}else {

			System.out.println(
					"La tabla ya existe."
			);
		}
	}

	//eliminar tabla
	public void eliminarTabla(
			String nombre
	) {

		if(tablas.containsKey(nombre)) {

			tablas.remove(nombre);

			archivos.remove(nombre);

			File archivo =
					new File(
							nombre + ".json"
					);

			if(archivo.exists()) {

				archivo.delete();
			}

			actualizarArchivoTablas();

			System.out.println(
					"Tabla eliminada."
			);
		}
	}

	//obtener tabla
	public Tabla obtenerTabla(
			String nombre
	) {

		return tablas.get(nombre);
	}

	//obtener archivo
	public JsonManager obtenerArchivo(
			String nombre
	) {

		return archivos.get(nombre);
	}

	//existe tabla
	public boolean existeTabla(
			String nombre
	) {

		return tablas.containsKey(nombre);
	}

	//obtener nombres
	public String[] obtenerNombresTablas() {

		return tablas.keySet().toArray(
				new String[0]
		);
	}

	//cargar tablas
	private void cargarTablasGuardadas() {

		try {

			BufferedReader br =
					new BufferedReader(
							new FileReader(
									archivoTablas
							)
					);

			String linea;

			while(
					(linea = br.readLine())
							!= null
			) {

				String[] partes =
						linea.split(";");

				String nombre =
						partes[0];

				ArrayList<String> campos =
						new ArrayList<>();

				if(partes.length > 1) {

					String[] listaCampos =
							partes[1]
									.split(",");

					for(String campo : listaCampos) {

						if(!campo.isEmpty()) {

							campos.add(campo);
						}
					}
				}

				Tabla tabla =
						new Tabla(
								nombre,
								campos
						);

				JsonManager json =
						new JsonManager(nombre);

				json.cargarRegistros(
						tabla.getArbol()
				);

				tablas.put(
						nombre,
						tabla
				);

				archivos.put(
						nombre,
						json
				);
			}

			br.close();

		}catch(IOException e) {

			System.out.println(
					"No hay tablas guardadas."
			);
		}
	}

	//actualizar txt tablas
	private void actualizarArchivoTablas() {

		try {

			BufferedWriter bw =
					new BufferedWriter(
							new FileWriter(
									archivoTablas
							)
					);

			for(Tabla tabla : tablas.values()) {

				String linea =
						tabla.getNombre()
						+ ";";

				for(String campo : tabla.getCampos()) {

					linea += campo + ",";
				}

				bw.write(linea);

				bw.newLine();
			}

			bw.close();

		}catch(IOException e) {

			System.out.println(
					"Error actualizando tablas."
			);
		}
	}

	//agregar campo
	public void agregarCampoTabla(
			String tablaNombre,
			String campo
	) {

		Tabla tabla =
				tablas.get(tablaNombre);

		if(tabla == null) {

			return;
		}

		tabla.agregarCampo(campo);

		tabla.getArbol()
				.agregarCampoATodos(campo);

		actualizarArchivoTablas();

		archivos.get(tablaNombre)
				.limpiarArchivo();

		tabla.getArbol()
				.guardarEnArchivo(
						archivos.get(tablaNombre)
				);
	}

	//eliminar campo
	public void eliminarCampoTabla(
			String tablaNombre,
			String campo
	) {

		Tabla tabla =
				tablas.get(tablaNombre);

		if(tabla == null) {

			return;
		}

		tabla.eliminarCampo(campo);

		tabla.getArbol()
				.eliminarCampoATodos(campo);

		actualizarArchivoTablas();

		archivos.get(tablaNombre)
				.limpiarArchivo();

		tabla.getArbol()
				.guardarEnArchivo(
						archivos.get(tablaNombre)
				);
	}
}