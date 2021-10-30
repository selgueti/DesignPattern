package fr.uge.poo.paint.ex6;

import java.awt.Color;
import java.io.IOException;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public class Main {

	public static void main(String[] args) {
		if(args.length != 1){
			throw new IllegalArgumentException("Usage: Paint <file>");
		}

		try {
			var shapes = Parser.parseShape(args[0]);
			var dimension = Parser.parseDimension(shapes);

			var area = new SimpleGraphics("area", dimension.height(), dimension.width());
			area.clear(Color.WHITE);
			area.render(g -> Paint.drawAll(g, shapes));

			EventListener.highlightsOnOrangeTheNearestOnCLick(area, shapes);

		}catch(IOException e){
			System.err.println(e.getMessage());
			System.exit(1);
			return ;

		}
	}
}