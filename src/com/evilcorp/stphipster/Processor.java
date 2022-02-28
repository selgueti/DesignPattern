package com.evilcorp.stphipster;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Processor implements STPCommandProcessor{

    private final Map<Integer,Long> timers = new HashMap<>();

    @Override
    public void process(HelloCmd cmd) {
        System.out.println("Hello the current date is "+ LocalDateTime.now());
    }

    @Override
    public void process(StartTimerCmd cmd) {

    }

    @Override
    public void process(StopTimerCmd cmd) {

    }

    @Override
    public void process(ElapsedTimeCmd cmd) {

    }
}
