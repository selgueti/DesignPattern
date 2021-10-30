package fr.uge.poo.paint.ex9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import fr.uge.poo.paint.ex9.Canvas.CanvasColor;

public class Drawing {
	private Shape selected;
	private final ArrayList<Shape> shapes;

	private Drawing(List<Shape> shapes) {
		this.shapes = new ArrayList<>(List.copyOf(shapes));
	}

	public static Drawing fromFile(Path path) throws IOException {
		Objects.requireNonNull(path);
		ArrayList<Shape> shapes = new ArrayList<>();
		try (Stream<String> lines = Files.lines(path)) {
			lines.forEach(l -> {
				String[] token = l.split(" ");
				switch (token[0]) {
				case "line" -> shapes.add(new Line(Integer.parseInt(token[1]), Integer.parseInt(token[2]),
						Integer.parseInt(token[3]), Integer.parseInt(token[4])));
				case "rectangle" -> shapes.add(new Rectangle(Integer.parseInt(token[1]), Integer.parseInt(token[2]),
						Integer.parseInt(token[3]), Integer.parseInt(token[4])));
				case "ellipse" -> shapes.add(new Ellipse(Integer.parseInt(token[1]), Integer.parseInt(token[2]),
						Integer.parseInt(token[3]), Integer.parseInt(token[4])));
				default -> throw new IllegalArgumentException("Shape not permits");
				}
			});
		}
		return new Drawing(shapes);
	}

	public void paintAll(Canvas area) {
		area.clear(CanvasColor.WHITE);
		shapes.forEach(s -> {
			if (s.equals(selected)) {
				s.draw(area, CanvasColor.ORANGE);
			} else {
				s.draw(area, CanvasColor.BLACK);
			}
		});
		area.refresh();
	}

	public void onClick(Canvas area) {
		area.waitForMouseClick(
				(a, b) -> shapes.stream().min(Comparator.comparingDouble(s -> s.distance2(a, b))).ifPresent(s -> {
					selected = s;
					paintAll(area);
				}));
	}

	public WindowSize minWindowSize() {
		WindowSize ws = new WindowSize(500, 500);
		for (Shape shape : shapes) {
			ws = ws.union(shape.minWindowSize());
		}
		return ws;
	}
}
