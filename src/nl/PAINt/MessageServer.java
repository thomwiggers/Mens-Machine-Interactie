/**
 * PAaneINt 
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
	private CanvasPanel canvas;

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
					try {
						socket = server.accept();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

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
		this.canvas = window.getCanvas();
		canvas.setApiServer(this);
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

					line = line.toLowerCase();
					String[] lineParts = line.split(" ");
					switch (lineParts[0]) {
					case "jemoeder":
						System.out.println("Je moeder is een linepart");
						break;
					case "start":
						window.connected();
						break;
					case "drawmode":
						if (lineParts.length >= 2) {
							switch (lineParts[1]) {
							case "triangle":
								canvas.setMode(PanelMode.TRIANGLE);
								break;
							case "rectangle":
								canvas.setMode(PanelMode.RECTANGLE);
								break;
							case "ellipse":
								canvas.setMode(PanelMode.ELLIPSE);
								break;
							case "line":
								canvas.setMode(PanelMode.LINE);
								break;
							case "text":
								canvas.setMode(PanelMode.TEXT);
								break;
							default:
								throw new IllegalArgumentException(
										"Illegal API call, operation drawmode " + lineParts[1]
												+ " unknown");

							}
						} else
							throw new IllegalArgumentException(
									"Not enough parameters for operation drawmode");
						break;
					case "applycolor":
						if (lineParts.length < 2)
							throw new IllegalArgumentException(
									"Not enough parameters for operation applycolor");
						switch (lineParts[1]) {
						case "line":
							canvas.applyLineColor();
							break;
						case "fill":
							canvas.applyFill();
							break;
						default:
							throw new IllegalArgumentException(
									"Illegal API call, operation applycolor doesn't support "
											+ lineParts[1]);

						}
						break;
					case "unfill":
						canvas.removeFill();
						break;
					case "delete":
						canvas.deleteSelected();
						break;
					case "rotate":
						if (lineParts.length < 2) {
							throw new IllegalArgumentException(
									"not enough parameters for operation rotate");
						}
						switch (lineParts[1]) {
						case "+":
							canvas.rotateSelected(10);
							break;
						case "-":
							canvas.rotateSelected(-10);
							break;
						default:
							throw new IllegalArgumentException(
									"Illegal parameter for operation rotate: " + lineParts[1]);
						}
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

	/**
	 * @param mode
	 */
	public void sendContext(PanelMode mode) {
		switch (mode) {
		case DELETE:
			break;
		case ELLIPSE:
		case ELL_FILLED:
			this.outqueue.add("context ellipse");
			break;
		case LINE:
			this.outqueue.add("context line");
			break;
		case MOVE:
		case RESIZE:
			this.outqueue.add("context select");
			break;
		case RECTANGLE:
		case RECT_FILLED:
			this.outqueue.add("context rectangle");
			break;
		case TEXT:
			this.outqueue.add("context text");
			break;
		case TRIANGLE:
			this.outqueue.add("context triangle");
			break;
		}

	}

}
