package fr.uge.poo.paint.ex4;

import java.awt.Graphics2D;

public record Line(int x1, int y1, int x2, int y2) implements Shape {

	@Override
	public void draw(Graphics2D graphics) {
		graphics.drawLine(x1, y1, x2, y2);
	}

	@Override
	public int sqDistance(int x, int y) {
		var x_center = (x1 + x2) / 2;
		var y_center = (y1 + y2) / 2;
		return (x - x_center) * (x - x_center) + (y - y_center) * (y - y_center);
	}
}