package fr.uge.poo.paint.ex5;

public sealed interface Shape2D permits Ellipse, Rectangle {

	static int sqDistanceFromShape(int x, int y, int xShape, int yShape, int widthShape, int heightShape) {
		var x_center = xShape + widthShape / 2;
		var y_center = yShape + heightShape / 2;
		return (x - x_center) * (x - x_center) + (y - y_center) * (y - y_center);
	}
}
