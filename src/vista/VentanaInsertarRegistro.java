package vista;

import modelo.Registro;
import modelo.Tabla;
import gestor.DatabaseManager;

import javax.swing.*;
import java.util.HashMap;

public class VentanaInsertarRegistro extends JDialog {

	//panel
	JPanel panel =
			new JPanel(null);

	//guardar campos
	HashMap<String, JTextField> camposTexto;

	//constructor
	public VentanaInsertarRegistro(
			JFrame padre,
			DatabaseManager db,
			String nombreTabla,
			panelArbol panelArbol
	) {

		super(
				padre,
				"Insertar Registro",
				true
		);

		camposTexto =
				new HashMap<>();

		Tabla tabla =
				db.obtenerTabla(
						nombreTabla
				);

		setSize(
				400,
				650
		);

		setLocationRelativeTo(
				padre
		);

		add(panel);

		//clave
		JLabel lblClave =
				new JLabel(
						"Clave:"
				);

		JTextField txtClave =
				new JTextField();

		lblClave.setBounds(
				20,
				20,
				120,
				25
		);

		txtClave.setBounds(
				140,
				20,
				180,
				25
		);

		panel.add(lblClave);

		panel.add(txtClave);

		//campos
		int y = 80;

		for(String campo : tabla.getCampos()) {

			JLabel lbl =
					new JLabel(
							campo + ":"
					);

			JTextField txt =
					new JTextField();

			lbl.setBounds(
					20,
					y,
					120,
					25
			);

			txt.setBounds(
					140,
					y,
					180,
					25
			);

			panel.add(lbl);

			panel.add(txt);

			camposTexto.put(
					campo,
					txt
			);

			y += 50;
		}

		//boton insertar
		JButton btnInsertar =
				new JButton(
						"INSERTAR"
				);

		btnInsertar.setBounds(
				120,
				y + 20,
				140,
				35
		);

		panel.add(btnInsertar);

		//accion insertar
		btnInsertar.addActionListener(e -> {

			try {

				int clave =
						Integer.parseInt(
								txtClave.getText()
						);

				//verificar duplicado
				if(
						tabla.getArbol()
								.buscar(clave)
								!= null
				) {

					JOptionPane.showMessageDialog(
							this,
							"Clave duplicada."
					);

					return;
				}

				Registro r =
						new Registro();

				for(String campo : camposTexto.keySet()) {

					r.agregarCampo(
							campo,
							camposTexto.get(campo)
									.getText()
					);
				}

				//insertar
				tabla.getArbol()
						.insertar(
								clave,
								r
						);

				//guardar json
				db.obtenerArchivo(nombreTabla)
						.guardarRegistro(
								clave,
								r
						);

				//actualizar arbol visual
				panelArbol.setRaiz(
						tabla.getArbol()
								.getRaiz()
				);

				panelArbol.resaltar(
						clave
				);

				JOptionPane.showMessageDialog(
						this,
						"Registro insertado."
				);

				dispose();

			}catch(Exception ex) {

				JOptionPane.showMessageDialog(
						this,
						"Error insertando."
				);
			}
		});

		setVisible(true);
	}
}