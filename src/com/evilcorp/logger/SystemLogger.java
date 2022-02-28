package com.evilcorp.logger;

import java.util.Objects;

public class SystemLogger implements Logger {

    private SystemLogger(){}
    private static final SystemLogger INSTANCE = new SystemLogger();

    public static SystemLogger getInstance(){
        return INSTANCE;
    }

    @Override
    public void log(Level level, String message) {
        System.err.println(level + " " + message);
    }
}