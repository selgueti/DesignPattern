package fr.uge.poo.cmdline.ex2;

import java.nio.file.Path;
import java.util.List;

public class Application {

	  static private class PaintSettings {
		private boolean legacy = false;
		private boolean bordered = true;
		private int borderWidth = 0;

		public void setLegacy(boolean legacy) {
			this.legacy = legacy;
		}

		public void setBorderWidth(int borderWidth) {
			this.borderWidth = borderWidth;
		}

		public boolean isLegacy() {
			return legacy;
		}

		public void setBordered(boolean bordered) {
			this.bordered = bordered;
		}

		public boolean isBordered() {
			return bordered;
		}

		@Override
		public String toString() {
			return "PaintSettings [ bordered = " + bordered + ", legacy = " + legacy + ", border-width = " + borderWidth
					+ " ]";
		}
	}

	public static void main(String[] args) {

		var options = new PaintSettings();
		String[] arguments = { "-legacy", "-no-borders", "filename1", "filename2", "-border-width", "3" };
		var cmdParser = new CmdLineParser();

		cmdParser.addFlag("-legacy", iterString -> options.setLegacy(true));
		cmdParser.addFlag("-with-borders", iterString -> options.setBordered(true));
		cmdParser.addFlag("-no-borders", iterString -> options.setBordered(false));
		cmdParser.registerWithParameter("-border-width", 1, iterString -> options.setBorderWidth(Integer.parseInt(iterString.next())));

		List<String> result = cmdParser.process(arguments);
		List<Path> files = result.stream().map(Path::of).toList();
		// this code replaces the rest of the application
		files.forEach(System.out::println);

		System.out.println(options);

	}
}
