package fr.uge.poo.paint.ex3;

import java.awt.Graphics2D;

public record Rectangle(int x, int y, int width, int height) implements Shape {
	
	@Override
	public void draw(Graphics2D graphics) {
		graphics.drawRect(x, y, width, height);
	}

}
