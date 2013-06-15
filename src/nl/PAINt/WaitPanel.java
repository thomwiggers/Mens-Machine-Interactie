package nl.PAINt;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WaitPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2180416319590058558L;

	public WaitPanel() {
		setPreferredSize(new Dimension(1440, 900));

		
		this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
		JLabel welkom = new JLabel("Welkom Subject #1, start deze applicatie via de Nexus met ip::");
		welkom.setFont(new Font("Sans Serif", Font.PLAIN, 40));

		String local_addr = this.getLocalV4Address();
		JLabel host = new JLabel(local_addr);
		host.setFont(new Font("Sans Serif", Font.PLAIN, 30));
		
		
		this.add(welkom, gbc);
		
		this.add(host, gbc);
	}

	private String getLocalV4Address(){
		Enumeration<NetworkInterface> interfaces = null;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (interfaces.hasMoreElements()) {
			NetworkInterface current = interfaces.nextElement();
			try {
				if (!current.isUp() || current.isLoopback()
						|| current.isVirtual())
					continue;
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Enumeration<InetAddress> addresses = current.getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress current_addr = addresses.nextElement();
				if (current_addr.isLoopbackAddress())
					continue;
				if(current_addr instanceof Inet4Address){
					return current_addr.getHostAddress();
				}
			}
		}
		
		return "";
	}
}
