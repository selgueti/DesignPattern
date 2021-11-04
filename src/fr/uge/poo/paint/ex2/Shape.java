package fr.uge.poo.paint.ex2;

import java.awt.Graphics2D;

public interface Shape {
	
	public void draw(Graphics2D graphics);

	record Line(int x1, int y1, int x2, int y2) implements Shape {
		
		@Override
		public void draw(Graphics2D graphics) {
			//TODO
		}
	};
}