package nl.PAINt.shapes;

import java.awt.Graphics2D;
import java.awt.Point;

public interface Shape {
	public void draw(Graphics2D g2d);
	public boolean checkHit(Point point);
	public void setSelectionBox(final boolean bool);
	public void move(final double dx, final double dy);
	public boolean lockCorner(Point point);
	public void unlockCorner();
}
