package fr.uge.poo.paint.ex9;

import java.util.Objects;

public abstract class ShapeInRect implements Shape {
	private final int x;
	private final int y;
	private final int width;
	private final int height;
	
	public ShapeInRect(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public double distance2(int x, int y) {
		return Shape.distance(x, y, this.getX() + getWidth() / 2, this.getY() + getHeight() / 2);
	}

	@Override
	public WindowSize minWindowSize() {
		return new WindowSize(getX() + getWidth(), getY() + getHeight());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getX(), getY(), getWidth(), getHeight());
	}

	public int getWidth() {
		return width;
	}

	public int getX() {
		return x;
	}

	public int getHeight() {
		return height;
	}

	public int getY() {
		return y;
	}
}
