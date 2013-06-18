/**
 * PAINt
 * 
 * Created for the course intro Human-Computer Interaction at the
 * Radboud Universiteit Nijmegen
 * 
 * 2013
 */
package nl.PAINt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Luuk Scholten & Thom Wiggers
 * 
 */
public class KnopjesPanel extends JPanel implements ActionListener {

	public enum KnopjesActies {
		VIERKANT_TEKENEN, VIERKANT_TEKENEN_FILLED, ELLIPS_TEKENEN, ELLIPSE_TEKENEN_FILLED, DRIEHOEK_TEKENEN, DRIEHOEK_TEKENEN_FILLED, RESIZE, DELETE, LINE, TEXT;
	}

	/**
     * 
     */
	private static final long serialVersionUID = 4988132356531200790L;
	private final CanvasPanel canvas;

	/**
	 * constructor doet dingen
	 */
	public KnopjesPanel(CanvasPanel theCanvas) {
		this.canvas = theCanvas;

		this.setPreferredSize(new Dimension(80, canvas.getHeight()));
		this.setLayout(new GridLayout(0, 1));
		setBackground(Color.blue);
		setAlignmentX(LEFT_ALIGNMENT);

		initKnopjes();
	}

	private void initKnopjes() {
		ImageIcon icon = new ImageIcon("resources/new_rectangle.png");
		Image img = icon.getImage();
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawImage(img, 40, 30, 80,80, null);
		ImageIcon newIcon = new ImageIcon(bi);
		
		JButton vierkantKnopje = new JButton("Rectangle", newIcon);
		vierkantKnopje.addActionListener(this);
		vierkantKnopje.setActionCommand(KnopjesActies.VIERKANT_TEKENEN.toString());
		vierkantKnopje.setSize(new Dimension(40, 40));
		vierkantKnopje.setMargin(new Insets(0, 0, 0, 0));
	   


		icon = new ImageIcon("resources/new_oval.png");
		img = icon.getImage();
		bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = bi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawImage(img, 40, 30, 80,80, null);
		newIcon = new ImageIcon(bi);
		
		JButton ellipseKnopje = new JButton("Ellipse", newIcon);
		ellipseKnopje.addActionListener(this);
		ellipseKnopje.setActionCommand(KnopjesActies.ELLIPS_TEKENEN.toString());
		ellipseKnopje.setSize(new Dimension(40, 40));
		ellipseKnopje.setMargin(new Insets(0, 0, 0, 0));

		icon = new ImageIcon("resources/new_triangle.png");
		img = icon.getImage();
		bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = bi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawImage(img, 40, 30, 80,80, null);
		newIcon = new ImageIcon(bi);
		
		JButton driehoekKnopje = new JButton("Triangle", newIcon);
		driehoekKnopje.addActionListener(this);
		driehoekKnopje.setActionCommand(KnopjesActies.DRIEHOEK_TEKENEN.toString());
		driehoekKnopje.setSize(new Dimension(40, 40));
		driehoekKnopje.setMargin(new Insets(0, 0, 0, 0));

		icon = new ImageIcon("resources/new_line.png");
		img = icon.getImage();
		bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = bi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawImage(img, 40, 30, 80,80, null);
		newIcon = new ImageIcon(bi);
		
		JButton lineKnopje = new JButton("Line", newIcon);
		lineKnopje.addActionListener(this);
		lineKnopje.setActionCommand(KnopjesActies.LINE.toString());
		lineKnopje.setSize(new Dimension(40, 40));
		lineKnopje.setMargin(new Insets(0, 0, 0, 0));

		icon = new ImageIcon("resources/new_text.png");
		img = icon.getImage();
		bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = bi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawImage(img, 40, 30, 80,80, null);
		newIcon = new ImageIcon(bi);
		
		JButton textKnopje = new JButton("Text", newIcon);
		textKnopje.addActionListener(this);
		textKnopje.setActionCommand(KnopjesActies.TEXT.toString());
		textKnopje.setSize(new Dimension(40, 40));
		textKnopje.setMargin(new Insets(0, 0, 0, 0));

		icon = new ImageIcon("resources/select.png");
		img = icon.getImage();
		bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = bi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawImage(img, 40, 30, 80,80, null);
		newIcon = new ImageIcon(bi);
		
		JButton moveKnopje = new JButton("Select", newIcon);
		moveKnopje.setActionCommand(KnopjesActies.RESIZE.toString());
		moveKnopje.addActionListener(this);
		moveKnopje.setSize(40, 40);
		moveKnopje.setMargin(new Insets(0, 0, 0, 0));

		this.add(vierkantKnopje);
		this.add(ellipseKnopje);
		this.add(driehoekKnopje);
		this.add(lineKnopje);
		this.add(textKnopje);
		this.add(moveKnopje);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (KnopjesActies.valueOf(e.getActionCommand())) {
		case DELETE:
			canvas.setMode(PanelMode.DELETE);
			break;
		case DRIEHOEK_TEKENEN:
			canvas.setMode(PanelMode.TRIANGLE);
			break;
		case DRIEHOEK_TEKENEN_FILLED:
			canvas.setMode(PanelMode.TRIANGLE);
			break;
		case ELLIPSE_TEKENEN_FILLED:
			canvas.setMode(PanelMode.ELL_FILLED);
			break;
		case ELLIPS_TEKENEN:
			canvas.setMode(PanelMode.ELLIPSE);
			break;
		case RESIZE:
			canvas.setMode(PanelMode.SELECT);
			break;
		case VIERKANT_TEKENEN:
			canvas.setMode(PanelMode.RECTANGLE);
			break;
		case VIERKANT_TEKENEN_FILLED:
			canvas.setMode(PanelMode.RECT_FILLED);
			break;
		case LINE:
			canvas.setMode(PanelMode.LINE);
			break;
		case TEXT:
			canvas.setMode(PanelMode.TEXT);
			break;

		}

	}

}
