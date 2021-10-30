package fr.uge.poo.paint.ex7_bis;

import java.io.IOException;

import fr.uge.poo.paint.ex7_bis.Canvas.CanvasColor;

public class Main {

	public static void main(String[] args) {
		var isLegacy = false;
		String pathName = "";
		Canvas area;
        
		if(args.length < 1 && args.length > 2){
			throw new IllegalArgumentException("Usage: Paint <file> [--legacy]");
		}

		for(var arg : args) {
			if (arg.equals("-legacy") || arg.equals("--legacy")) {
				isLegacy = true;
			}
			else {
				pathName = arg;
			}
		}

		try {
			var shapes = Parser.parseShape(pathName);
			var dimension = Parser.parseDimension(shapes);

			if(isLegacy) {
				area = new SimpleGraphicsAdapter("area", dimension.height(), dimension.width());
			}else {
				area = new CoolGraphicsAdapter("area", dimension.height(), dimension.width());
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