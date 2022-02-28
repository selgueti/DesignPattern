package fr.uge.poo.logger.q0;

public class SystemLogger implements Logger{

    public void log(Level level, String message) {

        System.err.println(level + " " + message);
    }
}