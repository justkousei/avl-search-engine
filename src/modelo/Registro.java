package modelo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Un registro de una tabla: conjunto de campos (nombre -> valor).
 * Usa LinkedHashMap para conservar el orden en que se agregan los campos.
 */
public class Registro {

	private final Map<String, String> datos = new LinkedHashMap<>();

	public void agregarCampo(String campo, String valor) {

		datos.put(campo, valor == null ? "" : valor);
	}

	public String obtenerCampo(String campo) {

		return datos.get(campo);
	}

	public Map<String, String> getDatos() {

		return datos;
	}

	public void eliminarCampo(String campo) {

		datos.remove(campo);
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		for(Map.Entry<String, String> e : datos.entrySet()) {

			sb.append(e.getKey()).append(": ").append(e.getValue()).append("\n");
		}

		return sb.toString();
	}
}
