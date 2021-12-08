package fr.uge.poo.cmdline.ex3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class CmdLineParserTest {
	
	
	
	/*Test addFlags*/
	@Test
	public void addNullFlag() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.addFlag(null, () -> {}));
	}

	@Test
	public void addFlagNullRunnable() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.addFlag("-test", null));
	}

	@Test
	public void addGoodFlag() {
		var parser = new CmdLineParser();
		String[] arguments = {"-test"};
		ArrayList<String> lst = new ArrayList<>();
		parser.addFlag("-test", () -> lst.add("yes"));
		parser.process(arguments);
		assertTrue(lst.contains("yes"));
	}
	
	/*Test addOptionWithOneParameter*/
	@Test
	public void addNullOptionWithOneParameter() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.addOptionWithOneParameter(null, parameter -> {}));
	}

	@Test
	public void addOptionWithOneParameterNullConsumer() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.addOptionWithOneParameter("-test", null));
	}

	@Test
	public void addGoodOptionWithOneParameter() {
		var parser = new CmdLineParser();
		String[] arguments = {"-test", "yes"};
		ArrayList<String> lst = new ArrayList<>();
		parser.addOptionWithOneParameter("-test", lst::add);
		parser.process(arguments);
		assertTrue(lst.contains("yes"));
	}

	@Test
	public void addGoodOptionWithoutOneParameter() {
		var parser = new CmdLineParser();
		String[] arguments = {"-test"};
		parser.addOptionWithOneParameter("-test", l -> {});
		assertThrows(IllegalArgumentException.class, () -> parser.process(arguments));
	}
	
	/* Test registerWithParameter */
	@Test
	public void registerNullOption() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.registerWithParameter(null,0 , iterString -> {}));
	}

	@Test
	public void registerNullRunnable() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.registerWithParameter("-legacy", 0, null));
	}

	@Test
	public void registerGoodOptionTest() {
		var parser = new CmdLineParser();
		var lst = new ArrayList<Integer>();
		parser.registerWithParameter("-legacy", 0, iterString -> lst.add(1));
		String[] strings = {"-legacy"};
		parser.process(strings);
		assertTrue(lst.contains(1));
	}
	
	/* Test registerWithParameter */
	@Test
	public void registerWithParameterNullOption() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.registerWithParameter(null, 0, iterString -> {}));
	}
	
	@Test
	public void registerWithParameterNullConsumer() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.registerWithParameter("-legacy", 0, null));
	}
	
	@Test
	public void registerWithParameterGoodOptionTest() {
		var parser = new CmdLineParser();
		var lst = new ArrayList<String>();
		parser.registerWithParameter("-legacy", 1, parameters -> lst.add(parameters.get(0)));
		String[] strings = {"-legacy", "parameter"};
		parser.process(strings);
		assertTrue(lst.contains("parameter"));
	}

	@Test
	public void registerWithParameterGoodOptionNoParameterTest() {
		var parser = new CmdLineParser();
		parser.registerWithParameter("-legacy", 1, iterString -> {});
		String[] strings = {"-legacy"};
		assertThrows(IllegalArgumentException.class, () -> parser.process(strings));
	}
	
	/* Test process */
	@Test
	public void processShouldFailFastOnNullArgument() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.process(null));
	}

	@Test
	public void processGoodArgument() {
		var parser = new CmdLineParser();
		String[] args = {"-legacy"};
		var lst = new ArrayList<String>();
		lst.add("-legacy");
		
		var file = parser.process(args);
		assertEquals(lst.size(), file.size());
		assertEquals(lst.contains("-legacy"), file.contains("-legacy"));
	}

	@Test
	public void processGoodArgumentWithOptionTrue() {
		var parser = new CmdLineParser();
		String[] args = {"-legacy", "-test"};
		var lst = new ArrayList<String>();
		parser.registerWithParameter("-test", 0, iterString -> lst.add("toto"));
		var file = parser.process(args);
		assertEquals(file.size(), 1); /* '-legacy' */
		assertTrue(file.contains("-legacy"));
		assertEquals(lst.size(), 1); /* 'toto' */
		assertTrue(lst.contains("toto"));
	}
	
	@Test
	public void processGoodArgumentWithOptionFalse() {
		var parser = new CmdLineParser();
		String[] args = {"-legacy"};
		var lst = new ArrayList<String>();
		parser.registerWithParameter("-test", 0, iterString -> lst.add("toto"));
		var file = parser.process(args);
		assertEquals(file.size(), 1); /* '-legacy' */
		assertTrue(file.contains("-legacy"));
		assertEquals(lst.size(), 0);
	}
	
	@Test
	public void processGoodArgumentWithOptionAndParameterTrue() {
		var parser = new CmdLineParser();
		String[] args = {"-legacy", "-test", "parameter1"};
		var lst = new ArrayList<String>();
		parser.registerWithParameter("-test", 1, parameters -> lst.add(parameters.get(0)));
		
		var file = parser.process(args);
		assertEquals(file.size(), 1); /* '-legacy' */
		assertTrue(file.contains("-legacy"));
		assertEquals(lst.size(), 1);
		assertTrue(lst.contains("parameter1"));
	}

	@Test
	public void processGoodArgumentWithOptionAndParameterFalse() {
		var parser = new CmdLineParser();
		String[] args = {"-legacy", "test", "parameter1"};
		var lst = new ArrayList<String>();
		parser.registerWithParameter("-test", 1, parameters -> lst.add(parameters.get(0)));
		var file = parser.process(args);
		assertEquals(file.size(), 3); /* '-legacy', 'parameter1' */
		assertTrue(file.contains("-legacy"));
		assertTrue(file.contains("test"));
		assertTrue(file.contains("parameter1"));
		assertEquals(lst.size(), 0);
	}
	
	@Test
	public void processGoodArgumentWithOptionAndNoParameter() {
		var parser = new CmdLineParser();
		String[] args = {"-legacy", "-test"};
		parser.registerWithParameter("-test", 1, iterString -> {});
		assertThrows(IllegalArgumentException.class, () -> parser.process(args));
	}

}