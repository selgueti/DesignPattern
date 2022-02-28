package com.evilcorp.logger;

import java.util.Objects;
import java.util.function.Predicate;

public class FilteredLogger implements Logger {

    private final Predicate<Level> filter;
    private final Logger logger;

    public FilteredLogger(Logger logger, Predicate<Level> filter){
        this.filter =  Objects.requireNonNull(filter);
        this.logger = Objects.requireNonNull(logger);
    }

    @Override
    public void log(Level level, String message) {
        if(filter.test(level)){
            logger.log(level, message);
        }
    }
}
