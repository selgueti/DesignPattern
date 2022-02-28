package fr.uge.poo.cmdline.ex1;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("static-method")
class CmdLineParserTest {

	/* Test registerOption */
	@Test
	public void registerNullOption() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.addFlag(null, System.out::println));
	}

	@Test
	public void registerNullRunnable() {
		var parser = new CmdLineParser();
		assertThrows(NullPointerException.class, () -> parser.addFlag("-legacy", null));
	}

	@Test
	public void registerGoodOptionTest() {
		var parser = new CmdLineParser();
		var lst = new ArrayList<Integer>();
		parser.addFlag("-legacy", () -> lst.add(1));
		String[] strings = { "-legacy" };
		parser.process(strings);
		assertTrue(lst.contains(1));
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
		String[] strings = { "-legacy" };
		var lst = new ArrayList<String>();
		lst.add("-legacy");

		var file = parser.process(strings);
		assertEquals(lst.size(), file.size());
		assertEquals(lst.contains("-legacy"), file.contains("-legacy"));
	}

}