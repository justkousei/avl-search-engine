package vista;

import gestor.DatabaseManager;

import javax.swing.*;
import java.awt.*;

public class VentanaDB {

	//ventana
	JFrame ventana =
			new JFrame(
					"Mini Gestor BD AVL"
			);

	//gestor
	DatabaseManager db =
			new DatabaseManager();

	//panel arbol
	panelArbol panelArbol =
			new panelArbol();

	//panel lateral
	JPanel panel =
			new JPanel(null);

	//botones
	JButton btnSeleccionar =
			new JButton(
					"SELECCIONAR TABLA"
			);

	JButton btnArboles =
			new JButton(
					"GESTIONAR ÁRBOLES"
			);

	JButton btnRegistros =
			new JButton(
					"GESTIONAR REGISTROS"
			);

	JButton btnMostrar =
			new JButton(
					"MOSTRAR ÁRBOL"
			);

	//guardar tabla actual
	String tablaActual = "";

	//constructor
	public VentanaDB() {

		ventana.setSize(
				1200,
				650
		);

		ventana.setDefaultCloseOperation(
				JFrame.EXIT_ON_CLOSE
		);

		ventana.setLayout(
				new BorderLayout()
		);

		ventana.setLocationRelativeTo(
				null
		);

		panel.setPreferredSize(
				new Dimension(
						350,
						650
				)
		);

		//boton seleccionar
		btnSeleccionar.setBounds(
				40,
				60,
				250,
				45
		);

		panel.add(
				btnSeleccionar
		);

		//boton arboles
		btnArboles.setBounds(
				40,
				150,
				250,
				45
		);

		panel.add(
				btnArboles
		);

		//boton registros
		btnRegistros.setBounds(
				40,
				240,
				250,
				45
		);

		panel.add(
				btnRegistros
		);

		//boton mostrar
		btnMostrar.setBounds(
				40,
				330,
				250,
				45
		);

		panel.add(
				btnMostrar
		);

		//agregar paneles
		ventana.add(
				panel,
				BorderLayout.WEST
		);
		//Ventana scroleable
		JScrollPane scrollArbol = new JScrollPane(panelArbol);

		scrollArbol.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollArbol.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		ventana.add(scrollArbol, BorderLayout.CENTER);

		// SELECCIONAR TABLA
		btnSeleccionar.addActionListener(e -> {

			String[] tablas =
					db.obtenerNombresTablas();

			if(tablas.length == 0) {

				JOptionPane.showMessageDialog(
						ventana,
						"No hay tablas creadas."
				);

				return;
			}

			String seleccion =
					(String)
					JOptionPane.showInputDialog(
							ventana,
							"Seleccione tabla:",
							"Tablas",
							JOptionPane.PLAIN_MESSAGE,
							null,
							tablas,
							tablas[0]
					);

			if(seleccion != null) {

				tablaActual =
						seleccion;

				panelArbol.setRaiz(
						db.obtenerTabla(
								seleccion
						)
						.getArbol()
						.getRaiz()
				);

				ventana.setTitle(
						"Mini Gestor BD AVL - "
						+ seleccion
				);
			}
		});

		// GESTIONAR ÁRBOLES

		btnArboles.addActionListener(e -> {

			String[] opciones = {
					"Crear Árbol",
					"Eliminar Árbol",
					"Agregar Campo",
					"Eliminar Campo"
			};

			int opcion =
					JOptionPane.showOptionDialog(
							ventana,
							"Seleccione opción",
							"Árboles",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE,
							null,
							opciones,
							opciones[0]
					);

			//crear
			if(opcion == 0) {

				new VentanaCrearTabla(
						ventana,
						db
				);
			}

			//eliminar
			if(opcion == 1) {

				String tabla =
						JOptionPane.showInputDialog(
								"Nombre tabla:"
						);

				if(tabla == null) {

					return;
				}

				db.eliminarTabla(
						tabla
				);

				if(tabla.equals(
						tablaActual
				)) {

					tablaActual = "";

					panelArbol.setRaiz(
							null
					);

					ventana.setTitle(
							"Mini Gestor BD AVL"
					);
				}

				JOptionPane.showMessageDialog(
						ventana,
						"Tabla eliminada."
				);
			}

			//agregar campo
			if(opcion == 2) {

				if(tablaActual.isEmpty()) {

					JOptionPane.showMessageDialog(
							ventana,
							"Seleccione una tabla."
					);

					return;
				}

				String campo =
						JOptionPane.showInputDialog(
								"Nuevo campo:"
						);

				if(campo == null ||
						campo.isEmpty()) {

					return;
				}

				String tipo =
						JOptionPane.showInputDialog(
								"Tipo:\n(entero/texto/real/boolean)"
						);

				if(tipo == null ||
						tipo.isEmpty()) {

					return;
				}

				db.agregarCampoTabla(
						tablaActual,
						campo
				);

				JOptionPane.showMessageDialog(
						ventana,
						"Campo agregado."
				);
			}

			//eliminar campo
			if(opcion == 3) {

				if(tablaActual.isEmpty()) {

					JOptionPane.showMessageDialog(
							ventana,
							"Seleccione una tabla."
					);

					return;
				}

				String campo =
						JOptionPane.showInputDialog(
								"Campo a eliminar:"
						);

				if(campo == null ||
						campo.isEmpty()) {

					return;
				}

				db.eliminarCampoTabla(
						tablaActual,
						campo
				);

				JOptionPane.showMessageDialog(
						ventana,
						"Campo eliminado."
				);
			}
		});

		// GESTIONAR REGISTROS
	
		btnRegistros.addActionListener(e -> {

			if(tablaActual.isEmpty()) {

				JOptionPane.showMessageDialog(
						ventana,
						"Seleccione una tabla."
				);

				return;
			}

			String[] opciones = {
					"Insertar",
					"Buscar",
					"Actualizar",
					"Eliminar",
					"Buscar Rango",
					"Recorrido Inorder"
			};

			int opcion =
					JOptionPane.showOptionDialog(
							ventana,
							"Seleccione opción",
							"Registros",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE,
							null,
							opciones,
							opciones[0]
					);

			//insertar
			if(opcion == 0) {

				new VentanaInsertarRegistro(
						ventana,
						db,
						tablaActual,
						panelArbol
				);
			}

			//buscar
			if(opcion == 1) {

				new VentanaBuscarRegistro(
						ventana,
						db,
						tablaActual,
						panelArbol
				);
			}

			//actualizar
			if(opcion == 2) {

				new VentanaActualizarRegistro(
						ventana,
						db,
						tablaActual,
						panelArbol
				);
			}

			//eliminar
			if(opcion == 3) {

				new VentanaEliminarRegistro(
						ventana,
						db,
						tablaActual,
						panelArbol
				);
			}

			//buscar rango
			if(opcion == 4) {

				new VentanaBuscarRango(
						ventana,
						db,
						tablaActual,
						panelArbol
				);
			}

			//inorder
			if(opcion == 5) {

				String texto =
						db.obtenerTabla(
								tablaActual
						)
						.getArbol()
						.inorderTexto();

				JTextArea area =
						new JTextArea(
								texto
						);

				area.setEditable(false);

				JOptionPane.showMessageDialog(
						ventana,
						new JScrollPane(area),
						"Recorrido Inorder",
						JOptionPane.INFORMATION_MESSAGE
				);
			}
		});

		
		// MOSTRAR ÁRBOL
	
		btnMostrar.addActionListener(e -> {

			if(tablaActual.isEmpty()) {

				JOptionPane.showMessageDialog(
						ventana,
						"Seleccione una tabla."
				);

				return;
			}

			panelArbol.setRaiz(
					db.obtenerTabla(
							tablaActual
					)
					.getArbol()
					.getRaiz()
			);
		});

		ventana.setVisible(true);
	}
}