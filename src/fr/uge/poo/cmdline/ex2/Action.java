package fr.uge.poo.cmdline.ex2;

import java.util.Objects;
import java.util.function.Consumer;

public class Action {

	private final Runnable runnable;
	private final Consumer<String> consumer;

	public Action(Consumer<String> consumer) {
		Objects.requireNonNull(consumer);
		this.consumer = consumer;
		this.runnable = null;
	}

	public Action(Runnable runnable) {
		Objects.requireNonNull(runnable);
		this.runnable = runnable;
		this.consumer = null;
	}

	public boolean isRunnable() {
		return runnable != null;
	}

	public void make() {
		if (isRunnable()) {
			runnable.run();
		} else {
			throw new IllegalStateException("Consumer and you don't given parameter");
		}
	}

	public void make(String parameter) {
		if (!isRunnable()) {
			consumer.accept(parameter);
		} else {
			throw new IllegalStateException("Runnable and you given parameter");
		}
	}
}
