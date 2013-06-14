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

import javax.swing.JPanel;

/**
 * @author Luuk Scholten & Thom Wiggers
 * 
 */
public class KnopjesPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 4988132356531200790L;

    /**
     * constructor doet dingen
     */
    public KnopjesPanel() {
	this.setPreferredSize(new Dimension(40, 900));
	setBackground(Color.blue);
	setAlignmentX(LEFT_ALIGNMENT);

	this.setVisible(true);

	// todo knopjes toevoegen
    }

}
