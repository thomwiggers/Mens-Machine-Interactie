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
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Luuk Scholten & Thom Wiggers
 * 
 */
public class KnopjesPanel extends JPanel implements ActionListener {

	public enum KnopjesActies {
		VIERKANT_TEKENEN, VIERKANT_TEKENEN_FILLED, ELLIPS_TEKENEN, ELLIPSE_TEKENEN_FILLED, DRIEHOEK_TEKENEN, DRIEHOEK_TEKENEN_FILLED, RESIZE, DELETE;
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
		this.setLayout(new GridLayout(0, 2));
		setBackground(Color.blue);
		setAlignmentX(LEFT_ALIGNMENT);

		initKnopjes();
	}

	private void initKnopjes() {
		JButton vierkantKnopje = new JButton("Sq");
		vierkantKnopje.addActionListener(this);
		vierkantKnopje.setActionCommand(KnopjesActies.VIERKANT_TEKENEN.toString());
		vierkantKnopje.setSize(new Dimension(40, 40));
		vierkantKnopje.setMargin(new Insets(0, 0, 0, 0));

		JButton vierkantKnopjeFilled = new JButton("SqF");
		vierkantKnopjeFilled.addActionListener(this);
		vierkantKnopjeFilled.setActionCommand(KnopjesActies.VIERKANT_TEKENEN_FILLED
				.toString());
		vierkantKnopjeFilled.setSize(new Dimension(40, 40));
		vierkantKnopjeFilled.setMargin(new Insets(0, 0, 0, 0));

		JButton ellipseKnopje = new JButton("El");
		ellipseKnopje.addActionListener(this);
		ellipseKnopje.setActionCommand(KnopjesActies.ELLIPS_TEKENEN.toString());
		ellipseKnopje.setSize(new Dimension(40, 40));
		ellipseKnopje.setMargin(new Insets(0, 0, 0, 0));

		JButton ellipseKnopjeFilled = new JButton("ElF");
		ellipseKnopjeFilled.addActionListener(this);
		ellipseKnopjeFilled.setActionCommand(KnopjesActies.ELLIPSE_TEKENEN_FILLED
				.toString());
		ellipseKnopjeFilled.setSize(new Dimension(40, 40));
		ellipseKnopjeFilled.setMargin(new Insets(0, 0, 0, 0));

		JButton driehoekKnopje = new JButton("Tr");
		driehoekKnopje.addActionListener(this);
		driehoekKnopje.setActionCommand(KnopjesActies.DRIEHOEK_TEKENEN.toString());
		driehoekKnopje.setSize(new Dimension(40, 40));
		driehoekKnopje.setMargin(new Insets(0, 0, 0, 0));

		JButton driehoekKnopjeFilled = new JButton("TrF");
		driehoekKnopjeFilled.addActionListener(this);
		driehoekKnopjeFilled.setActionCommand(KnopjesActies.DRIEHOEK_TEKENEN_FILLED
				.toString());
		driehoekKnopjeFilled.setSize(new Dimension(40, 40));
		driehoekKnopjeFilled.setMargin(new Insets(0, 0, 0, 0));

		JButton moveKnopje = new JButton("Re");
		moveKnopje.setActionCommand(KnopjesActies.RESIZE.toString());
		moveKnopje.addActionListener(this);
		moveKnopje.setSize(40, 40);
		moveKnopje.setMargin(new Insets(0, 0, 0, 0));

		JButton deleteKnopje = new JButton("X");
		deleteKnopje.setActionCommand(KnopjesActies.DELETE.toString());
		deleteKnopje.addActionListener(this);
		deleteKnopje.setSize(40, 40);
		deleteKnopje.setMargin(new Insets(0, 0, 0, 0));

		this.add(vierkantKnopje);
		this.add(vierkantKnopjeFilled);
		this.add(ellipseKnopje);
		this.add(ellipseKnopjeFilled);
		this.add(driehoekKnopje);
		this.add(driehoekKnopjeFilled);
		this.add(moveKnopje);
		this.add(deleteKnopje);

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
			canvas.setMode(PanelMode.ELL);
			break;
		case RESIZE:
			canvas.setMode(PanelMode.RESIZE);
			break;
		case VIERKANT_TEKENEN:
			canvas.setMode(PanelMode.RECT);
			break;
		case VIERKANT_TEKENEN_FILLED:
			canvas.setMode(PanelMode.RECT);
			break;
		default:
			break;

		}

	}

}
