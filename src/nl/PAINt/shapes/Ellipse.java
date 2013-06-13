package nl.PAINt.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

public class Ellipse extends RectangularShape {
	private final boolean isFilled;
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

		g2d.setPaint(Color.CYAN);

		if (isFilled) {
			g2d.fill(el2d);
		} else {
			g2d.setStroke(new BasicStroke(3.0f));
			g2d.draw(el2d);
		}

		if (selectionBox) {
			drawSelectionBox(g2d);
		}
	}

	@Override
	public boolean checkHit(final Point point) {
		return el2d.contains(point);
	}

}
