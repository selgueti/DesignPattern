package fr.uge.poo.paint.ex8;

public  interface Canvas {
	
	enum CanvasColor{ RED, GREEN, ORANGE, BLUE, BLACK, WHITE }
	
	@FunctionalInterface
	interface MouseClickCallBack{
		void onClick(int x, int y);
	}
	
	void clear(CanvasColor canvasColor);
	void drawLine(CanvasColor canvasColor, int x1, int y1, int x2, int y2);
	void drawRect(CanvasColor canvasColor, int x, int y, int width, int height);
	void drawEllipse(CanvasColor canvasColor, int x, int y, int width, int height);
	void waitForMouseClick(MouseClickCallBack callback);
}
