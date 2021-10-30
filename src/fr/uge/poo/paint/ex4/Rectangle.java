package fr.uge.poo.paint.ex4;

import java.awt.Graphics2D;

public record Rectangle(int x, int y, int width, int height) implements Shape {

	@Override
	public void draw(Graphics2D graphics) {
		graphics.drawRect(x, y, width, height);
	}

	@Override
	public int sqDistance(int x, int y) {
		var x_center = this.x + width / 2;
		var y_center = this.y + height / 2;
		return (x - x_center) * (x - x_center) + (y - y_center) * (y - y_center);
	}
}
