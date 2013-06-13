package nl.PAINt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import nl.PAINt.shapes.Ellipse;
import nl.PAINt.shapes.Rectangle;
import nl.PAINt.shapes.RectangularShape;
import nl.PAINt.shapes.Shape;
import nl.PAINt.shapes.Triangle;

public class CanvasPanel extends JPanel {

	private final MouseInputListener drawListener;
	private final MouseInputListener modeListener;
	private final MouseInputListener resizeListener;

	private final ArrayList<Shape> displayList;
	private PanelMode mode;
	private static final long serialVersionUID = 1L;

	public CanvasPanel() {
		setPreferredSize(new Dimension(1280, 900));
		this.setBackground(Color.WHITE);
		mode = PanelMode.RECT;
		displayList = new ArrayList<Shape>();
		drawListener = new DrawModeListener();
		modeListener = new ModeListener();
		resizeListener = new ResizeListener();

		setFocusable(true);
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);

		final Graphics2D g2d = (Graphics2D) g;

		for (final Shape shape : displayList) {
			shape.draw(g2d);
		}
	}

	public void setMode(final PanelMode pm) {
		for (final MouseListener lsnr : getMouseListeners()) {
			removeMouseListener(lsnr);
		}
		for (final MouseMotionListener lsnr : getMouseMotionListeners()) {
			removeMouseMotionListener(lsnr);
		}

		for (final Shape s : displayList) {
			s.setSelectionBox(false);
		}
		this.repaint();

		if (pm == PanelMode.DELETE || pm == PanelMode.MOVE) {
			addMouseListener(modeListener);
			addMouseMotionListener(modeListener);
		} else if (pm == PanelMode.RESIZE) {
			addMouseListener(resizeListener);
			addMouseMotionListener(resizeListener);
		} else {

			addMouseListener(drawListener);
			addMouseMotionListener(drawListener);
		}
		mode = pm;
	}

	private class DrawModeListener implements MouseInputListener {
		private Point startPoint;
		private Shape currentlyDrawing = null;
		private boolean triangleLastPhase = false;

		@Override
		public void mouseClicked(final MouseEvent arg0) {

		}

		@Override
		public void mouseEntered(final MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(final MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(final MouseEvent arg0) {
			if(this.currentlyDrawing != null){
				if(this.currentlyDrawing instanceof Triangle){	
					this.currentlyDrawing.setSelectionBox(false);
					this.currentlyDrawing = null;
					this.triangleLastPhase = false;
				}
				
				CanvasPanel.this.repaint();
				return;
			}
			
			startPoint = arg0.getPoint();

			switch (mode) {
			case ELL:
				currentlyDrawing = new Ellipse(startPoint.x, startPoint.y, 0,
						0, false);
				break;
			case ELL_FILLED:
				currentlyDrawing = new Ellipse(startPoint.x, startPoint.y, 0,
						0, true);
				break;
			case RECT:
				currentlyDrawing = new Rectangle(startPoint.x, startPoint.y, 0,
						0, false);
				break;
			case TRIANGLE:
				currentlyDrawing = new Triangle(arg0.getPoint(), arg0.getPoint(), arg0.getPoint());
				break;
			case DELETE:
			case MOVE:
			case RESIZE:
			default:
				throw new IllegalArgumentException("Operation "
						+ mode.toString() + " not supported");
			}

			displayList.add(currentlyDrawing);
			currentlyDrawing.setSelectionBox(true);
			CanvasPanel.this.repaint();

		}

		@Override
		public void mouseReleased(final MouseEvent arg0) {
			if(mode != PanelMode.TRIANGLE){
				currentlyDrawing.setSelectionBox(false);
				currentlyDrawing = null;
			}else if(currentlyDrawing != null){
				this.triangleLastPhase = true;
			}
			
			CanvasPanel.this.repaint();
		}

		@Override
		public void mouseDragged(final MouseEvent arg0) {
			if (currentlyDrawing == null)
				return;
			final Point newPoint = arg0.getPoint();

			if(mode == PanelMode.TRIANGLE && !this.triangleLastPhase){
				((Triangle) currentlyDrawing).setSecondPoint(arg0.getPoint());
				((Triangle) currentlyDrawing).setLastPoint(arg0.getPoint());
			}else if(mode != PanelMode.TRIANGLE){
				((RectangularShape) currentlyDrawing).setFrame(startPoint.x,
						startPoint.y, newPoint.x - startPoint.x, newPoint.y
								- startPoint.y);

			}
			
			CanvasPanel.this.repaint();
		}

		@Override
		public void mouseMoved(final MouseEvent arg0) {
			if(mode == PanelMode.TRIANGLE && this.currentlyDrawing != null){
				((Triangle)this.currentlyDrawing).setLastPoint(arg0.getPoint());
				CanvasPanel.this.repaint();
			}

		}
	}

	private class ModeListener implements MouseInputListener {
		private Point movePoint;
		private Shape currentlyMoving;

		@Override
		public void mouseClicked(final MouseEvent arg0) {

		}

		@Override
		public void mouseEntered(final MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(final MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(final MouseEvent arg0) {
			if (mode == PanelMode.MOVE) {
				for (final Shape shape : displayList) {
					if (shape.checkHit(arg0.getPoint())) {
						movePoint = arg0.getPoint();
						currentlyMoving = shape;
						currentlyMoving.setSelectionBox(true);
						CanvasPanel.this.repaint();
						break;
					}
				}
			}
			
			if (mode == PanelMode.DELETE) {
				for(int i = displayList.size() - 1; i >= 0; i--){
					Shape shape = displayList.get(i);
					if (shape.checkHit(arg0.getPoint())) {
						displayList.remove(shape);
						break;
					}
				}
				
			}
			CanvasPanel.this.repaint();

		}

		@Override
		public void mouseReleased(final MouseEvent arg0) {
			movePoint = null;
			if (currentlyMoving != null) {
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

	private class ResizeListener implements MouseInputListener {
		private Shape selected = null;
		private Point startPoint;
		private Point movePoint;

		@Override
		public void mouseClicked(final MouseEvent arg0) {
			
		}

		@Override
		public void mouseEntered(final MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(final MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(final MouseEvent arg0) {
			// nieuw dingetje selecten
			if (selected == null || !selected.lockCorner(arg0.getPoint())) {
				if (selected != null) {
					selected.setSelectionBox(false);
				}
				
				for(int i = displayList.size() - 1; i >= 0; i--){
					Shape shape = displayList.get(i);	
					if (shape.checkHit(arg0.getPoint())) {
						selected = shape;
						selected.setSelectionBox(true);
						break;
					}
				}
			}
			
			if(selected == null){
				return;
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
			startPoint = null;
			movePoint = null;
			if (selected != null) {
				selected.unlockCorner();
			}
		}

		@Override
		public void mouseDragged(final MouseEvent arg0) {
			if (startPoint != null && selected != null) {
				if(selected instanceof Triangle){
					((Triangle)selected).moveCorner(arg0.getPoint());
				}else{
					((RectangularShape)selected).setFrameFromCorner(startPoint, arg0.getPoint());
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
			// TODO Auto-generated method stub

		}

	}
}
