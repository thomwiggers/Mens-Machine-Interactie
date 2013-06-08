/**
 * PAINt
 * 
 * Created for the course intro Human-Computer Interaction at the
 * Radboud Universiteit Nijmegen
 * 
 * 2013
 */
package nl.PAINt;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 * @author Luuk Scholten & Thom Wiggers
 *
 */
public class StatusbarPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 6547918643843952081L;

    private final JLabel statusLabel = new JLabel();

    private static final String prefixMode = "Mode: ";

    /**
     * 
     */
    public StatusbarPanel(final int width) {
	setBorder(new BevelBorder(BevelBorder.LOWERED));
	setPreferredSize(new Dimension(width, 16));
	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	setText("None");
	statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
	this.add(statusLabel);
    }

    public void setText(final String msg) {
	statusLabel.setText(prefixMode + msg);
    }

}
