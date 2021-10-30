package fr.uge.poo.paint.ex3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Paint {

	private final ArrayList<Shape> shapes = new ArrayList<>();

	public void add(Shape shape){
		Objects.requireNonNull(shape);
		shapes.add(shape);
	}

	@Override
	public String toString() {
		return shapes.stream().map(d -> d.toString()).collect(Collectors.joining("\n"));
	}

	public void drawAll(Graphics2D graphics) {
		graphics.setColor(Color.BLACK);
		shapes.forEach( d -> d.draw(graphics));
	}
}