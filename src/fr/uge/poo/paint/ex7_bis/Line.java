package fr.uge.poo.paint.ex7_bis;

import fr.uge.poo.paint.ex7_bis.Canvas.CanvasColor;

public record Line(int x1, int y1, int x2, int y2) implements Shape {
	
	@Override
	public void draw(Canvas canvas, CanvasColor color) {
		canvas.drawLine(color, x1, y1, x2, y2);
	}

	@Override
	public int sqDistance(int x, int y) {
		var x_center = (x1 + x2) / 2;
		var y_center = (y1 + y2) / 2;
		return (x - x_center) * (x - x_center) + (y - y_center) * (y - y_center);
	}

	@Override
	public int xMax() {
		return Math.max(x1, x2);
	}

	@Override
	public int yMax() {
		return Math.max(y1, y2);
	}
}