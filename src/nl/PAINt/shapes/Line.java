/**
 * PAINt 
 * 
 * Created for the course intro Human-Computer Interaction at the
 * Radboud Universiteit Nijmegen
 * 
 * 2013
 */
package nl.PAINt.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import nl.PAINt.PanelMode;

/**
 * @author Luuk Scholten & Thom Wiggers
 *
 */
public class Line implements Shape {

	private Point p1;
	private Point p2;

	/**
	 * 
	 */
	public Line(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	/* (non-Javadoc)
	 * @see nl.PAINt.shapes.Shape#draw(java.awt.Graphics2D)
	 */
	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawLine(p1.x, p1.y, p2.x, p2.y);

	}

	/* (non-Javadoc)
	 * @see nl.PAINt.shapes.Shape#checkHit(java.awt.Point)
	 */
	@Override
	public boolean checkHit(Point point) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nl.PAINt.shapes.Shape#setSelectionBox(boolean)
	 */
	@Override
	public void setSelectionBox(boolean bool) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nl.PAINt.shapes.Shape#move(double, double)
	 */
	@Override
	public void move(double dx, double dy) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nl.PAINt.shapes.Shape#lockCorner(java.awt.Point)
	 */
	@Override
	public boolean lockCorner(Point point) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nl.PAINt.shapes.Shape#unlockCorner()
	 */
	@Override
	public void unlockCorner() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nl.PAINt.shapes.Shape#setColor(java.awt.Color)
	 */
	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nl.PAINt.shapes.Shape#setLineColor(java.awt.Color)
	 */
	@Override
	public void setLineColor(Color color) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nl.PAINt.shapes.Shape#setLineWidth(float)
	 */
	@Override
	public void setLineWidth(float lineWidth) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nl.PAINt.shapes.Shape#setFilled(boolean)
	 */
	@Override
	public void setFilled(boolean b) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nl.PAINt.shapes.Shape#getContext()
	 */
	@Override
	public PanelMode getContext() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nl.PAINt.shapes.Shape#rotate(int)
	 */
	@Override
	public void rotate(int i) {
		// TODO Auto-generated method stub

	}

}
