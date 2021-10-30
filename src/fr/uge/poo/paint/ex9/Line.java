package fr.uge.poo.paint.ex9;

import fr.uge.poo.paint.ex9.Canvas.CanvasColor;

public record Line(int x1, int y1, int x2, int y2) implements Shape {

	@Override
	public void draw(Canvas canvas, CanvasColor color) {
		canvas.drawLine(color, x1, y1, x2, y2);
	}

	@Override
	public double distance2(int x, int y) {
		return Shape.distance(x, y, (this.x1 + this.x2) / 2, (this.y1 + this.y2) / 2);
	}

	@Override
	public WindowSize minWindowSize() {
		return new WindowSize(Math.max(x1, x2), Math.max(y1, y2));
	}

}