package com.evilcorp.logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class DoubleLogger implements Logger {

    private final List<Logger> loggers = new ArrayList<>();

    public DoubleLogger(Path path, Predicate<Level> filter){
        Objects.requireNonNull(path);
        loggers.add(new FilteredLogger(SystemLogger.getInstance(), filter));
        loggers.add(new FilteredLogger(new PathLogger(path), filter));
    }

    @Override
    public void log(Level level, String message) {
        loggers.forEach(logger -> logger.log(level, message));
    }

    public static void main(String[] args) {
        Logger logger = new DoubleLogger(Path.of("/tmp/logs.txt"), level -> level == Level.WARNING || level == Level.ERROR);
        logger.log(Level.INFO, "HelloWorld");
        logger.log(Level.WARNING, "test : warning");
        logger.log(Level.ERROR, "test : error");
        logger.log(Level.INFO, "ByeWorld");
    }
}
