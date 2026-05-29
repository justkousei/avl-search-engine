package persistencia;

import java.io.*;
import java.util.HashMap;

import avl.AVL;
import modelo.Registro;

public class JsonManager {

	private String archivo;

	//constructor
	public JsonManager(String nombreTabla) {

		this.archivo = nombreTabla + ".json";
	}

	//guardar registro
	public void guardarRegistro(int clave, Registro r) {

		try {

			BufferedWriter bw = new BufferedWriter(
					new FileWriter(archivo, true)
			);

			String texto = clave + ";";

			for(HashMap.Entry<String, String> dato : r.getDatos().entrySet()) {

				texto += dato.getKey() + "=" + dato.getValue() + ";";
			}

			bw.write(texto);

			bw.newLine();

			bw.close();

		}catch(IOException e) {

			System.out.println("Error guardando archivo.");
		}
	}

	//limpiar archivo
	public void limpiarArchivo() {

		try {

			BufferedWriter bw = new BufferedWriter(
					new FileWriter(archivo)
			);

			bw.write("");

			bw.close();

		}catch(IOException e) {

			System.out.println("Error limpiando archivo.");
		}
	}

	//cargar registros
	public void cargarRegistros(AVL arbol) {

		try {

			BufferedReader br = new BufferedReader(
					new FileReader(archivo)
			);

			String linea;

			while((linea = br.readLine()) != null) {

				String[] partes = linea.split(";");

				int clave = Integer.parseInt(partes[0]);

				Registro r = new Registro();

				for(int i = 1; i < partes.length; i++) {

					String[] dato = partes[i].split("=");

					if(dato.length == 2) {

						r.agregarCampo(dato[0], dato[1]);
					}
				}

				arbol.insertar(clave, r);
			}

			br.close();

		}catch(IOException e) {

			System.out.println("No hay archivo aún.");
		}
	}
}