package fr.uge.poo.paint.ex9;

import java.util.ArrayList;
import java.util.Objects;

import com.evilcorp.coolgraphics.CoolGraphics;
import com.evilcorp.coolgraphics.CoolGraphics.ColorPlus;

public final class CoolGraphicsAdapter implements Canvas {

	private final CoolGraphics cg;
	private final ArrayList<Runnable> drawCalls = new ArrayList<>();

	private static ColorPlus translate(CanvasColor color) {
		return switch(color) {
		case RED -> ColorPlus.RED;
		case GREEN -> ColorPlus.GREEN;
		case ORANGE -> ColorPlus.ORANGE;
		case BLUE -> ColorPlus.BLUE;
		case BLACK -> ColorPlus.BLACK;
		case WHITE -> ColorPlus.WHITE;

		default -> throw new IllegalArgumentException("Unexpected value: " + color);
		};
	}

	public CoolGraphicsAdapter(String name, int width, int height) {
		Objects.requireNonNull(name);
		cg = new CoolGraphics(name, width, height);
	}

	@Override
	public void clear(CanvasColor canvasColor) {
		Objects.requireNonNull(canvasColor);
		drawCalls.add(() -> cg.repaint(translate(canvasColor)));

	}

	@Override
	public void drawLine(CanvasColor canvasColor, int x1, int y1, int x2, int y2) {
		Objects.requireNonNull(canvasColor);
		drawCalls.add(() -> cg.drawLine(x1, y1, x2, y2, translate(canvasColor)));
	}

	@Override
	public void drawRect(CanvasColor canvasColor, int x, int y, int width, int height) {
		Objects.requireNonNull(canvasColor);
		var color = translate(canvasColor);
		drawCalls.add(() -> cg.drawLine(x, y, x + width, y, color));
		drawCalls.add(() -> cg.drawLine(x + width, y, x + width, y + height, color));
		drawCalls.add(() -> cg.drawLine(x + width, y + height, x, y + height, color));
		drawCalls.add(() -> cg.drawLine(x, y + height, x, y, color));
	}

	@Override
	public void drawEllipse(CanvasColor canvasColor, int x, int y, int width, int height) {
		Objects.requireNonNull(canvasColor);
		drawCalls.add(() -> cg.drawEllipse(x, y, width, height, translate(canvasColor)));

	}

	@Override
	public void waitForMouseClick(MouseClickCallBack callback) {
		cg.waitForMouseEvents(callback::onClick);
	}

	@Override
	public void refresh() {
		drawCalls.forEach(Runnable::run);
		drawCalls.clear();
	}	
}
