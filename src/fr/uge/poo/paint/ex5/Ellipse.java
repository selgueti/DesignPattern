package fr.uge.poo.paint.ex5;

import java.awt.Graphics2D;

public record Ellipse(int x , int y, int width, int height) implements Shape, Shape2D{

	@Override
	public void draw(Graphics2D graphics) {
		graphics.drawOval(x, y, width, height);
	}

	@Override
	public int sqDistance(int x, int y) {
		return Shape2D.sqDistanceFromShape(x, y, this.x, this.y, width, height);
	}
}
