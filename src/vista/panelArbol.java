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
	private static final int SEP_X = 36;   // separacion horizontal entre nodos consecutivos (inorder)
	private static final int MARGEN = 40;

	private int offsetX = MARGEN;
	private int contador;                  // posicion inorder durante el calculo de coordenadas
	private Timer timerResaltado;

	public panelArbol() {
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createTitledBorder("Árbol AVL"));
		setPreferredSize(new Dimension(800, 500));
	}
	//Llamar desde la ventana para actualizar la raiz
	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;

		// el lienzo crece con el arbol, asi nunca se corta y el scroll funciona
		int ancho = Math.max(800, contar(raiz) * SEP_X + 2 * MARGEN);
		int alto = Math.max(500, altura(raiz) * GAP_Y + 100);
		setPreferredSize(new Dimension(ancho, alto));

		revalidate();
		repaint();
	}

	//Resaltar un nodo por busqueda o insercion
	public void resaltar(int clave) {
		this.nodoResaltado = clave;
		repaint();

		//quitar el resaltado despues de 1.5 segundos (reiniciando el temporizador anterior)
		if (timerResaltado != null) {
			timerResaltado.stop();
		}
		timerResaltado = new Timer(1500, e -> {
			nodoResaltado = -1;
			repaint();
		});
		timerResaltado.setRepeats(false);
		timerResaltado.start();
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

		// el arbol se centra si es mas angosto que la ventana
		int anchoArbol = (contar(raiz) - 1) * SEP_X;
		offsetX = Math.max(MARGEN, (getWidth() - anchoArbol) / 2);
		contador = 0;
		calcularPosiciones(raiz, 0);
		dibujarAristas(g2, raiz);
		dibujarNodos(g2, raiz, true);
	}

	// Layout por recorrido inorder: la coordenada X es la posicion del nodo en el
	// orden de las claves, asi los nodos nunca se encimen sin importar la altura.
	private void calcularPosiciones(Nodo n, int profundidad) {
		if (n == null) return;

		calcularPosiciones(n.getIzq(), profundidad + 1);

		n.px = offsetX + contador * SEP_X;
		n.py = 50 + profundidad * GAP_Y;
		contador++;

		calcularPosiciones(n.getDer(), profundidad + 1);
	}

	private int contar(Nodo n) {
		return n == null ? 0 : 1 + contar(n.getIzq()) + contar(n.getDer());
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