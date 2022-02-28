package com.evilcorp.stp;

import java.time.LocalDateTime;
import java.util.*;

public class Visitor implements STPVisitor{

    private final Map<Integer,Long> timers = new HashMap<>();
    private final List<STPCommandObserver> observers = new ArrayList<>();

    @Override
    public void visit(HelloCmd hello) {
        System.out.println("Hello the current date is "+ LocalDateTime.now());
        notifyHello();
    }

    @Override
    public void visit(StartTimerCmd start) {
        var timerId = start.getTimerId();
        if (timers.get(timerId)!=null){
            System.out.println("Timer "+timerId+" was already started");
            return;
        }
        var currentTime =  System.currentTimeMillis();
        timers.put(timerId,currentTime);
        System.out.println("Timer "+timerId+" started");
        notifyStart();
    }

    @Override
    public void visit(StopTimerCmd stop) {
        var timerId = stop.getTimerId();
        var startTime = timers.get(timerId);
        if (startTime==null){
            System.out.println("Timer "+timerId+" was never started");
            return;
        }
        var currentTime =  System.currentTimeMillis();
        System.out.println("Timer "+timerId+" was stopped after running for "+(currentTime-startTime)+"ms");
        timers.put(timerId,null);
        notifyStop(startTime, currentTime);
    }

    @Override
    public void visit(ElapsedTimeCmd elapsed) {
        var currentTime =  System.currentTimeMillis();
        for(var timerId : elapsed.getTimers()){
            var startTime = timers.get(timerId);
            if (startTime==null){
                System.out.println("Unknown timer "+timerId);
                return;
            }
            System.out.println("Elapsed time on timerId "+timerId+" : "+(currentTime-startTime)+"ms");
        }
        notifyElapsed();
    }

    public void register(STPCommandObserver observer){
        Objects.requireNonNull(observer);
        observers.add(observer);
    }

    public void unregister(STPCommandObserver observer){
        Objects.requireNonNull(observer);
        observers.remove(observer);
    }

    private void notifyHello(){
        for(var observer : observers){
            observer.onHello();
        }
    }

    private void notifyStart(){
        for(var observer : observers){
            observer.onStart();
        }
    }

    private void notifyStop(long start, long end){
        for(var observer : observers){
            observer.onStop(start, end);
        }
    }

    private void notifyElapsed(){
        for(var observer : observers){
            observer.onElapsed();
        }
    }
}
