package nl.PAINt.shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import nl.PAINt.PanelMode;

public class Rectangle extends RectangularShape {
	private Rectangle2D rect2d;

	public Rectangle(double x, double y, double width, double height,
			boolean isFilled) {
		super(x, y, width, height);
		this.isFilled = isFilled;
		this.rect2d = new Rectangle2D.Double(x, y, width, height);
	}

	@Override
	public void draw(Graphics2D g2d) {
		rect2d.setFrame(x, y, width, height);

		g2d.setPaint(lineColor);

		if (this.isFilled) {
			g2d.setPaint(color);
			g2d.fill(rect2d);
			g2d.setPaint(lineColor);
		}
		g2d.setStroke(new BasicStroke(lineWidth));
		g2d.draw(rect2d);

		if (this.selectionBox) {
			this.drawSelectionBox(g2d);
		}
	}

	@Override
	public boolean checkHit(Point point) {
		boolean hit = this.rect2d.contains(point);
		logger.debug("Did " + (hit ? "not " : "") + "hit " + this.toString());
		return hit;
	}
	
	public PanelMode getContext(){
		return PanelMode.RECTANGLE;
	}

}
