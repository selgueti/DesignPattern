package fr.uge.poo.paint.ex9;

import fr.uge.poo.paint.ex9.Canvas.CanvasColor;

public class Rectangle extends ShapeInRect {

	public Rectangle(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(Canvas canvas, CanvasColor color) {
		canvas.drawRect(color, getX(), getY(), getWidth(), getHeight());		
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Rectangle rect && getX() == rect.getX() && getY() == rect.getY() && getWidth() == rect.getWidth() && getHeight() == rect.getHeight();
	}
}
