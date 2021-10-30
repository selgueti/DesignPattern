package fr.uge.poo.paint.ex8;

import java.util.List;

import fr.uge.poo.paint.ex8.Canvas.CanvasColor;

public class Paint {

	public static void drawAll(Canvas canvas, List<Shape> shapes, CanvasColor color) {
		canvas.clear(CanvasColor.WHITE);
		shapes.forEach(s -> s.draw(canvas, color));
	}
	
	public static void drawAll(Canvas canvas, List<Shape> shapes, Shape selection, CanvasColor color, CanvasColor colorSelect) {
		canvas.clear(CanvasColor.WHITE);
		shapes.forEach(s -> {
			if(s.equals(selection)) {
				s.draw(canvas, colorSelect);
			}else {
				s.draw(canvas, color);
			}
			});
	}
}