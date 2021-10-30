package fr.uge.poo.paint.ex8;

import java.io.IOException;
import java.util.ServiceLoader;

import fr.uge.poo.paint.ex8.Canvas.CanvasColor;

public class Main {

	public static void main(String[] args) {
		String pathName = "";
		Canvas area;

		GraphicsAdapterFactory factory;

		if(args.length != 1){
			throw new IllegalArgumentException("Usage: Paint <file>");
		}
		pathName = args[0];

		ServiceLoader<GraphicsAdapterFactory> loader = ServiceLoader.load(GraphicsAdapterFactory.class);

		try {
			var shapes = Parser.parseShape(pathName);
			var dimension = Parser.parseDimension(shapes);

			if(loader.findFirst().isPresent()) {
				factory = loader.findFirst().get();
				area = factory.getCanvas("area", dimension.width(), dimension.height());
			}
			else {
				area = new SimpleGraphicsAdapter("area", dimension.width(), dimension.height());
			}

			area.clear(CanvasColor.WHITE);

			Paint.drawAll(area, shapes, CanvasColor.BLACK);

			EventListener.highlightsOnOrangeTheNearestOnCLick(area, shapes);

		}catch(IOException e){
			System.err.println(e.getMessage());
			System.exit(1);
			return ;

		}
	}
}