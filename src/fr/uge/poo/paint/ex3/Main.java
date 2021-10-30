package fr.uge.poo.paint.ex3;

import java.awt.Color;
import java.io.IOException;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public class Main {

	public static void main(String[] args) throws IOException {
		if(args.length != 1){
			throw new IllegalArgumentException("Usage: Paint <file>");
		}

		var area = new SimpleGraphics("area", 800, 600);
		area.clear(Color.WHITE);

		var paint = Parser.parse(args[0]);
		area.render(paint::drawAll);
		System.out.println(paint);
	}
}