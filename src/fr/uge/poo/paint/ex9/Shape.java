package fr.uge.poo.paint.ex9;
import fr.uge.poo.paint.ex9.Canvas.CanvasColor;

public interface Shape {
	
	void draw(Canvas canvas, CanvasColor color);
	
	double distance2(int x, int y);
	
	static double distance(int x1, int y1, int x2, int y2) {
		return (x1 - x2) * (x1 - x2) + (y1- y2) * (y1- y2);
	}
		
	WindowSize minWindowSize();
	
}