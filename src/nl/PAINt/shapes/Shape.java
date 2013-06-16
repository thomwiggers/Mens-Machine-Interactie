package nl.PAINt.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import nl.PAINt.PanelMode;

public interface Shape {
	public void draw(Graphics2D g2d);

	public boolean checkHit(Point point);

	public void setSelectionBox(final boolean bool);

	public void move(final double dx, final double dy);

	public boolean lockCorner(Point point);

	public void unlockCorner();

	/**
	 * @param color
	 */
	public void setColor(Color color);

	/**
	 * @param color
	 */
	public void setLineColor(Color color);

	/**
	 * @param lineWidth
	 */
	public void setLineWidth(float lineWidth);

	/**
	 * @param b
	 */
	public void setFilled(boolean b);
	
	public PanelMode getContext();
}
