package fr.uge.poo.logger.q0;

public interface Logger {
    void log(Level level, String message);

    enum Level {
        ERROR, WARNING, INFO
    }
}
