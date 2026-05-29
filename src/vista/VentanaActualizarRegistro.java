package vista;

import modelo.Registro;
import modelo.Tabla;
import gestor.DatabaseManager;

import javax.swing.*;
import java.util.HashMap;

public class VentanaActualizarRegistro extends JDialog {

	//panel
	JPanel panel =
			new JPanel(null);

	//guardar campos
	HashMap<String, JTextField> camposTexto;

	//constructor
	public VentanaActualizarRegistro(
			JFrame padre,
			DatabaseManager db,
			String nombreTabla,
			panelArbol panelArbol
	) {

		super(
				padre,
				"Actualizar Registro",
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

		//boton cargar
		JButton btnCargar =
				new JButton(
						"CARGAR"
				);

		btnCargar.setBounds(
				40,
				y + 20,
				120,
				35
		);

		panel.add(btnCargar);

		//boton actualizar
		JButton btnActualizar =
				new JButton(
						"ACTUALIZAR"
				);

		btnActualizar.setBounds(
				190,
				y + 20,
				140,
				35
		);

		panel.add(btnActualizar);

		//accion cargar
		btnCargar.addActionListener(e -> {

			try {

				int clave =
						Integer.parseInt(
								txtClave.getText()
						);

				Registro r =
						tabla.getArbol()
								.buscar(clave);

				if(r == null) {

					JOptionPane.showMessageDialog(
							this,
							"No encontrado."
					);

					return;
				}

				for(String campo : tabla.getCampos()) {

					camposTexto.get(campo)
							.setText(
									r.obtenerCampo(campo)
							);
				}

				panelArbol.resaltar(
						clave
				);

			}catch(Exception ex) {

				JOptionPane.showMessageDialog(
						this,
						"Error cargando."
				);
			}
		});

		//accion actualizar
		btnActualizar.addActionListener(e -> {

			try {

				int clave =
						Integer.parseInt(
								txtClave.getText()
						);

				Registro nuevo =
						new Registro();

				for(String campo : tabla.getCampos()) {

					nuevo.agregarCampo(
							campo,
							camposTexto.get(campo)
									.getText()
					);
				}

				boolean actualizado =
						tabla.getArbol()
								.actualizar(
										clave,
										nuevo
								);

				if(actualizado) {

					//reescribir json
					db.obtenerArchivo(nombreTabla)
							.limpiarArchivo();

					tabla.getArbol()
							.guardarEnArchivo(
									db.obtenerArchivo(
											nombreTabla
									)
							);

					JOptionPane.showMessageDialog(
							this,
							"Registro actualizado."
					);

					dispose();

				}else {

					JOptionPane.showMessageDialog(
							this,
							"No encontrado."
					);
				}

			}catch(Exception ex) {

				JOptionPane.showMessageDialog(
						this,
						"Error actualizando."
				);
			}
		});

		setVisible(true);
	}
}