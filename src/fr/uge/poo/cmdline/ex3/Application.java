package fr.uge.poo.cmdline.ex3;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import fr.uge.poo.cmdline.ex3.PaintSettings.PaintSettingsBuilder;
import fr.uge.poo.paint.ex9.WindowSize;

public class Application {

	public static void main(String[] args) {

		var options = new PaintSettingsBuilder();

		String[] arguments = { "-window-name", "fmzeknfzknze", "-legacy", "-no-borders", "filename1", "filename2",
				"-border-width", "3", "-min-size", "600", "600", "-remote-server", "ige.fr", "48" };
		var cmdParser = new CmdLineParser();

		cmdParser.registerOption("-legacy", iterString -> options.setLegacy(true));
		cmdParser.registerOption("-with-borders", iterString -> options.setBordered(true));
		cmdParser.registerOption("-no-borders", iterString -> options.setBordered(false));
		cmdParser.registerWithParameter("-min-size", 2, iterString -> {
			var width = 0;
			var height = 0;
			if (iterString.hasNext()) {
				width = Integer.parseInt(iterString.next());
			} else {
				throw new IllegalArgumentException("Parameter width is missing");
			}

			if (iterString.hasNext()) {
				height = Integer.parseInt(iterString.next());
			} else {
				throw new IllegalArgumentException("Parameter height is missing");
			}
			var ws = new WindowSize(width, height);
			options.setMinSize(ws);
		});

		cmdParser.addOptionWithOneParameter("-window-name", name -> options.setWindowName(name));

		cmdParser.addOptionWithOneParameter("-border-width", bw -> options.setBorderWidth(Integer.parseInt(bw)));
		
		cmdParser.registerWithParameter("-remote-server", 2, iterString -> {
			var name = "";
			var port = 0;
			if (iterString.hasNext()) {
				name = iterString.next();
			} else {
				throw new IllegalArgumentException("Parameter name is missing");
			}

			if (iterString.hasNext()) {
				port = Integer.parseInt(iterString.next());
			} else {
				throw new IllegalArgumentException("Parameter port is missing");
			}
			options.setRemoteServer(new RemoteServer(name, port));
		});

		// options.setWindowName("area");
		List<String> result = cmdParser.process(arguments);

		var settings = options.build();

		List<Path> files = result.stream().map(Path::of).collect(Collectors.toList());
		// this code replaces the rest of the application
		files.forEach(p -> System.out.println(p));

		System.out.println(settings.toString());

	}
}
