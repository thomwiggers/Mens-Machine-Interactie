package nl.PAINt.shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

public class Ellipse extends RectangularShape {

	Ellipse2D el2d;


	public Ellipse(final double x, final double y, final double width,
			final double height, final boolean isFilled) {
		super(x, y, width, height);
		this.isFilled = isFilled;
		el2d = new Ellipse2D.Double(x, y, width, height);

	}

	@Override
	public void draw(final Graphics2D g2d) {
		el2d.setFrame(x, y, width, height);

		if (isFilled) {
			g2d.setPaint(color);
			g2d.fill(el2d);
		}
		g2d.setPaint(lineColor);
		g2d.setStroke(new BasicStroke(lineWidth));
		g2d.draw(el2d);

		if (selectionBox) {
			drawSelectionBox(g2d);
		}
	}

	@Override
	public boolean checkHit(Point point) {
		boolean hit = this.el2d.contains(point);
		logger.debug("Did " + (hit ? "" : "not ") + "hit " + this.toString());
		return hit;
	}


}
