package fr.uge.poo.paint.ex8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Parser {
	
	public static List<Shape> parseShape(String pathname) throws IOException {
		Objects.requireNonNull(pathname);
		var paint = new ArrayList<Shape>();
		Path path = Paths.get(pathname);
		
		try(Stream<String> lines = Files.lines(path)) {
			lines.forEach(l -> {
				String[] token = l.split(" ");
				switch (token[0]) {
				case "line" -> paint.add(new Line(Integer.parseInt(token[1]), Integer.parseInt(token[2]), Integer.parseInt(token[3]), Integer.parseInt(token[4])));
				case "rectangle" -> paint.add(new Rectangle(Integer.parseInt(token[1]), Integer.parseInt(token[2]), Integer.parseInt(token[3]), Integer.parseInt(token[4])));
				case "ellipse" -> paint.add(new Ellipse(Integer.parseInt(token[1]), Integer.parseInt(token[2]), Integer.parseInt(token[3]), Integer.parseInt(token[4])));
				default -> throw new IllegalArgumentException("Shape not permits");
				}
			});
		}
		return paint;
	}
	
	public static Dimension parseDimension(List<Shape> shapes) throws IOException {
		Objects.requireNonNull(shapes);
		int xMax = 0;
		int yMax = 0;
		
		for(var shape : shapes) {
			if(shape.xMax() > xMax) {
				xMax = shape.xMax();
			}
			if(shape.yMax() > yMax) {
				yMax = shape.yMax();
			}
		}
		if(xMax < 500) {
			xMax = 500;
		}
		if(yMax < 500) {
			yMax = 500;
		}
		return new Dimension(xMax, yMax);
	}
}