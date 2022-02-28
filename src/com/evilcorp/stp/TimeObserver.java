package com.evilcorp.stp;

import java.util.HashMap;
import java.util.Map;

public class TimeObserver implements STPCommandObserver{

    private long totalTimeRun = 0;
    private long nbRun = 0;
    private final Map<String, Integer> cmdCount = new HashMap<>();

    @Override
    public void onHello() {
        incrementCounter("Hello");
    }

    @Override
    public void onStart() {
        incrementCounter("Start");
    }

    @Override
    public void onStop(long start, long end) {
        nbRun++;
        totalTimeRun += end - start;
        incrementCounter("Stop");
    }

    @Override
    public void onElapsed() {
        incrementCounter("Elapsed");
    }

    private void incrementCounter(String cmd){
        cmdCount.merge(cmd, 1, Integer::sum);
    }

    private double getAverageTimeElapsed(){
        return totalTimeRun * 1.0 / nbRun;
    }

    public void displayInfo() {
        System.out.println("number of command calls per calls : ");
        cmdCount.entrySet().forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));
        System.out.println("average elapsed time between respective start and stops : " + getAverageTimeElapsed() + " ms");
    }
}
