package vista;

import modelo.Registro;
import modelo.Tabla;
import gestor.DatabaseManager;

import javax.swing.*;
import java.awt.*;

public class VentanaBuscarRegistro extends JDialog {

	//panel
	JPanel panel = new JPanel(null);

	//constructor
	public VentanaBuscarRegistro(
			JFrame padre,
			DatabaseManager db,
			String nombreTabla,
			panelArbol panelArbol
	) {

		super(padre, "Buscar Registro", true);

		Tabla tabla = db.obtenerTabla(nombreTabla);

		setSize(350, 220);

		setLocationRelativeTo(padre);

		add(panel);

		//label
		JLabel lblClave = new JLabel("Clave:");

		lblClave.setBounds(30, 40, 100, 25);

		panel.add(lblClave);

		//textfield
		JTextField txtClave = new JTextField();

		txtClave.setBounds(120, 40, 150, 25);

		panel.add(txtClave);

		//boton
		JButton btnBuscar = new JButton("BUSCAR");

		btnBuscar.setBounds(90, 100, 140, 35);

		panel.add(btnBuscar);

		//accion
		btnBuscar.addActionListener(e -> {

			try {

				int clave = Integer.parseInt(
						txtClave.getText()
				);

				Registro r = tabla.getArbol()
						.buscar(clave);

				if(r != null) {

					panelArbol.resaltar(clave);

					JOptionPane.showMessageDialog(
							this,
							r.toString()
					);

				}else {

					JOptionPane.showMessageDialog(
							this,
							"No encontrado."
					);
				}

			}catch(Exception ex) {

				JOptionPane.showMessageDialog(
						this,
						"Error buscando."
				);
			}
		});

		setVisible(true);
	}
}