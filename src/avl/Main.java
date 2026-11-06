package avl;

import javax.swing.SwingUtilities;

import vista.VentanaDB;

public class Main {

	public static void main(String[] args) {

		// Swing debe crearse en el hilo de eventos (EDT)
		SwingUtilities.invokeLater(() -> new VentanaDB());
	}
}
