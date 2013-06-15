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
import java.util.Arrays;

import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Luuk Scholten & Thom Wiggers
 * 
 */
public class OptiesPanel extends JPanel implements ChangeListener {

	/**
     * 
     */
	private static final long serialVersionUID = -8647274997122730322L;
	private CanvasPanel canvas;
	private JColorChooser colorPicker;

	/**
	 * @param canvas
	 * 
	 */
	public OptiesPanel(CanvasPanel theCanvas) {
		canvas = theCanvas;

		setBackground(Color.black);
		setPreferredSize(new Dimension(450, canvas.getHeight()));

		this.setLayout(new GridLayout(3, 1));

		colorPicker = new JColorChooser();
		colorPicker.getSelectionModel().addChangeListener(this);
		colorPicker.setPreviewPanel(new JPanel());
		boolean skipped = false;
		for (AbstractColorChooserPanel panel : colorPicker.getChooserPanels()) {
			if (!skipped) {
				skipped = true;
				continue;
			} else
				colorPicker.removeChooserPanel(panel);
		}
		colorPicker.setPreviewPanel(new JPanel());
		System.out.println(Arrays.toString(colorPicker.getChooserPanels()));
		this.add(colorPicker);
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
		canvas.setColor(colorPicker.getColor());

	}
}
