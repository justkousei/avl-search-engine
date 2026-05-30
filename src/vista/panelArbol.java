package vista;

import avl.Nodo;
import javax.swing.*;
import java.awt.*;

public class panelArbol extends JPanel {

	private Nodo raiz;
	private int nodoResaltado = -1;

	//Colores
	private static final Color COLOR_RAIZ       = new Color(15, 110, 86);
	private static final Color COLOR_RAIZ_FONDO = new Color(225, 245, 238);
	private static final Color COLOR_NODO       = new Color(24, 95, 165);
	private static final Color COLOR_NODO_FONDO = new Color(230, 241, 251);
	private static final Color COLOR_RESALTADO  = new Color(153, 60, 29);
	private static final Color COLOR_RES_FONDO  = new Color(250, 236, 231);
	private static final Color COLOR_ARISTA     = new Color(95, 94, 90);
	private static final Color COLOR_ALTURA     = new Color(186, 117, 23);
	private static final Color COLOR_BF_OK      = new Color(15, 110, 86);
	private static final Color COLOR_BF_MAL     = new Color(163, 45, 45);

	private static final int RADIO = 24;
	private static final int GAP_Y = 90;

	public panelArbol() {
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createTitledBorder("Árbol AVL"));
		//El tamaño del lienzo interno para los sliders
		setPreferredSize(new Dimension(2000, 1000)); 
	}
	//Llamar desde la ventana para actualizar la raiz
	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
		repaint();
	}

	//Resaltar un nodo por busqueda o insercion
	public void resaltar(int clave) {
		this.nodoResaltado = clave;
		repaint();

		//quitar el resaltado despues de 1.5 segundos
		Timer t = new Timer(1500, e -> {
			nodoResaltado = -1;
			repaint();
		});
		t.setRepeats(false);
		t.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (raiz == null) {
			g2.setColor(new Color(136, 135, 128));
			g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
			FontMetrics fm = g2.getFontMetrics();
			String txt = "Árbol vacío — inserta nodos para comenzar";
			g2.drawString(txt, (getWidth() - fm.stringWidth(txt)) / 2, getHeight() / 2);
			return;
		}

		// Se eliminó la variable gapInicial y ahora solo se pasa la coordenada inicial
		calcularPosiciones(raiz, getWidth() / 2, 50);
		dibujarAristas(g2, raiz);
		dibujarNodos(g2, raiz, true);
	}

	// Método modificado para calcular la separación (gap) según la altura de cada nodo
	private void calcularPosiciones(Nodo n, int x, int y) {
		if (n == null) return;
		
		n.px = x;
		n.py = y;

		// El espacio horizontal crece exponencialmente según la altura actual del nodo
		int gap = (int) Math.pow(2, altura(n) - 1) * 28;
		
		// Evita que los nodos se encimen si el gap calculado es muy pequeño
		gap = Math.max(gap, RADIO * 2 + 10);

		if (n.getIzq() != null) {
			calcularPosiciones(n.getIzq(), x - gap, y + GAP_Y);
		}
		if (n.getDer() != null) {
			calcularPosiciones(n.getDer(), x + gap, y + GAP_Y);
		}
	}
	//Asignar coordenadas a cada nodo recursivamente
	private void calcularPosiciones(Nodo n, int x, int y, int gap) {
		if (n == null) return;
		n.px = x;
		n.py = y;
		int nuevoGap = Math.max(gap / 2, RADIO + 15);
		calcularPosiciones(n.getIzq(), x - nuevoGap, y + GAP_Y, nuevoGap);
		calcularPosiciones(n.getDer(), x + nuevoGap, y + GAP_Y, nuevoGap);
	}

	//Dibujar lineas de aristas
	private void dibujarAristas(Graphics2D g2, Nodo n) {
		if (n == null) return;
		g2.setColor(COLOR_ARISTA);
		g2.setStroke(new BasicStroke(1.5f));
		if (n.getIzq() != null) {
			g2.drawLine(n.px, n.py, n.getIzq().px, n.getIzq().py);
			dibujarAristas(g2, n.getIzq());
		}
		if (n.getDer() != null) {
			g2.drawLine(n.px, n.py, n.getDer().px, n.getDer().py);
			dibujarAristas(g2, n.getDer());
		}
	}

	//Dibujar cada nodo con circulo y etiquetas
	private void dibujarNodos(Graphics2D g2, Nodo n, boolean esRaiz) {
		if (n == null) return;

		Color stroke, fondo;
		if (n.getClave() == nodoResaltado) {
			stroke = COLOR_RESALTADO; fondo = COLOR_RES_FONDO;
		} else if (esRaiz) {
			stroke = COLOR_RAIZ;     fondo = COLOR_RAIZ_FONDO;
		} else {
			stroke = COLOR_NODO;     fondo = COLOR_NODO_FONDO;
		}

		//relleno del circulo
		g2.setColor(fondo);
		g2.fillOval(n.px - RADIO, n.py - RADIO, RADIO * 2, RADIO * 2);

		//borde del circulo
		g2.setColor(stroke);
		g2.setStroke(new BasicStroke(2f));
		g2.drawOval(n.px - RADIO, n.py - RADIO, RADIO * 2, RADIO * 2);

		//clave dentro del circulo
		g2.setFont(new Font("SansSerif", Font.BOLD, 13));
		FontMetrics fm = g2.getFontMetrics();
		String claveTxt = String.valueOf(n.getClave());
		g2.drawString(claveTxt,
				n.px - fm.stringWidth(claveTxt) / 2,
				n.py + fm.getAscent() / 2 - 1);

		//altura sobre el nodo
		g2.setColor(COLOR_ALTURA);
		g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
		FontMetrics fmS = g2.getFontMetrics();
		String hTxt = "h=" + n.getAltura();
		g2.drawString(hTxt,
				n.px - fmS.stringWidth(hTxt) / 2,
				n.py - RADIO - 3);

		//factor de balanceo bajo el nodo
		int bf = altura(n.getDer()) - altura(n.getIzq());
		g2.setColor(Math.abs(bf) <= 1 ? COLOR_BF_OK : COLOR_BF_MAL);
		String bfTxt = "bf=" + (bf > 0 ? "+" : "") + bf;
		g2.drawString(bfTxt,
				n.px - fmS.stringWidth(bfTxt) / 2,
				n.py + RADIO + 11);

		dibujarNodos(g2, n.getIzq(), false);
		dibujarNodos(g2, n.getDer(), false);
	}

	private int altura(Nodo n) {
		return n == null ? 0 : n.getAltura();
	}
}