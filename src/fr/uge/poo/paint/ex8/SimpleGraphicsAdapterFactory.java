package fr.uge.poo.paint.ex8;

public class SimpleGraphicsAdapterFactory implements GraphicsAdapterFactory {
	
	@Override
	public Canvas getCanvas(String name, int width, int height) {
		return new SimpleGraphicsAdapter(name, width, height);
	}

}
