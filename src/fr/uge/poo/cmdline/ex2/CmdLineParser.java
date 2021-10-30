package fr.uge.poo.cmdline.ex2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CmdLineParser {

	private final Map<String, Action> options = new HashMap<>();

	public void registerOption(String option, Action action) {
		Objects.requireNonNull(option);
		Objects.requireNonNull(action);
		options.put(option, action);
	}

	public void registerWithParameter(String option, Action action) {
		Objects.requireNonNull(option);
		Objects.requireNonNull(action);
		options.put(option, action);
	}

	public List<String> process(String[] arguments) {
		Objects.requireNonNull(arguments);
		ArrayList<String> files = new ArrayList<>();
		boolean isParameter = false;
		for (int i = 0; i < arguments.length; i++) {
			if (isParameter) {
				isParameter = false;
				continue;
			}
			if (options.containsKey(arguments[i])) {
				var action = options.get(arguments[i]);
				if (action.isRunnable()) {
					action.make();
				} else {
					if (arguments.length == i + 1) {
						throw new IllegalArgumentException(arguments[i] + " require a parameter");
					}
					action.make(arguments[i + 1]);
					isParameter = true;
				}
			} else {
				files.add(arguments[i]);
			}
		}
		return files;
	}
}