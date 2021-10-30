package fr.uge.poo.paint.ex8;

import java.util.List;

import fr.uge.poo.paint.ex8.Canvas.CanvasColor;

public class EventListener {

	public static void highlightsOnOrangeTheNearestOnCLick(Canvas area, List<Shape> shapes) {
		
		area.waitForMouseClick((x, y) -> {
			shapes.stream().min((s1, s2) -> s1.sqDistance(x, y) - s2.sqDistance(x, y))
			.ifPresent(
					shape -> {
						//area.clear(CanvasColor.WHITE);
						Paint.drawAll(area, shapes, shape, CanvasColor.BLACK, CanvasColor.ORANGE);
					}
			);
		});
	}
}