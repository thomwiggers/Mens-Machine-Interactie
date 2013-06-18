package nl.PAINt;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PAINt implements Runnable {

	public static void main(final String[] args) {
		final PAINt m = new PAINt();
		SwingUtilities.invokeLater(m);
	}

	@Override
	public void run() {
		final JFrame frame = new MainWindow();
		
		GraphicsDevice myDevice = GraphicsEnvironment.
		        getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.dispose();
		frame.setUndecorated(true);
		frame.setSize(new Dimension(1920, 1080));
		frame.repaint();
		frame.revalidate();
		
		try {
		    myDevice.setFullScreenWindow(frame);	
		} finally {
		    myDevice.setFullScreenWindow(null);
		}


	}
}
