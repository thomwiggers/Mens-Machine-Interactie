package nl.PAINt;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = -3312756899988117703L;
	private final CanvasPanel canvas;
	private final KnopjesPanel knopjes;
	private OptiesPanel optiesPanel;
	WaitPanel wpanel;
	private Logger logger;
	private boolean jemoeder = true;



	public MainWindow() {

		wpanel = new WaitPanel();
		super.add(wpanel);

		// Logging
		BasicConfigurator.configure();
		try {
			FileAppender fa = new FileAppender(new PatternLayout(
					" %d{HH:mm:ss,SSS} [%17.17t] %-5p %32.32c %x - %m%n"), "PAINt.log",
					true);
			Logger.getRootLogger().addAppender(fa);
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger = Logger.getLogger(getClass());

		this.canvas = new CanvasPanel();

		this.knopjes = new KnopjesPanel(canvas);

		this.optiesPanel = new OptiesPanel(canvas, jemoeder);

		new MessageServer(this);
		connected();
	}

	public CanvasPanel getCanvas() {
		return this.canvas;
	}

	public void connected() {
		super.remove(wpanel);

		logger.info("Started Logging");

		logger.debug("initialising GUI");

		TimerPanel.reset();

		if (jemoeder) {
			add(knopjes, BorderLayout.WEST);
			logger.info("KNOPJES");
		} else {
			logger.info("GEEN KNOPJES");
		}
		super.add(optiesPanel, BorderLayout.EAST);
		super.add(canvas);

		super.invalidate();
		super.validate();
		super.repaint();

	}
}
