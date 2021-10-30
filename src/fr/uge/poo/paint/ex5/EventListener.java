package fr.uge.poo.paint.ex5;

import java.awt.Color;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public class EventListener {

	public static void highlightsOnOrangeTheNearestOnCLick(SimpleGraphics area, Paint paint) {
		area.waitForMouseEvents(
				(x, y) -> paint.getShape().stream().min((s1, s2) -> s1.sqDistance(x, y) - s2.sqDistance(x, y))
				.ifPresent(
						shape -> area.render(
								g -> {
									paint.drawAll(g);
									g.setColor(Color.ORANGE);
									shape.draw(g);
								})));
	}
}
