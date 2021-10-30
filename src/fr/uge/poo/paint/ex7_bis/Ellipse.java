package fr.uge.poo.paint.ex7_bis;

import fr.uge.poo.paint.ex7_bis.Canvas.CanvasColor;

public record Ellipse(int x , int y, int width, int height) implements Shape, Shape2D{

	@Override
	public void draw(Canvas canvas, CanvasColor color) {
		canvas.drawEllipse(color, x, y, width, height);
	}

	@Override
	public int sqDistance(int x, int y) {
		return Shape2D.sqDistanceFromShape(x, y, this.x, this.y, width, height);
	}

	@Override
	public int xMax() {
		return Shape2D.x_max2D(x, width);
	}

	@Override
	public int yMax() {
		return Shape2D.y_max2D(y, height);
	}
}
