package avl;

import gestor.DatabaseManager;
import modelo.Registro;
import modelo.Tabla;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsolaDB {

	private DatabaseManager db;
	private Scanner sc;

	public ConsolaDB() {

		db = new DatabaseManager();

		sc = new Scanner(System.in);

		System.out.println(" MINI GESTOR DB AVL - CONSOLA");

		iniciar();
	}

	private void iniciar() {

		while(true) {

			System.out.print("\n>> ");

			String linea = sc.nextLine();

			if(linea.equalsIgnoreCase("EXIT")) {

				System.out.println("Cerrando gestor...");
				break;
			}

			ejecutarComando(linea);
		}
	}

	private void ejecutarComando(String comando) {

		try {

			String[] partes = comando.split(" ");

			// CREATE tabla campo1 campo2 campo3
			if(partes[0].equalsIgnoreCase("CREATE")) {

				String nombreTabla = partes[1];

				ArrayList<String> campos =
						new ArrayList<>();

				for(int i = 2; i < partes.length; i++) {

					campos.add(partes[i]);
				}

				db.crearTabla(
						nombreTabla,
						campos
				);

				System.out.println("Tabla creada.");
			}

			// DROP tabla
			else if(partes[0].equalsIgnoreCase("DROP")) {

				db.eliminarTabla(partes[1]);

				System.out.println("Tabla eliminada.");
			}

			// SHOW
			else if(partes[0].equalsIgnoreCase("SHOW")) {

				String[] tablas =
						db.obtenerNombresTablas();

				System.out.println("\nTABLAS:");

				for(String t : tablas) {

					System.out.println("- " + t);
				}
			}

			// INSERT tabla clave campo=valor campo=valor
			else if(partes[0].equalsIgnoreCase("INSERT")) {

				String tablaNombre = partes[1];

				int clave =
						Integer.parseInt(partes[2]);

				Tabla tabla =
						db.obtenerTabla(tablaNombre);

				if(tabla == null) {

					System.out.println("Tabla no existe.");
					return;
				}

				Registro r =
						new Registro();

				for(int i = 3; i < partes.length; i++) {

					String[] dato =
							partes[i].split("=");

					if(dato.length == 2) {

						r.agregarCampo(
								dato[0],
								dato[1]
						);
					}
				}

				tabla.getArbol()
						.insertar(clave, r);

				db.obtenerArchivo(tablaNombre)
						.guardarRegistro(clave, r);

				System.out.println("Registro insertado.");
			}

			// FIND tabla clave
			else if(partes[0].equalsIgnoreCase("FIND")) {

				String tablaNombre = partes[1];

				int clave =
						Integer.parseInt(partes[2]);

				Tabla tabla =
						db.obtenerTabla(tablaNombre);

				if(tabla == null) {

					System.out.println("Tabla no existe.");
					return;
				}

				Registro r =
						tabla.getArbol()
								.buscar(clave);

				if(r == null) {

					System.out.println("No encontrado.");

				}else {

					System.out.println("\n" + r);
				}
			}

			// DELETE tabla clave
			else if(partes[0].equalsIgnoreCase("DELETE")) {

				String tablaNombre = partes[1];

				int clave =
						Integer.parseInt(partes[2]);

				Tabla tabla =
						db.obtenerTabla(tablaNombre);

				if(tabla == null) {

					System.out.println("Tabla no existe.");
					return;
				}

				tabla.getArbol()
						.eliminar(clave);

				db.obtenerArchivo(tablaNombre)
						.limpiarArchivo();

				tabla.getArbol()
						.guardarEnArchivo(
								db.obtenerArchivo(tablaNombre)
						);

				System.out.println("Registro eliminado.");
			}

			// UPDATE tabla clave campo=valor
			else if(partes[0].equalsIgnoreCase("UPDATE")) {

				String tablaNombre = partes[1];

				int clave =
						Integer.parseInt(partes[2]);

				Tabla tabla =
						db.obtenerTabla(tablaNombre);

				if(tabla == null) {

					System.out.println("Tabla no existe.");
					return;
				}

				Registro viejo =
						tabla.getArbol()
								.buscar(clave);

				if(viejo == null) {

					System.out.println("Registro no encontrado.");
					return;
				}

				for(int i = 3; i < partes.length; i++) {

					String[] dato =
							partes[i].split("=");

					if(dato.length == 2) {

						viejo.agregarCampo(
								dato[0],
								dato[1]
						);
					}
				}

				tabla.getArbol()
						.actualizar(
								clave,
								viejo
						);

				db.obtenerArchivo(tablaNombre)
						.limpiarArchivo();

				tabla.getArbol()
						.guardarEnArchivo(
								db.obtenerArchivo(tablaNombre)
						);

				System.out.println("Registro actualizado.");
			}

			// INORDER tabla
			else if(partes[0].equalsIgnoreCase("INORDER")) {

				String tablaNombre = partes[1];

				Tabla tabla =
						db.obtenerTabla(tablaNombre);

				if(tabla == null) {

					System.out.println("Tabla no existe.");
					return;
				}

				System.out.println(
						tabla.getArbol()
								.inorderTexto()
				);
			}

			// RANGE tabla min max
			else if(partes[0].equalsIgnoreCase("RANGE")) {

				String tablaNombre = partes[1];

				int min =
						Integer.parseInt(partes[2]);

				int max =
						Integer.parseInt(partes[3]);

				Tabla tabla =
						db.obtenerTabla(tablaNombre);

				if(tabla == null) {

					System.out.println("Tabla no existe.");
					return;
				}

				ArrayList<Registro> lista =
						tabla.getArbol()
								.buscarRango(min, max);

				for(Registro r : lista) {

					System.out.println(r);

					System.out.println("----------------");
				}
			}

			else {

				System.out.println("Comando no válido.");
			}

		}catch(Exception e) {

			System.out.println("Error en comando.");
		}
	}
}