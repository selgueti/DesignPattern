package fr.uge.poo.paint.ex6;

import java.awt.Color;
import java.util.List;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public class EventListener {

	public static void highlightsOnOrangeTheNearestOnCLick(SimpleGraphics area, List<Shape> shapes) {
		area.waitForMouseEvents(
				(x, y) -> shapes.stream().min((s1, s2) -> s1.sqDistance(x, y) - s2.sqDistance(x, y))
				.ifPresent(
						shape -> {
							area.clear(Color.WHITE);
							area.render(g -> Paint.drawAll(g, shapes));
							area.render(g -> {
								g.setColor(Color.ORANGE);
								shape.draw(g);});

						})
				);
	}

}
