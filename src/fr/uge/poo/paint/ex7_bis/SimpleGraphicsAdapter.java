package fr.uge.poo.paint.ex7_bis;

import java.awt.Color;
import java.util.Objects;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public final class SimpleGraphicsAdapter implements Canvas {
	private final SimpleGraphics sg;

	public SimpleGraphicsAdapter(String name, int width, int height) {
		Objects.requireNonNull(name);
		sg = new SimpleGraphics(name, width, height);
	}
	
	private static Color translate(CanvasColor color) {
		return switch(color) {
		case RED -> Color.RED;
		case GREEN -> Color.GREEN;
		case ORANGE -> Color.ORANGE;
		case BLUE -> Color.BLUE;
		case BLACK -> Color.BLACK;
		case WHITE -> Color.WHITE;
		default -> throw new IllegalArgumentException("Unexpected value: " + color);
		};
	}

	@Override
	public void clear(CanvasColor canvasColor) {
		Objects.requireNonNull(canvasColor);
		sg.clear(translate(canvasColor));
	}

	@Override
	public void drawLine(CanvasColor canvasColor, int x1, int y1, int x2, int y2) {
		Objects.requireNonNull(canvasColor);
		sg.render( g -> {
			g.setColor(translate(canvasColor));
			g.drawLine(x1, y1, x2, y2);
		});

	}

	@Override
	public void drawRect(CanvasColor canvasColor, int x, int y, int width, int height) {
		Objects.requireNonNull(canvasColor);
		sg.render( g -> {
			g.setColor(translate(canvasColor));
			g.drawRect(x, y, width, height);
		});
	}

	@Override
	public void drawEllipse(CanvasColor canvasColor, int x, int y, int width, int height) {
		Objects.requireNonNull(canvasColor);
		sg.render( g -> {
			g.setColor(translate(canvasColor));
			g.drawOval(x, y, width, height);
		});
	}

	@Override
	public void waitForMouseClick(MouseClickCallBack callback) {
		sg.waitForMouseEvents(callback::onClick);
	}
}
