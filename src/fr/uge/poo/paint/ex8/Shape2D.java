package fr.uge.poo.paint.ex8;

public sealed interface Shape2D permits Ellipse, Rectangle {

	static int sqDistanceFromShape(int x, int y, int xShape, int yShape, int widthShape, int heightShape) {
		var x_center = xShape + widthShape / 2;
		var y_center = yShape + heightShape / 2;
		return (x - x_center) * (x - x_center) + (y - y_center) * (y - y_center);
	}
	
	static int x_max2D(int x, int width) {
		return x + width;
	}
	
	static int y_max2D(int y, int height) {
		return y + height;
	}
}
