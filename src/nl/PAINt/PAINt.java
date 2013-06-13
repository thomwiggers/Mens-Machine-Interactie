package nl.PAINt;

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

		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(10, 10);
		frame.setSize(1280, 900);
		frame.setTitle("PAINt");
		frame.setVisible(true);
		frame.setResizable(false);

	}
}
