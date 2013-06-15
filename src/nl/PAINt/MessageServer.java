/**
 * PAINt 
 * 
 * Created for the course intro Human-Computer Interaction at the
 * Radboud Universiteit Nijmegen
 * 
 * 2013
 */
package nl.PAINt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Luuk Scholten & Thom Wiggers
 * 
 */
public class MessageServer {

	ServerSocket server;
	Socket socket;

	Queue<String> outqueue = new LinkedList<>();

	/**
	 * 
	 */
	public MessageServer(final MainWindow window) {
		try {
			server = new ServerSocket(2222);
			server.setSoTimeout(0);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					socket = createSocket();
					System.out.println(socket.getInetAddress());
					try {
						BufferedReader reader = new BufferedReader(new InputStreamReader(
								socket.getInputStream()));

						BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
								socket.getOutputStream()));

						StreamReader sr = new StreamReader(reader, window);

						Thread t1 = new Thread(sr);
						t1.setDaemon(true);
						t1.start();

						StreamWriter sw = new StreamWriter(writer, outqueue);

						Thread t2 = new Thread(sw);
						t2.setDaemon(true);
						t2.start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t1.setDaemon(true);
		t1.start();
	}

	/**
	 * Anders stomme problemen met final.
	 * 
	 * @see http://stackoverflow.com/questions/5858250
	 * 
	 * @return Accept socket
	 */
	public Socket createSocket() {
		try {
			return server.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return socket;

	}

	class StreamReader implements Runnable {

		private BufferedReader reader = null;
		private MainWindow window = null;

		public StreamReader(BufferedReader inputReader, MainWindow window) {
			reader = inputReader;
			this.window = window;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					System.out.println("received line " + line);
					if(line.equals("start")){
						window.connected();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	class StreamWriter implements Runnable {

		BufferedWriter writer;
		Queue<String> queue;

		public StreamWriter(BufferedWriter outputWriter, Queue<String> queue) {
			writer = outputWriter;
			this.queue = queue;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub\
			try {
				while (true) {
					Thread.sleep(50);

					String line = queue.poll();
					if (line != null) {
						try {
							writer.write(line + "\n");
							writer.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
