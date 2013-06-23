package nl.PAINt;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WaitPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2180416319590058558L;

	public WaitPanel() {
		setPreferredSize(new Dimension(1440, 900));

		this.setLayout(new FlowLayout());
		
		ImageIcon icon = new ImageIcon("resources/aperture.png");
		Image img = icon.getImage();
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawImage(img, 50, 50, 50, 50, null);
		ImageIcon newIcon = new ImageIcon(bi);
		
		JLabel logo = new JLabel(icon);
		
		JLabel welkom = new JLabel(
				"Hello, and, again, welcome to the Aperture Science Computer-Aided Enrichment Center.");
		welkom.setFont(new Font("Sans Serif", Font.PLAIN, 40));
		
		JLabel start = new JLabel(
				"Start the application on the cellphone.");
		start.setFont(new Font("Sans Serif", Font.PLAIN, 40));

		this.add(logo);
		this.add(welkom);
		this.add(start);
		

	}
}
