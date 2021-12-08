package fr.uge.poo.cmdline.ex5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class CmdLineParser {

	private static class Option {
		private final int nbParams;
		private final Required required;
		private Status status;
		private final Consumer<List<String>> action;

		private Option(int nbParams, Required required, Consumer<List<String>> action) {
			this.nbParams = nbParams;
			this.required = required;
			this.status = Status.UNSET;
			this.action = action;
		}

		public void setStatus(Status status) {
			this.status = status;
		}
	}

	enum Status {
		SET, UNSET
	}

	enum Required {
		YES, NO
	}

	private final Map<String, Option> options = new HashMap<>();
	// private final Map<String, Integer> nbParams = new HashMap<>();

	public void addOptionWithOneParameter(String option, Consumer<String> action) {
		Objects.requireNonNull(option);
		Objects.requireNonNull(action);
		Consumer<List<String>> newConsumer = parameters -> action.accept(parameters.get(0));
		registerWithParameter(option, 1, Required.NO, newConsumer);
	}

	public void addFlag(String option, Runnable action) {
		Objects.requireNonNull(option);
		Objects.requireNonNull(action);
		Consumer<List<String>> newConsumer = parameters -> action.run();
		registerWithParameter(option, 0, Required.NO, newConsumer);
	}

	public void registerWithParameter(String option, int nbParam, Required required, Consumer<List<String>> action) {
		Objects.requireNonNull(option);
		Objects.requireNonNull(action);
		options.put(option, new Option(nbParam, required, action));
//		nbParams.put(option, nbParam);
	}

	private static List<String> getParameters(Iterator<String> arguments, int nbArgs) {
		var res = new ArrayList<String>();
		for (var i = 0; i < nbArgs; i++) {
			if (arguments.hasNext()) {
				var parameter = arguments.next();
				if (parameter.charAt(0) == '-') {
					throw new IllegalArgumentException("Parameters can't start with '-'");
				}
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
		// options.entrySet().stream().forEach(System.out::println);
		while (argsIter.hasNext()) {
			var arg = argsIter.next();
			if (arg.charAt(0) == '-') {
				var option = options.get(arg);
				if (option == null) {
					throw new IllegalArgumentException("This options does not exist");
				} else {
					var tmpIter = CmdLineParser.getParameters(argsIter, option.nbParams);
					option.action.accept(tmpIter);
					option.setStatus(Status.SET);
				}
			} else {
				files.add(arg);
			}
		}
		var unsets = options.entrySet().stream()
				.filter(e -> e.getValue().required == Required.YES && e.getValue().status == Status.UNSET).findFirst();
		if (unsets.isPresent()) {
			throw new IllegalArgumentException("One or more required options are not set");
		}
		return files;
	}

}