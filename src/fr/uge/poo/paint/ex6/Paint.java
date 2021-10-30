package fr.uge.poo.paint.ex6;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

public class Paint {

	public static void drawAll(Graphics2D graphics, List<Shape> shapes) {
		graphics.setColor(Color.BLACK);
		shapes.forEach(d -> d.draw(graphics));
	}

}