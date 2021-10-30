package fr.uge.poo.paint.ex4;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public class EventListener {

	//	private static void callback(SimpleGraphics area, Paint paint, int x , int y) {
	//		paint.getShape().stream().min((s1, s2) -> s1.sqDistance(x, y) - s2.sqDistance(x, y)).ifPresent(System.out::println);
	//	}

		public static void listen(SimpleGraphics area, Paint paint) {
			area.waitForMouseEvents(
					(x, y) -> paint.getShape().stream().min((s1, s2) -> s1.sqDistance(x, y) - s2.sqDistance(x, y)).ifPresent(System.out::println));
		}
}
