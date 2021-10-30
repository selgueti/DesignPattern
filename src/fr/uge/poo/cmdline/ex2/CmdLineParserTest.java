package fr.uge.poo.cmdline.ex2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class CmdLineParserTest {
	
	/* Test registerOption */
	@Test
	public void registerNullOption() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.registerOption(null, new Action(() -> {})));
	}

	@Test
	public void registerNullRunnable() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.registerOption("-legacy", null));
	}

	@Test
	public void registerGoodOptionTest() {
		var parser = new CmdLineParser();
		var lst = new ArrayList<Integer>();
		parser.registerOption("-legacy", new Action(() -> lst.add(1)));
		String[] strings = {"-legacy"};
		parser.process(strings);
		assertTrue(lst.contains(1));
	}
	
	/* Test registerWithParameter */
	@Test
	public void registerWithParameterNullOption() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.registerWithParameter(null, new Action(() -> {})));
	}
	
	@Test
	public void registerWithParameterNullConsumer() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.registerWithParameter("-legacy", null));
	}
	
	@Test
	public void registerWithParameterGoodOptionTest() {
		var parser = new CmdLineParser();
		var lst = new ArrayList<String>();
		parser.registerWithParameter("-legacy", new Action(lst::add));
		String[] strings = {"-legacy", "parameter"};
		parser.process(strings);
		assertTrue(lst.contains("parameter"));
	}

	@Test
	public void registerWithParameterGoodOptionNoParameterTest() {
		var parser = new CmdLineParser();
		parser.registerWithParameter("-legacy", new Action((str) -> {}));
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
		parser.registerOption("-test", new Action(() -> lst.add("toto")));
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
		parser.registerOption("-test", new Action(() -> lst.add("toto")));
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
		parser.registerWithParameter("-test", new Action(lst::add));
		
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
		parser.registerWithParameter("-test", new Action(lst::add));
		
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
		parser.registerWithParameter("-test", new Action(str -> {}));
		assertThrows(IllegalArgumentException.class, () -> parser.process(args));
	}
}