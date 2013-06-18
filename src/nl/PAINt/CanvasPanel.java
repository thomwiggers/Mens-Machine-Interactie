package nl.PAINt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import nl.PAINt.shapes.Ellipse;
import nl.PAINt.shapes.Line;
import nl.PAINt.shapes.Rectangle;
import nl.PAINt.shapes.RectangularShape;
import nl.PAINt.shapes.Shape;
import nl.PAINt.shapes.Text;
import nl.PAINt.shapes.Triangle;

import org.apache.log4j.Logger;

public class CanvasPanel extends JPanel {

	/**
     * 
     */
	private static final long serialVersionUID = 8598546562221302439L;
	private final MouseInputListener drawListener;
	private final MouseInputListener modeListener;
	private final MouseInputListener resizeListener;

	private final ArrayList<Shape> displayList;
	private PanelMode mode;
	private Color color = Color.BLACK;
	private Color lineColor = Color.black;
	private float lineWidth = 3.0f;
	private MessageServer apiServer = null;
	private Color activeColor;

	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * @param apiServer
	 *          the apiServer to set
	 */
	public void setApiServer(MessageServer apiServer) {
		this.apiServer = apiServer;
	}

	public CanvasPanel() {
		
		setPreferredSize(new Dimension(1280, 900));
		setBackground(Color.WHITE);
		mode = PanelMode.RECTANGLE;
		displayList = new ArrayList<>();
		drawListener = new DrawModeListener();
		modeListener = new ModeListener();
		resizeListener = new SelectListener();

		setFocusable(true);
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);

