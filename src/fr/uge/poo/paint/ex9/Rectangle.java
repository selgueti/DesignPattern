package fr.uge.poo.paint.ex9;

import fr.uge.poo.paint.ex9.Canvas.CanvasColor;

public class Rectangle extends ShapeInRect {

	public Rectangle(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(Canvas canvas, CanvasColor color) {
		canvas.drawRect(color, x, y, width, height);		
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Rectangle rect && x == rect.x && y == rect.y && width == rect.width && height == rect.height;
	}
}
