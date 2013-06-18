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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

/**
 * @author Luuk Scholten & Thom Wiggers
 * 
 */
public class OptiesPanel extends JPanel {

	/**
     * 
     */
	private static final long serialVersionUID = -8647274997122730322L;
	private CanvasPanel canvas;
	private JColorChooser colorPicker;

	private Logger logger;
	private boolean knopjes;

	/**
	 * @param canvas
	 * 
	 */
	public OptiesPanel(CanvasPanel theCanvas, boolean knopjesenabled) {
		logger = Logger.getLogger(getClass());
		this.knopjes = knopjesenabled;
		canvas = theCanvas;

		setPreferredSize(new Dimension(450, canvas.getHeight()));

		this.setLayout(new GridLayout(0, 1));

		initColorPicker();
		if (knopjesenabled) {
			this.add(new SliderPanel());
		}
		this.add(new TimerPanel());
		
	}

	private void initColorPicker() {
		logger.debug("initialising color picker");
		colorPicker = new JColorChooser();
		PreviewPanel pp = new PreviewPanel();
		colorPicker.getSelectionModel().addChangeListener(pp);

		colorPicker.setPreviewPanel(pp);

		boolean skipped = false;
		for (AbstractColorChooserPanel panel : colorPicker.getChooserPanels()) {
			if (!skipped) {
				skipped = true;
				continue;
			} else
				colorPicker.removeChooserPanel(panel);
		}
		this.add(colorPicker);
	}

	protected class PreviewPanel extends JPanel implements ChangeListener,
			ActionListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3445719326561602037L;
		private JLabel label;
		private JPanel block;
		private Logger logger;

		public PreviewPanel() {
			super();

			logger = Logger.getLogger(PreviewPanel.class);

			this.setLayout(new GridLayout(0, 2));

			setPreferredSize(new Dimension(400, 100));
			setSize(getPreferredSize());
			label = new JLabel("Huidige Kleur: ");
			label.setBackground(Color.blue);
			label.setSize(getPreferredSize());
			label.setVisible(true);
			this.block = new JPanel();
			block.setSize(80, 80);
			block.setBackground(colorPicker.getColor());

			JButton knopjeFill = new JButton("Gebruik als Fill");
			knopjeFill.setActionCommand("FILL");
			knopjeFill.addActionListener(this);
			JButton knopjeLijn = new JButton("Gebruik als Lijn");
			knopjeLijn.setActionCommand("LIJN");
			knopjeLijn.addActionListener(this);

			add(label);
			add(block);
			if (knopjes) {
				add(knopjeFill);
				add(knopjeLijn);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent
		 * )
		 */
		@Override
		public void stateChanged(ChangeEvent e) {
			logger.info("Gebruiker koos nieuwe kleur "
					+ colorPicker.getColor().toString());
			block.setBackground(colorPicker.getColor());
			canvas.setActiveColor(colorPicker.getColor());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			logger.info("Gebruiker klinkte op actie " + arg0.getActionCommand());
			switch (arg0.getActionCommand()) {
			case "FILL":
				canvas.setColor(colorPicker.getColor());
				break;
			case "LIJN":
				canvas.setLineColor(colorPicker.getColor());
				break;
			default:
				IllegalArgumentException e = new IllegalArgumentException("WAT");
				logger.error("Unrecognised action in PreviewPanel", e);
				throw e;
			}
		}

	}

	protected class SliderPanel extends JPanel implements ChangeListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6529442891082983907L;
		private JSlider lijnDikte;
		private Logger logger = Logger.getLogger(SliderPanel.class);
		
		public SliderPanel() {
			logger.debug("initialising SliderPanel");

			this.setLayout(new GridLayout(0, 2));
			
			this.add(new JLabel("Lijndikte: ", JLabel.RIGHT));

			lijnDikte = new JSlider(10, 150, 30);
			lijnDikte.addChangeListener(this);

			this.add(lijnDikte);

			JButton knopvooruit = new JButton("haal naar voren");
			knopvooruit.setActionCommand("z-index+");
			knopvooruit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					logger.info("naar voren geklikt");
					canvas.moveSelectedBackward();
				}
			});

			JButton knopachteruit = new JButton("haal naar achteren");
			knopachteruit.setActionCommand("z-index-");
			knopachteruit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					logger.info("naar achteren geklikt");
					canvas.moveSelectedForward();
				}
			});
			
			JButton knopFill = new JButton("toggle fill");
			knopFill.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					logger.info("toggledFill geklikt");
					canvas.toggleFill();
					
				}
			});

			JButton deleteKnopje = new JButton("Delete");
			deleteKnopje.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					logger.info("pressed deleteselected");
					canvas.deleteSelected();
				}
			});

			if (knopjes) {
				this.add(knopvooruit);
				this.add(knopachteruit);
				this.add(knopFill);
				this.add(deleteKnopje);
			}

		}


		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent
		 * )
		 */
		@Override
		public void stateChanged(ChangeEvent arg0) {
			logger.info("Gebruiker koos lijndikte: " + lijnDikte.getValue());
			canvas.setLineWidth((float) lijnDikte.getValue() / 10f);
		}
	}
}
