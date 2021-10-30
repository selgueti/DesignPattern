package fr.uge.poo.paint.ex6;

import java.awt.Graphics2D;
import java.util.Objects;

public record Rectangle(int x, int y, int width, int height) implements Shape, Shape2D{
	
	@Override
	public void draw(Graphics2D graphics) {
		Objects.requireNonNull(graphics);
		graphics.drawRect(x, y, width, height);
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
