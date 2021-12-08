package fr.uge.poo.cmdline.ex7;

import fr.uge.poo.cmdline.ex7.Option.OptionBuilder;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("static-method")
class CmdLineParserTest {

	/* Test addFlags */
	@Test
	public void addNullFlag() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.addFlag(null, () -> {
		}));
	}

	@Test
	public void addFlagNullRunnable() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.addFlag("-test", null));
	}

	@Test
	public void addGoodFlag() throws ParseException {
		var parser = new CmdLineParser();
		String[] arguments = { "-test" };
		ArrayList<String> lst = new ArrayList<>();
		parser.addFlag("-test", () -> lst.add("yes"));
		parser.process(arguments);
		assertTrue(lst.contains("yes"));
	}

	/* Test addOptionWithOneParameter */
	@Test
	public void addNullOptionWithOneParameter() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.addOptionWithOneParameter(null, parameter -> {
		}));
	}

	@Test
	public void addOptionWithOneParameterNullConsumer() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.addOptionWithOneParameter("-test", null));
	}

	@Test
	public void addGoodOptionWithOneParameter() throws ParseException {
		var parser = new CmdLineParser();
		String[] arguments = { "-test", "yes" };
		ArrayList<String> lst = new ArrayList<>();
		parser.addOptionWithOneParameter("-test", lst::add);
		parser.process(arguments);
		assertTrue(lst.contains("yes"));
	}

	@Test
	public void addGoodOptionWithoutOneParameter() {
		var parser = new CmdLineParser();
		String[] arguments = { "-test" };
		parser.addOptionWithOneParameter("-test", parameter -> {
		});
		assertThrows(ParseException.class, () -> parser.process(arguments));
	}

	/* Test registerWithParameter */
	@Test
	public void registerNullOption() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class,
				() -> parser.registerWithParameter(null, 0, iterString -> {
				}));
	}

	@Test
	public void registerNullRunnable() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.registerWithParameter("-legacy", 0, null));
	}

	@Test
	public void registerGoodOptionTest() throws ParseException {
		var parser = new CmdLineParser();
		var lst = new ArrayList<Integer>();
		parser.registerWithParameter("-legacy", 0, iterString -> lst.add(1));
		String[] strings = { "-legacy" };
		parser.process(strings);
		assertTrue(lst.contains(1));
	}

	/* Test registerWithParameter */
	@Test
	public void registerWithParameterNullOption() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class,
				() -> parser.registerWithParameter(null, 0, iterString -> {
				}));
	}

	@Test
	public void registerWithParameterNullConsumer() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.registerWithParameter("-legacy", 0, null));
	}

	@Test
	public void registerWithParameterGoodOptionTest() throws ParseException {
		var parser = new CmdLineParser();
		var lst = new ArrayList<String>();
		parser.registerWithParameter("-legacy", 1, parameters -> lst.add(parameters.get(0)));
		String[] strings = { "-legacy", "parameter" };
		parser.process(strings);
		assertTrue(lst.contains("parameter"));
	}

	@Test
	public void registerWithParameterGoodOptionNoParameterTest() {
		var parser = new CmdLineParser();
		parser.registerWithParameter("-legacy", 1, iterString -> {
		});
		String[] strings = { "-legacy" };
		assertThrows(ParseException.class, () -> parser.process(strings));
	}

	@Test
	public void registerRequiredOptionButMissing() {
		var parser = new CmdLineParser();
		Option option = new OptionBuilder("-legacy", 1, l -> {}).required().build();
		parser.addOption(option);
		String[] strings = { "legacy" };
		assertThrows(ParseException.class, () -> parser.process(strings));
	}

	@Test
	public void registerRequiredOptionButMissingParameter() {
		var parser = new CmdLineParser();
		parser.registerWithParameter("-legacy", 1, iterString -> {
		});
		String[] strings = { "-legacy" };
		assertThrows(ParseException.class, () -> parser.process(strings));
	}

	@Test
	public void registerRequiredOptionAndPresent() throws ParseException {
		var parser = new CmdLineParser();
		parser.registerWithParameter("-border-width", 1, iterString -> {
		});
		String[] strings = { "-border-width", "400" };
		parser.process(strings);
	}

	@Test
	public void registerTwoOptionButSecondIsArgumentOfFirst() {
		var parser = new CmdLineParser();
		parser.registerWithParameter("-border-width", 1, iterString -> {
		});
		parser.registerWithParameter("-legacy", 0, iterString -> {
		});
		String[] strings = { "-border-width", "-legacy" };
		assertThrows(ParseException.class, () -> parser.process(strings));
	}

	/* Test process */
	@Test
	public void processShouldFailFastOnNullArgument() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.process(null));
	}

	@Test
	public void processGoodArgument() throws ParseException {
		var parser = new CmdLineParser();
		String[] args = { "legacy" };
		var file = parser.process(args);
		assertEquals(1, file.size());
		assertTrue(file.contains("legacy"));
	}

	@Test
	public void processGoodArgumentWithOptionTrue() throws ParseException {
		var parser = new CmdLineParser();
		String[] args = { "legacy", "-test" };
		var lst = new ArrayList<String>();
		parser.registerWithParameter("-test", 0, iterString -> lst.add("toto"));
		var file = parser.process(args);
		assertEquals(file.size(), 1); /* 'legacy' */
		assertTrue(file.contains("legacy"));
		assertEquals(lst.size(), 1); /* 'toto' */
		assertTrue(lst.contains("toto"));
	}

	@Test
	public void processGoodArgumentWithOptionFalse() throws ParseException {
		var parser = new CmdLineParser();
		String[] args = { "legacy" };
		var lst = new ArrayList<String>();
		parser.registerWithParameter("-test", 0, iterString -> lst.add("toto"));
		var file = parser.process(args);
		assertEquals(file.size(), 1); /* 'legacy' */
		assertTrue(file.contains("legacy"));
		assertEquals(lst.size(), 0);
	}

	@Test
	public void processGoodArgumentWithOptionAndParameterTrue() throws ParseException {
		var parser = new CmdLineParser();
		String[] args = { "legacy", "-test", "parameter1" };
		var lst = new ArrayList<String>();
		parser.registerWithParameter("-test", 1, parameters -> lst.add(parameters.get(0)));

		var file = parser.process(args);
		assertEquals(file.size(), 1); /* '-legacy' */
		assertTrue(file.contains("legacy"));
		assertEquals(lst.size(), 1);
		assertTrue(lst.contains("parameter1"));
	}

	@Test
	public void processGoodArgumentWithOptionAndParameterFalse() throws ParseException {
		var parser = new CmdLineParser();
		String[] args = { "legacy", "test", "parameter1" };
		var lst = new ArrayList<String>();
		parser.registerWithParameter("-test", 1, parameters -> lst.add(parameters.get(0)));
		var file = parser.process(args);
		assertEquals(file.size(), 3); /* '-legacy', 'parameter1' */
		assertTrue(file.contains("legacy"));
		assertTrue(file.contains("test"));
		assertTrue(file.contains("parameter1"));
		assertEquals(lst.size(), 0);
	}

	@Test
	public void processGoodArgumentWithOptionAndNoParameter() {
		var parser = new CmdLineParser();
		String[] args = { "-legacy", "-test" };
		parser.registerWithParameter("-test", 1, iterString -> {
		});
		assertThrows(IllegalArgumentException.class, () -> parser.process(args));
	}


	/* Given test ex 6 */
	@Test
	public void processRequiredOption() {
		var cmdParser = new CmdLineParser();
		var option= new OptionBuilder("-test",0, l->{}).required().build();
		cmdParser.addOption(option);
		cmdParser.addFlag("-test1",() -> {});
		String[] arguments = {"-test1","a","b"};
		assertThrows(ParseException.class,()-> cmdParser.process(arguments));
	}


	@Test
	public void processConflicts2() {
		var cmdParser = new CmdLineParser();
		var option= new OptionBuilder("-test",0, l->{}).conflictWith("-test1").build();
		cmdParser.addOption(option);
		var option2= new OptionBuilder("-test1",0, l->{}).build();
		cmdParser.addOption(option2);
		String[] arguments = {"-test1","-test"};
		assertThrows(ParseException.class,()-> cmdParser.process(arguments));
	}

	@Test
	public void processConflictsAndAliases() {
		var cmdParser = new CmdLineParser();
		var option= new OptionBuilder("-test",0, l->{}).conflictWith("-test2").build();
		cmdParser.addOption(option);
		var option2= new OptionBuilder("-test1",0, l->{}).alias("-test2").build();
		cmdParser.addOption(option2);
		String[] arguments = {"-test1","-test"};
		assertThrows(ParseException.class,()-> cmdParser.process(arguments));
	}

	@Test
	public void processConflictsAndAliases2() {
		var cmdParser = new CmdLineParser();
		var option= new OptionBuilder("-test",0, l->{}).conflictWith("-test2").build();
		cmdParser.addOption(option);
		var option2= new OptionBuilder("-test1",0, l->{}).alias("-test2").build();
		cmdParser.addOption(option2);
		String[] arguments = {"-test","-test1"};
		assertThrows(ParseException.class,()-> cmdParser.process(arguments));
	}

	/* Given test ex 7 */
	@Test
	public void processPolicyStandard() {
		var hosts = new ArrayList<String>();
		var cmdParser = new CmdLineParser();
		var optionHosts= new OptionBuilder("-hosts",2, hosts::addAll).build();
		cmdParser.addOption(optionHosts);
		cmdParser.addFlag("-legacy",()->{});
		String[] arguments = {"-hosts","localhost","-legacy","file"};
		assertThrows(ParseException.class,()->{cmdParser.process(arguments,CmdLineParser.STANDARD);});
	}

	@Test
	public void processPolicyRelaxed() {
		var hosts = new ArrayList<String>();
		var cmdParser = new CmdLineParser();
		var optionHosts= new OptionBuilder("-hosts",2, hosts::addAll).build();
		cmdParser.addOption(optionHosts);
		cmdParser.addFlag("-legacy",()->{});
		String[] arguments = {"-hosts","localhost","-legacy","file"};
		try {
			cmdParser.process(arguments,CmdLineParser.RELAXED);
		} catch (ParseException e) {
			System.out.println("Conflicts between option"); // Ne devrait pas arrivé
			return;
		}
		assertEquals(1,hosts.size());
		assertEquals("localhost",hosts.get(0));
	}


	@Test
	public void processPolicyRelaxed2() {
		var hosts = new ArrayList<String>();
		var cmdParser = new CmdLineParser();
		final boolean[] isLegacy = {false};
		var optionHosts= new OptionBuilder("-hosts",2, hosts::addAll).build();
		cmdParser.addOption(optionHosts);
		cmdParser.addFlag("-legacy",()-> isLegacy[0] = true);

		String[] arguments = {"-hosts","localhost","-legacy","file"};
		try {
			cmdParser.process(arguments,CmdLineParser.RELAXED);
		} catch (ParseException e) {
			System.out.println("Conflicts between option"); // Ne devrait pas arrivé
			return;
		}
		assertEquals(1,hosts.size());
		assertEquals("localhost",hosts.get(0));
		assertTrue(isLegacy[0]);
	}



	@Test
	public void processPolicyOldSchool() {
		var hosts = new ArrayList<String>();
		var cmdParser = new CmdLineParser();
		var optionHosts= new OptionBuilder("-hosts",2, hosts::addAll).build();
		cmdParser.addOption(optionHosts);
		cmdParser.addFlag("-legacy",()->{});
		String[] arguments = {"-hosts","localhost","-legacy","file"};
		try {
			cmdParser.process(arguments,CmdLineParser.OLDSCHOOL);
		} catch (ParseException e) {
			System.out.println("Conflicts between option"); // Ne devrait pas arrivé
			return;
		}
		assertEquals(2,hosts.size());
		assertEquals("localhost",hosts.get(0));
		assertEquals("-legacy",hosts.get(1));
	}
}