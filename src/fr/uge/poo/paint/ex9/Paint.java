package fr.uge.poo.paint.ex9;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Paint {

	public static void main(String[] args) throws IOException{
		var isLegacy = false;
		String pathName = "";
		Canvas area;
		
		if(args.length < 1 || args.length > 2){
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
		Path path = Paths.get(pathName);
		var drawing = Drawing.fromFile(path);
		var windowSize = drawing.minWindowSize();
		area = isLegacy ? new SimpleGraphicsAdapter("area", windowSize.height(), windowSize.width()) 
				        : new CoolGraphicsAdapter  ("area", windowSize.height(), windowSize.width());
		drawing.paintAll(area);
		drawing.onClick(area);
	}
}