package fr.uge.poo.cmdline.ex7;

import java.text.ParseException;
import java.util.*;
import java.util.function.Consumer;

public class CmdLineParser {

	public static final ParameterRetrievalStrategy STANDARD = new StandardStrategy();
	public static final ParameterRetrievalStrategy RELAXED = new RelaxedStrategy();
	public static final ParameterRetrievalStrategy OLDSCHOOL = new OldschoolStrategy();

	private final OptionsManager optionsManager;
	private final DocumentationObserver documentationObserver;

	public CmdLineParser(){
		optionsManager = new OptionsManager();
		documentationObserver = new DocumentationObserver();
		optionsManager.registerObserver(new LoggerObserver());
		optionsManager.registerObserver(new RequiredObserver());
		optionsManager.registerObserver(new ConflictsObserver());
		optionsManager.registerObserver(documentationObserver);
	}

	public void addOptionWithOneParameter(String name, Consumer<String> action) {
		addOptionWithOneParameter(name, false, action);
	}

	public void addOptionWithOneParameter(String name, boolean isRequired, Consumer<String> action) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(action);
		Consumer<List<String>> newConsumer = parameters -> action.accept(parameters.get(0));
		var builder = new Option.OptionBuilder(name, 1, newConsumer);
		if(isRequired){
			builder.required();
		}
		var option = builder.build();
		optionsManager.register(option);
	}

	public void addFlag(String name, Runnable action) {
		addFlag(name, false, action);
	}

	public void addFlag(String name, boolean isRequired, Runnable action) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(action);
		Consumer<List<String>> newConsumer = parameters -> action.run();
		var builder = new Option.OptionBuilder(name, 0, newConsumer);
		if(isRequired){
			builder.required();
		}
		var option = builder.build();
		optionsManager.register(option);
	}

	public void registerWithParameter(String name, int nbParam, Consumer<List<String>> action) {
		registerWithParameter(name, nbParam, false, action);
	}

	public void registerWithParameter(String name, int nbParam, boolean isRequired, Consumer<List<String>> action) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(action);
		var builder = new Option.OptionBuilder(name, nbParam, action);
		if(isRequired){
			builder.required();
		}
		var option = builder.build();
		optionsManager.register(option);
	}

	public void addOption(Option option){
		optionsManager.register(option);
	}

	public void usage(){
		documentationObserver.usage();
	}

	public List<String> process(String[] arguments) throws ParseException {
		return process(arguments, STANDARD);
	}

	public List<String> process(String[] arguments, ParameterRetrievalStrategy strategy) throws ParseException {
		Objects.requireNonNull(arguments);
		ArrayList<String> files = new ArrayList<>();
		//var argsIter = Arrays.stream(arguments).iterator();

		var argsDeque = new ArrayDeque<String>(arguments.length);
		argsDeque.addAll(Arrays.stream(arguments).toList());

		// options.entrySet().stream().forEach(System.out::println);
		/*while (argsIter.hasNext()) {
			var arg = argsIter.next();
			if (arg.charAt(0) == '-') {
				var optionalOption = optionsManager.processOption(arg);
				if(optionalOption.isPresent()){
					var option = optionalOption.get();
					var tmpIter = strategy.retriveParameters(argsIter, option);
					option.action().accept(tmpIter);
				}else{
					throw new IllegalArgumentException("This options does not exist");
				}
			} else {
				files.add(arg);
			}
		}*/

		while (!argsDeque.isEmpty()) {
			var arg = argsDeque.removeFirst();
			if (arg.charAt(0) == '-') {
				var optionalOption = optionsManager.processOption(arg);
				if(optionalOption.isPresent()){
					var option = optionalOption.get();
					var parameters = strategy.retriveParameters(argsDeque, option);
					option.action().accept(parameters);
				}else{
					throw new IllegalArgumentException("This options does not exist");
				}
			} else {
				files.add(arg);
			}
		}
		optionsManager.finishProcess();
		return files;
	}

	private interface OptionsManagerObserver {

		void onRegisteredOption(OptionsManager optionsManager, Option option);

		void onProcessedOption(OptionsManager optionsManager, Option option);

		void onFinishedProcess(OptionsManager optionsManager) throws ParseException;
	}

	private static class LoggerObserver implements OptionsManagerObserver {

		@Override
		public void onRegisteredOption(OptionsManager optionsManager, Option option) {
			System.out.println("Option " + option + " is registered");
		}

		@Override
		public void onProcessedOption(OptionsManager optionsManager, Option option) {
			System.out.println("Option " + option + " is processed");
		}

		@Override
		public void onFinishedProcess(OptionsManager optionsManager) {
			System.out.println("Process method is finished");
		}
	}

	private static class RequiredObserver implements OptionsManagerObserver {

		private final List<Option> optionsRequired = new ArrayList<>();

		@Override
		public void onRegisteredOption(OptionsManager optionsManager, Option option) {
			if(option.isRequired()){
				optionsRequired.add(option);
			}
		}

		@Override
		public void onProcessedOption(OptionsManager optionsManager, Option option) {
			optionsRequired.remove(option);
		}

		@Override
		public void onFinishedProcess(OptionsManager optionsManager) throws ParseException {
			optionsRequired.forEach(option -> System.out.println("Option " + option + " is missing"));
			if(!optionsRequired.isEmpty()){
				throw new ParseException("Option required missing", 1);
			}
		}
	}

	private static class DocumentationObserver implements OptionsManagerObserver {

		private final List<Option> options = new ArrayList<>();
		private final StringBuilder sb = new StringBuilder().append("\nUSAGE :\n");

		@Override
		public void onRegisteredOption(OptionsManager optionsManager, Option option) {
			options.add(option);
		}

		@Override
		public void onProcessedOption(OptionsManager optionsManager, Option option) {

		}

		@Override
		public void onFinishedProcess(OptionsManager optionsManager) {

		}

		public void usage(){
			options.stream().sorted(Comparator.comparing(Option::name)).forEach(option -> sb.append(option.name()).append(" -> ").append(option.notice() == null ? "no description for this option" : option.notice()).append("\n"));
			System.out.println(sb);
		}
	}

	private static class ConflictsObserver implements OptionsManagerObserver {

		private final List<Option> options = new ArrayList<>();

		@Override
		public void onRegisteredOption(OptionsManager optionsManager, Option option) {

		}

		@Override
		public void onProcessedOption(OptionsManager optionsManager, Option option) {
			options.add(option);
		}

		@Override
		public void onFinishedProcess(OptionsManager optionsManager) throws ParseException {
			for(var option1 : options){
				for(var option2 : options){
					if(!option1.equals(option2)){
						if(option2.conflicts().contains(option1.name())) {
							throw new ParseException("Conflicts !", 0);
						}
						for(var alias : option1.aliases()){
							if(option2.conflicts().contains(alias)) {
								throw new ParseException("Conflicts !", 0);
							}
						}
					}
				}
			}
		}
	}

	private static class OptionsManager {

		private final HashMap<String, Option> byName = new HashMap<>();
		private final List<OptionsManagerObserver> observers = new ArrayList<>();

		private void registerObserver(OptionsManagerObserver observer) {
			observers.add(observer);
		}

		private void unregisterObserver(OptionsManagerObserver observer) {
			observers.remove(observer);
		}

		/**
		 * Register the option with all its possible names
		 * @param option
		 */
		private void register(Option option) {
			register(option.name(), option);
			for (var alias : option.aliases()) {
				register(alias, option);
			}
			for(var obs : observers){
				obs.onRegisteredOption(this, option);
			}
		}

		private void register(String name, Option option) {

			if (byName.containsKey(name)) {
				throw new IllegalStateException("Option " + name + " is already registered.");
			}
			byName.put(name, option);
		}

		/**
		 * This method is called to signal that an option is encountered during
		 * a command line process
		 *
		 * @param optionName
		 * @return the corresponding object option if it exists
		 */

		private Optional<Option> processOption(String optionName) {
			var option = Optional.ofNullable(byName.get(optionName));
			option.ifPresent(opt -> {
				for(var observer : observers){
					observer.onProcessedOption(this, opt);
				}
			});
			return Optional.ofNullable(byName.get(optionName));
		}

		/**
		 * This method is called to signal the method process of the CmdLineParser is finished
		 */
		private void finishProcess() throws ParseException {
			for(var obs : observers){
				obs.onFinishedProcess(this);
			}
		}
	}

	private interface ParameterRetrievalStrategy {
		List<String> retriveParameters (Deque<String> args, Option option) throws ParseException;
	}

	private final static class StandardStrategy implements ParameterRetrievalStrategy {

		@Override
		public List<String> retriveParameters(Deque<String> args, Option option) throws ParseException {
			var res = new ArrayList<String>();
			for (var i = 0; i < option.nbParams(); i++) {
				System.out.println(args);
				if (!args.isEmpty()) {
					var parameter = args.peekFirst();
					if (parameter.charAt(0) == '-') {
						throw new ParseException("Parameters can't start with '-'", 0);
						//throw new IllegalArgumentException("Parameters can't start with '-'");
					}
					res.add(args.removeFirst());
				} else {
					throw new ParseException("Parameter(s) is/are missing", 0);
				}
			}
			return res;
		}
	}

	private final static class RelaxedStrategy implements ParameterRetrievalStrategy {

		@Override
		public List<String> retriveParameters(Deque<String> args, Option option) {
			var res = new ArrayList<String>();
			for (var i = 0; i < option.nbParams(); i++) {
				if (!args.isEmpty()) {
					var parameter = args.peek();
					if (parameter.charAt(0) == '-') {
						return res;
						//throw new IllegalArgumentException("Parameters can't start with '-'");
					}
					res.add(args.removeFirst());
				}
			}
			return res;
		}
	}

	private final static class OldschoolStrategy implements ParameterRetrievalStrategy {

		@Override
		public List<String> retriveParameters(Deque<String> args, Option option) throws ParseException {
			var res = new ArrayList<String>();
			for (var i = 0; i < option.nbParams(); i++) {
				if (!args.isEmpty()) {
					var parameter = args.peek();
					res.add(args.removeFirst());
				} else {
					throw new ParseException("Parameter(s) is/are missing", 0);
				}
			}
			return res;
		}
	}
}