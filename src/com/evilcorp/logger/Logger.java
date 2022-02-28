package com.evilcorp.logger;

public interface Logger {

    enum Level {
        ERROR, WARNING, INFO
    }

    void log(Level level, String message);
}
