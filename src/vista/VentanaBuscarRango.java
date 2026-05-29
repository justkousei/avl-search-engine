package vista;

import gestor.DatabaseManager;
import modelo.Registro;

import javax.swing.*;
import java.util.ArrayList;

public class VentanaBuscarRango extends JDialog {

	public VentanaBuscarRango(
			JFrame padre,
			DatabaseManager db,
			String tablaActual,
			panelArbol panel
	) {

		super(
				padre,
				"Buscar Rango",
				true
		);

		setSize(
				400,
				300
		);

		setLayout(null);

		setLocationRelativeTo(
				padre
		);

		JLabel lblMin =
				new JLabel(
						"Clave mínima:"
				);

		lblMin.setBounds(
				30,
				30,
				120,
				25
		);

		add(lblMin);

		JTextField txtMin =
				new JTextField();

		txtMin.setBounds(
				160,
				30,
				150,
				25
		);

		add(txtMin);

		JLabel lblMax =
				new JLabel(
						"Clave máxima:"
				);

		lblMax.setBounds(
				30,
				80,
				120,
				25
		);

		add(lblMax);

		JTextField txtMax =
				new JTextField();

		txtMax.setBounds(
				160,
				80,
				150,
				25
		);

		add(txtMax);

		JButton btnBuscar =
				new JButton(
						"BUSCAR"
				);

		btnBuscar.setBounds(
				110,
				140,
				150,
				35
		);

		add(btnBuscar);

		btnBuscar.addActionListener(e -> {

			try {

				int min =
						Integer.parseInt(
								txtMin.getText()
						);

				int max =
						Integer.parseInt(
								txtMax.getText()
						);

				ArrayList<Registro> lista =
						db.obtenerTabla(
								tablaActual
						)
								.getArbol()
								.buscarRango(
										min,
										max
								);

				StringBuilder sb =
						new StringBuilder();

				for(
						Registro r : lista
				) {

					sb.append(
							r.toString()
					)
							.append(
									"\n\n"
							);
				}

				JTextArea area =
						new JTextArea(
								sb.toString()
						);

				area.setEditable(false);

				JOptionPane.showMessageDialog(
						padre,
						new JScrollPane(area),
						"Resultados",
						JOptionPane.INFORMATION_MESSAGE
				);

				dispose();

			} catch(Exception ex) {

				JOptionPane.showMessageDialog(
						padre,
						"Datos inválidos."
				);
			}
		});

		setVisible(true);
	}
}