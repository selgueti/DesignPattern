package fr.uge.poo.cmdline.ex5;

import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.List;

import static fr.uge.poo.cmdline.ex5.CmdLineParser.*;

public class Application {

	private static class PaintSettings {

		private final boolean legacy;
		private final boolean bordered;
		private final int borderWidth;
		private final String windowName;
		private final WindowSize minWindowSize;
		private final InetSocketAddress remoteServer;

		private record WindowSize(int width, int height) {

			private WindowSize union (WindowSize ws){
				return new WindowSize(Math.max(this.width, ws.width()), Math.max(this.width, ws.width()));
			}
		}


		private PaintSettings(PaintSettingsBuilder builder) {
			this.legacy = builder.legacy;
			this.bordered = builder.bordered;
			this.borderWidth = builder.borderWidth;
			this.windowName = builder.windowName;
			this.minWindowSize = builder.minWindowSize;
			this.remoteServer = builder.remoteServer;
		}

		static private class PaintSettingsBuilder {
			private boolean legacy = false;
			private boolean bordered = true;
			private int borderWidth = 10;
			private String windowName = "";
			private WindowSize minWindowSize = new WindowSize(500, 500);
			private InetSocketAddress remoteServer;

			public PaintSettingsBuilder setLegacy(boolean legacy) {
				this.legacy = legacy;
				return this;
			}

			public PaintSettingsBuilder setBordered(boolean bool) {
				this.bordered = bool;
				return this;
			}

			public PaintSettingsBuilder setBorderWidth(int borderWidth) {
				this.borderWidth = borderWidth;
				return this;
			}

			public PaintSettingsBuilder setWindowName(String windowName) {
				this.windowName = windowName;
				return this;
			}

			public PaintSettingsBuilder setMinSize(WindowSize minWindowSize) {
				this.minWindowSize = this.minWindowSize.union(minWindowSize);
				return this;
			}

//		public PaintSettingsBuilder setRemoteServer(InetSocketAddress remoteServer) {
//			this.remoteServer = remoteServer;
//			return this;
//		}

			public PaintSettingsBuilder setRemoteServer(String name, int port) {
				this.remoteServer = new InetSocketAddress(name, port);
				return this;
			}

			public PaintSettings build() {
				if (windowName.equals("")) {
					throw new IllegalArgumentException("window name is required");
				}
				return new PaintSettings(this);
			}
		}

		@Override
		public String toString() {
			return "PaintSettings [ bordered = " + bordered + ", legacy = " + legacy + ", border-width = " + borderWidth
					+ ", min-size =  " + minWindowSize + ", window-name = " + windowName + ", remote-server = "
					+ remoteServer + " ]";
		}
	}


	public static void main(String[] args) {

		var options = new PaintSettings.PaintSettingsBuilder();

		String[] arguments = { "-window-name", "hello.txt", "-legacy", "-no-borders", "filename1", "filename2",
				"-border-width", "3", "-remote-server", "igm.fr", "48" , "-min-size", "600", "600"};
		var cmdParser = new CmdLineParser();

		cmdParser.addFlag("-legacy", () -> options.setLegacy(true));
		cmdParser.addOptionWithOneParameter("-with-borders", parameters -> options.setBordered(true));
		cmdParser.addFlag("-no-borders", () -> options.setBordered(false));
		cmdParser.registerWithParameter("-min-size", 2, Required.YES, parameters -> {
			int width;
			int height;
			try {
				var parameter = parameters.get(0);
				if (parameter == null) {
					throw new IllegalArgumentException("-min-size option require width");
				}
				width = Integer.parseInt(parameter);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("width mush be integer");
			}
			try {
				var parameter = parameters.get(1);
				if (parameter == null) {
					throw new IllegalArgumentException("-min-size option require height");
				}
				height = Integer.parseInt(parameter);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("height mush be integer");
			}
			var ws = new PaintSettings.WindowSize(width, height);
			options.setMinSize(ws);
		});

		cmdParser.addOptionWithOneParameter("-window-name", options::setWindowName);

		cmdParser.addOptionWithOneParameter("-border-width", bw -> options.setBorderWidth(Integer.parseInt(bw)));

		cmdParser.registerWithParameter("-remote-server", 2, Required.NO, parameters -> {
			var name = "";
			var port = 0;
			var parameter = parameters.get(0);
			if (parameter == null) {
				throw new IllegalArgumentException("server must had a name");
			}
			name = parameter;
			try {
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

		List<Path> files = result.stream().map(Path::of).toList();
		// this code replaces the rest of the application
		files.forEach(System.out::println);

		System.out.println(settings);

	}
}
