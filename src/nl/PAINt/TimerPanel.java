/**
q * PAINt 
 * 
 * Created for the course intro Human-Computer Interaction at the
 * Radboud Universiteit Nijmegen
 * 
 * 2013
 */
package nl.PAINt;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Luuk Scholten & Thom Wiggers
 *
 */
public class TimerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6198098720366019440L;
	private JLabel timeLabel;

	private static long startTime = System.currentTimeMillis();

	/**
	 * 
	 */
	public TimerPanel() {
		this.setLayout(new FlowLayout());

		this.timeLabel = new JLabel("00:00", JLabel.CENTER);
		this.setPreferredSize(new Dimension(200, 200));
		
		

		timeLabel.setPreferredSize(new Dimension(2000, 200));
		timeLabel.setFont(new Font("Sans-serif", Font.PLAIN, 80));
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				long seconds = (System.currentTimeMillis() - startTime) / 1000;
				timeLabel.setText(String.format("%02d:%02d",
						(int) Math.floor(seconds / 60), seconds % 60));
				
			}
		}, 200, 200);


		add(timeLabel);

		revalidate();
		
	}

	
	public static void reset() {
		startTime = System.currentTimeMillis();
	}


}
