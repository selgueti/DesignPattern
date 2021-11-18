package fr.uge.poo.cmdline.ex3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class CmdLineParser {

	private final Map<String, Consumer<List<String>>> options = new HashMap<>();
	private final Map<String, Integer> nbParams = new HashMap<>();

	public void addOptionWithOneParameter(String option, Consumer<String> action) {
		Objects.requireNonNull(option);
		Objects.requireNonNull(action);
		Consumer<List<String>> newConsumer = parameters -> {
			var parameter = parameters.get(0);
			if (parameter == null) {
				throw new IllegalArgumentException("Parameter for option " + option + "is missing");
			}
			action.accept(parameter);
		};
		registerWithParameter(option, 1, newConsumer);
	}

	public void addFlag(String option, Runnable action) {
		Objects.requireNonNull(option);
		Objects.requireNonNull(action);
		Consumer<List<String>> newConsumer = parameters -> action.run();
		registerWithParameter(option, 0, newConsumer);
	}

	public void registerWithParameter(String option, int nbParam, Consumer<List<String>> action) {
		Objects.requireNonNull(option);
		Objects.requireNonNull(action);
		options.put(option, action);
		nbParams.put(option, nbParam);
	}

	private static List<String> getParameters(Iterator<String> arguments, int nbArgs) {
		var res = new ArrayList<String>();
		for (var i = 0; i < nbArgs; i++) {
			if (arguments.hasNext()) {
				var parameter = arguments.next();
				res.add(parameter);
			} else {
				throw new IllegalArgumentException("Parameter(s) is/are missing");
			}
		}
		return res;
	}
	
	public List<String> process(String[] arguments) {
		Objects.requireNonNull(arguments);
		ArrayList<String> files = new ArrayList<>();
		var argsIter = Arrays.stream(arguments).iterator();

		while (argsIter.hasNext()) {
			var arg = argsIter.next();
			var option = options.get(arg);
			if(option == null) {
				files.add(arg);
			}else {
				var tmpIter = CmdLineParser.getParameters(argsIter, nbParams.get(arg));
				option.accept(tmpIter);
			}
		}
		return files;
	}

}