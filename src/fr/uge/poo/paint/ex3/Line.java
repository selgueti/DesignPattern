package fr.uge.poo.paint.ex3;

import java.awt.Graphics2D;

public record Line(int x1, int y1, int x2, int y2) implements Shape {

	@Override
	public void draw(Graphics2D graphics) {
		graphics.drawLine(x1, y1, x2, y2);
	}
}