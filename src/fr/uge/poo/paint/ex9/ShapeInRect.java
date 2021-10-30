package fr.uge.poo.paint.ex9;

import java.util.Objects;

public abstract class ShapeInRect implements Shape {
	protected final int x;
	protected final int y;
	protected final int width;
	protected final int height;
	
	public ShapeInRect(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public double distance2(int x, int y) {
		return Shape.distance(x, y, this.x + width / 2, this.y + height / 2);
	}

	@Override
	public WindowSize minWindowSize() {
		return new WindowSize(x + width, y + height);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y, width, height);
	}
}
