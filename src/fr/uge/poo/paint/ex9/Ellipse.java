package fr.uge.poo.paint.ex9;

import fr.uge.poo.paint.ex9.Canvas.CanvasColor;

public class Ellipse extends ShapeInRect {

	public Ellipse(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(Canvas canvas, CanvasColor color) {
		canvas.drawEllipse(color, x, y, width, height);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Ellipse ellipse && x == ellipse.x && y == ellipse.y && width == ellipse.width && height == ellipse.height;
	}
}
