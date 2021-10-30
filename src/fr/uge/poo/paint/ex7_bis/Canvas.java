package fr.uge.poo.paint.ex7_bis;

public sealed interface Canvas permits SimpleGraphicsAdapter, CoolGraphicsAdapter {
	
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
