package modelo;

import java.util.HashMap;

public class Registro {

    private HashMap<String, String> datos;

    public Registro() {
        datos = new HashMap<>();
    }

    public void agregarCampo(String campo, String valor) {
        datos.put(campo, valor);
    }

    public String obtenerCampo(String campo) {
        return datos.get(campo);
    }

    public HashMap<String, String> getDatos() {
        return datos;
    }

    @Override
    public String toString() {

        String texto = "";

        for(String clave : datos.keySet()) {
            texto += clave + ": " + datos.get(clave) + "\n";
        }

        return texto;
    }
    
  //eliminar campo
    public void eliminarCampo(
    		String campo
    ) {

    	datos.remove(campo);
    }
}