package fr.uge.poo.paint.ex7_bis;
import fr.uge.poo.paint.ex7_bis.Canvas.CanvasColor;

public interface Shape {
	int sqDistance(int x , int y);
	void draw(Canvas canvas, CanvasColor color);
	int xMax();
	int yMax();
}