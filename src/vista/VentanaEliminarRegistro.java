package vista;

import modelo.Tabla;
import gestor.DatabaseManager;

import javax.swing.*;
import java.awt.*;

public class VentanaEliminarRegistro extends JDialog {

	//panel
	JPanel panel = new JPanel(null);

	//constructor
	public VentanaEliminarRegistro(
			JFrame padre,
			DatabaseManager db,
			String nombreTabla,
			panelArbol panelArbol
	) {

		super(padre, "Eliminar Registro", true);

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
		JButton btnEliminar = new JButton("ELIMINAR");

		btnEliminar.setBounds(90, 100, 140, 35);

		panel.add(btnEliminar);

		//accion
		btnEliminar.addActionListener(e -> {

			try {

				int clave = Integer.parseInt(
						txtClave.getText()
				);

				if(tabla.getArbol()
						.buscar(clave) == null) {

					JOptionPane.showMessageDialog(
							this,
							"No existe."
					);

					return;
				}

				tabla.getArbol().eliminar(clave);

				db.obtenerArchivo(nombreTabla)
						.limpiarArchivo();

				tabla.getArbol().guardarEnArchivo(
						db.obtenerArchivo(nombreTabla)
				);

				panelArbol.setRaiz(
						tabla.getArbol().getRaiz()
				);

				JOptionPane.showMessageDialog(
						this,
						"Registro eliminado."
				);

				dispose();

			}catch(Exception ex) {

				JOptionPane.showMessageDialog(
						this,
						"Error eliminando."
				);
			}
		});

		setVisible(true);
	}
}