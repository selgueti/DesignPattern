package fr.uge.poo.cmdline.ex4;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import fr.uge.poo.cmdline.ex4.CmdLineParser.Required;
import fr.uge.poo.cmdline.ex4.PaintSettings.PaintSettingsBuilder;

public class Application {

	public static void main(String[] args) {

		var options = new PaintSettingsBuilder();

		String[] arguments = { "-window-name", "fmzeknfzknze", "-legacy", "-no-borders", "filename1", "filename2",
				"-border-width", "3", "-remote-server", "igm.fr", "48" , "-min-size", "600", "600"};
		var cmdParser = new CmdLineParser();

		cmdParser.addFlag("-legacy", () -> options.setLegacy(true));
		cmdParser.addOptionWithOneParameter("-with-borders", parameters -> options.setBordered(true));
		cmdParser.addFlag("-no-borders", () -> options.setBordered(false));
		cmdParser.registerWithParameter("-min-size", 2, Required.YES, parameters -> {
			int width = 0;
			int height = 0;
			try {
				var parameter = parameters.get(0);
				if (parameter == null) {
					throw new IllegalArgumentException("-min-size option requiert width");
				}
				width = Integer.parseInt(parameter);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("width mush be integer");
			}
			try {
				var parameter = parameters.get(1);
				if (parameter == null) {
					throw new IllegalArgumentException("-min-size option requiert height");
				}
				height = Integer.parseInt(parameter);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("height mush be integer");
			}
			var ws = new WindowSize(width, height);
			options.setMinSize(ws);
		});

		cmdParser.addOptionWithOneParameter("-window-name", name -> options.setWindowName(name));

		cmdParser.addOptionWithOneParameter("-border-width", bw -> options.setBorderWidth(Integer.parseInt(bw)));

		cmdParser.registerWithParameter("-remote-server", 2, Required.NO, parameters -> {
			var name = "";
			var port = 0;
			var parameter = parameters.get(0);
			if (parameter == null) {
				throw new IllegalArgumentException("serveur must had a name");
			}
			name = parameter;
			try {
				parameter = null;
				parameter = parameters.get(1);
				port = Integer.parseInt(parameter);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("port mush be integer");
			}
//			options.setRemoteServer(new InetSocketAddress(name, port));
			options.setRemoteServer(name, port);
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
