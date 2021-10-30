package fr.uge.poo.paint.ex6;

import java.awt.Graphics2D;

public interface Shape {
	//void draw(Graphics2D graphics);
	int sqDistance(int x , int y);
	void draw(Graphics2D graphics);
	int xMax();
	int yMax();
	// draw -> passé l'aera pour la question 7
}