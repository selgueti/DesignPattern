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
		assertThrows(NullPointerException.class, () -> parser.registerOption(null, new Action(() -> { ; })));
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
		assertEquals(lst.contains(1), true);
	}
	
	/* Test registerWithParameter */
	@Test
	public void registerWithParameterNullOption() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.registerWithParameter(null, new Action(() -> { ; })));
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
		parser.registerWithParameter("-legacy", new Action((str) -> lst.add(str)));
		String[] strings = {"-legacy", "parameter"};
		parser.process(strings);
		assertEquals(lst.contains("parameter"), true);
	}

	@Test
	public void registerWithParameterGoodOptionNoParameterTest() {
		var parser = new CmdLineParser();
		var lst = new ArrayList<String>();
		parser.registerWithParameter("-legacy", new Action((str) -> lst.add(str)));
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
		assertEquals(file.contains("-legacy"), true);
		assertEquals(lst.size(), 1); /* 'toto' */
		assertEquals(lst.contains("toto"), true);
	}
	
	@Test
	public void processGoodArgumentWithOptionFalse() {
		var parser = new CmdLineParser();
		String[] args = {"-legacy"};
		var lst = new ArrayList<String>();
		parser.registerOption("-test", new Action(() -> lst.add("toto")));
		var file = parser.process(args);
		assertEquals(file.size(), 1); /* '-legacy' */
		assertEquals(file.contains("-legacy"), true);
		assertEquals(lst.size(), 0);
	}
	
	@Test
	public void processGoodArgumentWithOptionAndParameterTrue() {
		var parser = new CmdLineParser();
		String[] args = {"-legacy", "-test", "parameter1"};
		var lst = new ArrayList<String>();
		parser.registerWithParameter("-test", new Action((str) -> lst.add(str)));
		
		var file = parser.process(args);
		assertEquals(file.size(), 1); /* '-legacy' */
		assertEquals(file.contains("-legacy"), true);
		assertEquals(lst.size(), 1);
		assertEquals(lst.contains("parameter1"), true);
	}

	@Test
	public void processGoodArgumentWithOptionAndParameterFalse() {
		var parser = new CmdLineParser();
		String[] args = {"-legacy", "test", "parameter1"};
		var lst = new ArrayList<String>();
		parser.registerWithParameter("-test", new Action((str) -> lst.add(str)));
		
		var file = parser.process(args);
		assertEquals(file.size(), 3); /* '-legacy', 'parameter1' */
		assertEquals(file.contains("-legacy"), true);
		assertEquals(file.contains("test"), true);
		assertEquals(file.contains("parameter1"), true);
		assertEquals(lst.size(), 0);
	}
	
	@Test
	public void processGoodArgumentWithOptionAndNoParameter() {
		var parser = new CmdLineParser();
		String[] args = {"-legacy", "-test"};
		var lst = new ArrayList<String>();
		parser.registerWithParameter("-test", new Action((str) -> lst.add(str)));
		assertThrows(IllegalArgumentException.class, () -> parser.process(args));
	}
}