		final Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);

		for (final Shape shape : displayList) {
			shape.draw(g2d);
		}
	}

	public void setMode(final PanelMode pm) {
		logger.info("Setting mode " + pm.name());

		logger.debug("removing mouselisteners");
		for (final MouseListener lsnr : getMouseListeners()) {
			removeMouseListener(lsnr);
		}
		logger.debug("removing mousemotionlisteners");
		for (final MouseMotionListener lsnr : getMouseMotionListeners()) {
			removeMouseMotionListener(lsnr);
		}

		for (final Shape s : displayList) {
			s.setSelectionBox(false);
		}
		

		this.repaint();

		if (pm == PanelMode.DELETE || pm == PanelMode.MOVE) {
			logger.debug("Adding modeListener");
			addMouseListener(modeListener);
			addMouseMotionListener(modeListener);
		} else if (pm == PanelMode.SELECT) {
			logger.debug("Adding resizelistener");
			addMouseListener(resizeListener);
			addMouseMotionListener(resizeListener);
		} else {
			logger.debug("adding drawlistener");
			addMouseListener(drawListener);
			addMouseMotionListener(drawListener);
			apiServer.sendContext(PanelMode.NONE);
		}
		mode = pm;

	}

	private class DrawModeListener implements MouseInputListener {
		private Point startPoint;
		private Shape currentlyDrawing = null;
		private boolean triangleLastPhase = false;
		private Logger logger = Logger.getLogger(DrawModeListener.class);

		@Override
		public void mouseClicked(final MouseEvent arg0) {
			logger.debug("Mouse clicked");
		}

		@Override
		public void mouseEntered(final MouseEvent arg0) {
			logger.debug("mouse entered object");
		}

		@Override
		public void mouseExited(final MouseEvent arg0) {
			logger.debug("mouse exited object");
		}

		@Override
		public void mousePressed(final MouseEvent arg0) {
			logger.info("Mouse pressed ");
			if (currentlyDrawing != null) {
				if (currentlyDrawing instanceof Triangle) {
					currentlyDrawing.setSelectionBox(false);
					currentlyDrawing = null;
					triangleLastPhase = false;
				}

				CanvasPanel.this.repaint();
				return;
			}

			startPoint = arg0.getPoint();

			switch (mode) {
			case ELLIPSE:
				logger.info("Currently drawing " + mode.name());
				currentlyDrawing = new Ellipse(startPoint.x, startPoint.y, 0, 0, false);
				break;
			case ELL_FILLED:
				logger.info("Currently drawing " + mode.name());
				currentlyDrawing = new Ellipse(startPoint.x, startPoint.y, 0, 0, true);
				break;
			case RECTANGLE:
				logger.info("Currently drawing " + mode.name());
				currentlyDrawing = new Rectangle(startPoint.x, startPoint.y, 0, 0,
						false);
				break;
			case RECT_FILLED:
				logger.info("Currently drawing " + mode.name());
				currentlyDrawing = new Rectangle(startPoint.x, startPoint.y, 0, 0, true);
				break;
			case TRIANGLE:
				logger.info("Currently drawing " + mode.name());
				currentlyDrawing = new Triangle(arg0.getPoint(), arg0.getPoint(),
						arg0.getPoint(), false);
				break;
			case LINE:
				logger.info("Currently drawing Line");
				currentlyDrawing = new Line(arg0.getPoint(), arg0.getPoint());
				break;
			case TEXT:
				logger.info("Currently drawing text");
				currentlyDrawing = new Text(startPoint.x, startPoint.y, 0, 0, "[PLACEHOLDER]");
				break;
			case DELETE:
			case MOVE:
			case SELECT:
			default:
				logger.error("unsupported mode");
				throw new IllegalArgumentException("Operation " + mode.toString()
						+ " not supported");
			}
			currentlyDrawing.setColor(color);
			currentlyDrawing.setLineColor(lineColor);
			currentlyDrawing.setLineWidth(lineWidth);

			displayList.add(currentlyDrawing);
			currentlyDrawing.setSelectionBox(true);
			CanvasPanel.this.repaint();

		}

		@Override
		public void mouseReleased(final MouseEvent arg0) {
			logger.debug("Mouse released");
			if (mode != PanelMode.TRIANGLE) {
				currentlyDrawing.setSelectionBox(false);
				if(currentlyDrawing instanceof Text){
					String str = JOptionPane.showInputDialog(null, "Vul uw text in:", 
							"", 1);
					((Text) currentlyDrawing).setText(str);
				}
				currentlyDrawing = null;
			} else if (currentlyDrawing != null) {
				logger.info("second triangle phase entered");
				triangleLastPhase = true;
			}

			CanvasPanel.this.repaint();
		}

		@Override
		public void mouseDragged(final MouseEvent arg0) {
			if (currentlyDrawing == null)
				return;
			final Point newPoint = arg0.getPoint();

			if (mode == PanelMode.TRIANGLE && !triangleLastPhase) {
				((Triangle) currentlyDrawing).setSecondPoint(arg0.getPoint());
				((Triangle) currentlyDrawing).setLastPoint(arg0.getPoint());
			} else if (currentlyDrawing instanceof RectangularShape) {
				((RectangularShape) currentlyDrawing).setFrame(startPoint.x,
						startPoint.y, newPoint.x - startPoint.x, newPoint.y - startPoint.y);
			} else if (mode == PanelMode.LINE) {
				((Line) currentlyDrawing).setSecondPoint(newPoint);
			}

			CanvasPanel.this.repaint();
		}

		@Override
		public void mouseMoved(final MouseEvent arg0) {
			if (mode == PanelMode.TRIANGLE && currentlyDrawing != null) {
				((Triangle) currentlyDrawing).setLastPoint(arg0.getPoint());
				CanvasPanel.this.repaint();
			}

		}
	}

	private class ModeListener implements MouseInputListener {
		private Point movePoint;
		private Shape currentlyMoving;
		private Logger logger = Logger.getLogger(getClass());

		@Override
		public void mouseClicked(final MouseEvent arg0) {
			logger.debug("Mouse clicked");
		}

		@Override
		public void mouseEntered(final MouseEvent arg0) {
			logger.debug("Mouse entered");
		}

		@Override
		public void mouseExited(final MouseEvent arg0) {
			logger.debug("mouse exited");
		}

		@Override
		public void mousePressed(final MouseEvent arg0) {
			logger.info("Mouse Pressed");
			if (mode == PanelMode.MOVE) {
				for (final Shape shape : displayList) {
					if (shape.checkHit(arg0.getPoint())) {
						logger.info("Starting to move " + shape.toString());
						movePoint = arg0.getPoint();
						currentlyMoving = shape;
						currentlyMoving.setSelectionBox(true);
						CanvasPanel.this.repaint();
						break;
					}
				}
			}

			if (mode == PanelMode.DELETE) {
				for (int i = displayList.size() - 1; i >= 0; i--) {
					final Shape shape = displayList.get(i);
					if (shape.checkHit(arg0.getPoint())) {
						logger.info("Removing object " + shape.toString());
						displayList.remove(shape);
						break;
					}
				}

			}
			CanvasPanel.this.repaint();

		}

		@Override
		public void mouseReleased(final MouseEvent arg0) {
			logger.debug("released mouse");
			movePoint = null;
			if (currentlyMoving != null) {
				logger.info("Stopped moving " + currentlyMoving.toString());
				currentlyMoving.setSelectionBox(false);
				CanvasPanel.this.repaint();
			}
			currentlyMoving = null;

		}

		@Override
		public void mouseDragged(final MouseEvent arg0) {
			if (movePoint == null && currentlyMoving == null)
				return;
			else {
				final double dx = arg0.getPoint().x - movePoint.x;
				final double dy = arg0.getPoint().y - movePoint.y;
				movePoint = arg0.getPoint();
				currentlyMoving.move(dx, dy);
				CanvasPanel.this.repaint();
			}

		}

		@Override
		public void mouseMoved(final MouseEvent arg0) {
		}

	}

	private class SelectListener implements MouseInputListener {
		protected Shape selected = null;
		private Point startPoint;
		private Point movePoint;
		private Logger logger = Logger.getLogger(SelectListener.class);

		@Override
		public void mouseClicked(final MouseEvent arg0) {
			if(arg0.getClickCount()==2 && selected != null && selected instanceof Text){
				String str = JOptionPane.showInputDialog(null, "Vul uw text in:", 
						"", 1);
				((Text) selected).setText(str);
				
				CanvasPanel.this.repaint();
	        }
		}


		@Override
		public void mouseEntered(final MouseEvent arg0) {

		}

		@Override
		public void mouseExited(final MouseEvent arg0) {
		}

		@Override
		public void mousePressed(final MouseEvent arg0) {
			logger.debug("Pressed mouse");
			// nieuw dingetje selecten
			if (selected == null || !selected.lockCorner(arg0.getPoint())) {
				if (selected != null) {
					logger.info("Deselected " + selected.toString());
					selected.setSelectionBox(false);
					selected = null;
				}

				for (int i = displayList.size() - 1; i >= 0; i--) {
					final Shape shape = displayList.get(i);
					if (shape.checkHit(arg0.getPoint())) {
						selected = shape;
						logger.info("Selected " + shape.toString());
						CanvasPanel.this.apiServer.sendContext(selected.getContext());
						selected.setSelectionBox(true);
						break;
					}
				}

				if (selected == null) {
					CanvasPanel.this.apiServer.sendContext(PanelMode.NONE);
					repaint();
					return;
				}
			}
				

			// meteen operaties uitvoeren
			if (selected.lockCorner(arg0.getPoint())) {
				startPoint = arg0.getPoint();
			} else if (selected.checkHit(arg0.getPoint())) {
				movePoint = arg0.getPoint();
			}

			CanvasPanel.this.repaint();

		}

		@Override
		public void mouseReleased(final MouseEvent arg0) {
			logger.debug("Mouse released");
			startPoint = null;
			movePoint = null;
			if (selected != null) {
				selected.unlockCorner();
			}
		}

		@Override
		public void mouseDragged(final MouseEvent arg0) {
			if (startPoint != null && selected != null) {
				if (selected instanceof Triangle) {
					((Triangle) selected).moveCorner(arg0.getPoint());
				} else if (selected instanceof Line) {
					((Line) selected).moveCorner(arg0.getPoint());
				} else {
					((RectangularShape) selected).setFrameFromCorner(startPoint,
							arg0.getPoint());
				}

				CanvasPanel.this.repaint();
			} else if (movePoint != null && selected != null) {
				final double dx = arg0.getPoint().x - movePoint.x;
				final double dy = arg0.getPoint().y - movePoint.y;
				movePoint = arg0.getPoint();
				selected.move(dx, dy);
				CanvasPanel.this.repaint();
			}
		}

		@Override
		public void mouseMoved(final MouseEvent arg0) {

		}

	}

	/**
	 * @param color
	 */
	public void setColor(Color color) {
		logger.debug("Selected color: " + color.toString());
		this.color = color;
		if (this.mode == PanelMode.SELECT && resizeListener instanceof SelectListener) {
			if (((SelectListener) resizeListener).selected != null) {
				((SelectListener) resizeListener).selected.setColor(color);
				repaint();
			}
		}
	}

	/**
	 * @param color2
	 */
	public void setLineColor(Color color) {
		logger.debug("selected line color " + color.toString());
		this.lineColor = color;
		if (this.mode == PanelMode.SELECT && resizeListener instanceof SelectListener) {
			if (((SelectListener) resizeListener).selected != null) {
				((SelectListener) resizeListener).selected.setLineColor(lineColor);
				repaint();
			}
		}

	}

	/**
	 * @param f
	 */
	public void setLineWidth(float f) {
		logger.debug("Selected line width: " + f);
		lineWidth = f;
		if (this.mode == PanelMode.SELECT && resizeListener instanceof SelectListener) {
			SelectListener rsl = (SelectListener) resizeListener;
			if (rsl.selected != null) {
				rsl.selected.setLineWidth(f);
				repaint();
			}
		}
	}

	/**
	 * 
	 */
	public void applyLineColor() {
		logger.debug("Trying to apply line color ");
		SelectListener rs = getResizeListener();
		if (this.mode == PanelMode.SELECT && rs.selected != null) {
			rs.selected.setLineColor(activeColor);
			repaint();
		}
	}

	/**
	 * 
	 */
	public void applyFill() {
		logger.debug("Trying to apply fill ");
		SelectListener rs = getResizeListener();
		if (this.mode == PanelMode.SELECT &&  rs.selected != null) {
			rs.selected.setColor(activeColor);
			rs.selected.setFilled(true);
			repaint();
		}
	}

	/**
	 * 
	 */
	public void toggleFill() {
		logger.debug("Trying to toggle fill ");
		SelectListener rs = getResizeListener();
		if (this.mode == PanelMode.SELECT &&  rs.selected != null) {
			rs.selected.setFilled(!rs.selected.getFilled());
			repaint();
		}

	}

	/**
	 * 
	 */
	public void deleteSelected() {
		logger.debug("Trying to delete shape");
		SelectListener rs = getResizeListener();
		if (this.mode == PanelMode.SELECT && rs.selected != null) {
			displayList.remove(rs.selected);
			logger.info("Deleted " + rs.selected.toString());
			rs.selected = null;
			repaint();
		}

	}

	/**
	 * @param i
	 */
	public void rotateSelected(int i) {
		logger.debug("rotating selected");
		SelectListener rs = getResizeListener();
		if (this.mode == PanelMode.SELECT && rs.selected != null) {
			rs.selected.rotate(i);
		}
	}

	/**
	 * 
	 */
	public void moveSelectedForward() {
		logger.debug("Trying to move selected forward");
		SelectListener rs = getResizeListener();
		if (this.mode == PanelMode.SELECT && rs.selected != null) {
			int index = this.displayList.indexOf(rs.selected);
			if (index > 0) {
				logger.info("moving shape forwards");
				swapDisplayList(index - 1, index);
				repaint();
			}
		}
	}

	/**
	 * 
	 */
	public void moveSelectedBackward() {
		SelectListener rs = getResizeListener();
		if (this.mode == PanelMode.SELECT && rs.selected != null) {
			int index = this.displayList.indexOf(rs.selected);
			if (displayList.size() - 1 > index && index != -1) {
				logger.info("moving shape backwards");
				swapDisplayList(index, index + 1);
				repaint();
			}
		}


	}

	public SelectListener getResizeListener() {
		if (resizeListener instanceof SelectListener)
			return (SelectListener) resizeListener;
		else
			return null;
	}

	private void swapDisplayList(int a, int b) {
		Shape temp = displayList.get(a);
		displayList.set(a, displayList.get(b));
		displayList.set(b, temp);
	}

	/**
	 * @param color
	 */
	public void setActiveColor(Color color) {
		logger.info("setting active color to " + color.toString());
		this.activeColor = color;
		
	}

	public void repaint() {
		super.repaint();
	}
}
