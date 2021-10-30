package fr.uge.poo.paint.ex4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Paint {

	private final ArrayList<Shape> shapes = new ArrayList<>();

	public void add(Shape shape){
		Objects.requireNonNull(shape);
		shapes.add(shape);
	}

	public List<Shape> getShape(){
		return List.copyOf(shapes);
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