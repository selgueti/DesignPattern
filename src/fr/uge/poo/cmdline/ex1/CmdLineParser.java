package fr.uge.poo.cmdline.ex1;

import java.util.*;

public class CmdLineParser {

	private final HashMap<String, Runnable> options = new HashMap<>();

	public void registerOption(String option, Runnable runnable) {
		Objects.requireNonNull(option);
		Objects.requireNonNull(runnable);
		options.put(option, runnable);
	}

	public List<String> process(String[] arguments) {
		Objects.requireNonNull(arguments);
		ArrayList<String> files = new ArrayList<>();
		for (String argument : arguments) {
			if (options.containsKey(argument)) {
				options.get(argument).run();
			} else {
				files.add(argument);
			}
		}
		return files;
	}
}