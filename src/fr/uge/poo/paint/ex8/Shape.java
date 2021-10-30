package fr.uge.poo.paint.ex8;
import fr.uge.poo.paint.ex8.Canvas.CanvasColor;

public interface Shape {
	int sqDistance(int x , int y);
	void draw(Canvas canvas, CanvasColor color);
	int xMax();
	int yMax();
}