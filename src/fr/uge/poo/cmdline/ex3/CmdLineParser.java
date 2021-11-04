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

	private final Map<String, Consumer<Iterator<String>>> options = new HashMap<>();
	private final Map<String, Integer> nbParams = new HashMap<>();

	public void registerOption(String option, Consumer<Iterator<String>> action) {
		Objects.requireNonNull(option);
		Objects.requireNonNull(action);
		options.put(option, action);
		nbParams.put(option, 0);
	}
	
	public void addOptionWithOneParameter(String option, Consumer<String> action) {				
		Consumer<Iterator<String>> newConsumer = iter -> {
			String parameter;
			if(iter.hasNext()) {
				parameter = iter.next();
			}else {
				throw new IllegalArgumentException("Parameter for option " + option + "is missing");
			}
			action.accept(parameter);
		};
		registerWithParameter(option, 1, newConsumer);
	}

	public void registerWithParameter(String option, int nbParam, Consumer<Iterator<String>> action) {
		Objects.requireNonNull(option);
		Objects.requireNonNull(action);
		options.put(option, action);
		nbParams.put(option, nbParam);
	}

	private static Iterator<String> getParameters(Iterator<String> it, int nbArgs) {
		var res = new ArrayList<String>();

		for (var i = 0; i < nbArgs; i++) {
			if (it.hasNext()) {
				res.add(it.next());
			} else {
				throw new IllegalArgumentException("Parameter(s) is/are missing");
			}
		}
		return res.iterator();
	}

	public List<String> process(String[] arguments) {
		Objects.requireNonNull(arguments);
		ArrayList<String> files = new ArrayList<>();

		var argsIter = Arrays.stream(arguments).iterator();

		while (argsIter.hasNext()) {
			var arg = argsIter.next();
			if (options.containsKey(arg)) {
				var tmpIter = CmdLineParser.getParameters(argsIter, nbParams.get(arg));
				options.get(arg).accept(tmpIter);
			} else {
				files.add(arg);
			}
		}
		return files;
	}
}