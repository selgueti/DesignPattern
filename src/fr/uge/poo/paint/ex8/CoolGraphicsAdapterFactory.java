package fr.uge.poo.paint.ex8;

public class CoolGraphicsAdapterFactory implements GraphicsAdapterFactory {

	@Override
	public Canvas getCanvas(String name, int width, int height) {
		return new CoolGraphicsAdapter(name, width, height);
	}
	
}
