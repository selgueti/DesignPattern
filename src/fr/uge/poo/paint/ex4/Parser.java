package fr.uge.poo.paint.ex4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Parser {

	public static Paint parse(String pathname) throws IOException {
		var paint = new Paint();
		Path path = Paths.get(pathname);
		try(Stream<String> lines = Files.lines(path)) {
			lines.forEach(l -> {
				String[] token = l.split(" ");
				switch (token[0]) {
				case "line" -> paint.add(new Line(Integer.parseInt(token[1]), Integer.parseInt(token[2]), Integer.parseInt(token[3]), Integer.parseInt(token[4])));
				case "rectangle" -> paint.add(new Rectangle(Integer.parseInt(token[1]), Integer.parseInt(token[2]), Integer.parseInt(token[3]), Integer.parseInt(token[4])));
				case "ellipse" -> paint.add(new Ellipse(Integer.parseInt(token[1]), Integer.parseInt(token[2]), Integer.parseInt(token[3]), Integer.parseInt(token[4])));
				default -> throw new IllegalArgumentException("figure not permits");
				}
			});
		}
		return paint;
	}
}