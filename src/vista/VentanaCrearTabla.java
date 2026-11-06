package vista;

import gestor.DatabaseManager;

import javax.swing.*;
import java.util.ArrayList;

public class VentanaCrearTabla extends JDialog {

	public VentanaCrearTabla(
			JFrame padre,
			DatabaseManager db
	) {

		super(
				padre,
				"Crear Tabla",
				true
		);

		setSize(
				450,
				500
		);

		setLayout(null);

		setLocationRelativeTo(
				padre
		);

		JLabel lblNombre =
				new JLabel(
						"Nombre tabla:"
				);

		lblNombre.setBounds(
				30,
				30,
				120,
				25
		);

		add(lblNombre);

		JTextField txtNombre =
				new JTextField();

		txtNombre.setBounds(
				160,
				30,
				200,
				25
		);

		add(txtNombre);

		JTextArea areaCampos =
				new JTextArea();

		JScrollPane scroll =
				new JScrollPane(
						areaCampos
				);

		scroll.setBounds(
				30,
				80,
				330,
				250
		);

		add(scroll);

		areaCampos.setEditable(false);

		ArrayList<String> campos =
				new ArrayList<>();

		JButton btnAgregarCampo =
				new JButton(
						"AGREGAR CAMPO"
				);

		btnAgregarCampo.setBounds(
				30,
				350,
				150,
				35
		);

		add(btnAgregarCampo);

		btnAgregarCampo.addActionListener(e -> {

			String nombreCampo =
					JOptionPane.showInputDialog(
							"Nombre del campo:"
					);

			if(nombreCampo == null) {

				return;
			}

			nombreCampo = nombreCampo.trim();

			if(!DatabaseManager.nombreValido(nombreCampo)) {

				JOptionPane.showMessageDialog(
						this,
						"Nombre de campo inválido: use solo letras, números y _ ."
				);

				return;
			}

			if(campos.contains(nombreCampo)) {

				JOptionPane.showMessageDialog(
						this,
						"Ese campo ya existe."
				);

				return;
			}

			campos.add(
					nombreCampo
			);

			areaCampos.append(
					nombreCampo
					+ "\n"
			);
		});

		JButton btnCrear =
				new JButton(
						"CREAR TABLA"
				);

		btnCrear.setBounds(
				210,
				350,
				150,
				35
		);

		add(btnCrear);

		btnCrear.addActionListener(e -> {

			String nombre =
					txtNombre.getText().trim();

			if(!DatabaseManager.nombreValido(nombre)) {

				JOptionPane.showMessageDialog(
						this,
						"Nombre inválido: use solo letras, números y _ ."
				);

				return;
			}

			if(!db.crearTabla(nombre, campos)) {

				JOptionPane.showMessageDialog(
						this,
						"La tabla '" + nombre + "' ya existe."
				);

				return;
			}

			JOptionPane.showMessageDialog(
					padre,
					"Tabla creada."
			);

			dispose();
		});

		setVisible(true);
	}
}