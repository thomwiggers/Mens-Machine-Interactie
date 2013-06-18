package nl.PAINt.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JLabel;

import nl.PAINt.PanelMode;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Text extends RectangularShape {
	String text;
	Graphics2D lastg2d;

	public Text(double x, double y, double width, double height, String text) {
		super(x, y, width, height);
		this.text = text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

	@Override
	public void setFrame(double x, double y, double width, double height) {
		this.x = x + width;
		this.y = y + height;
	}

	@Override
	public PanelMode getContext() {
		return PanelMode.TEXT;
	}

	@Override
	public void draw(Graphics2D g2d) {		
		if (this.selectionBox) {
			this.drawSelectionBox(g2d);
		}
		
		Font textFont = new Font("Arial", Font.PLAIN, 20);  
		FontMetrics textMetrics = g2d.getFontMetrics(textFont);  
		g2d.setColor(this.lineColor);
		g2d.setFont(textFont);  
		g2d.drawString(this.text,(int) this.x,(int) this.y + textMetrics.getHeight());
		this.lastg2d = g2d;
	}
	
	@Override
	protected void drawSelectionBox(Graphics2D g2d){
		Font textFont = new Font("Arial", Font.PLAIN, 20);  
		FontMetrics textMetrics = g2d.getFontMetrics(textFont);  
		g2d.setFont(textFont);  
		
		Rectangle2D r2d = new Rectangle2D.Double(x-3, y+5, textMetrics.stringWidth(text)+6, textMetrics.getHeight());
		g2d.setPaint(Color.BLACK);
		final float dash[] = { 7.0f };
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
		g2d.draw(r2d);
	}
	

	@Override
	public boolean checkHit(Point point) {
		Font textFont = new Font("Arial", Font.PLAIN, 20);  
		FontMetrics textMetrics = lastg2d.getFontMetrics(textFont);  
		lastg2d.setFont(textFont);  
		
		Rectangle2D r2d = new Rectangle2D.Double(x-3, y+5, textMetrics.stringWidth(text)+6, textMetrics.getHeight());
		return r2d.contains(point);
	}

}
