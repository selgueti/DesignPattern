package com.evilcorp.stphipster;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public interface STPCommandProcessor {
    void process(HelloCmd cmd);

    void process(StartTimerCmd cmd);

    void process(StopTimerCmd cmd);

    void process(ElapsedTimeCmd cmd);

    default void process(STPCommand cmd) {
        switch (cmd) {
            case HelloCmd helloCmd -> process(helloCmd);
            case StartTimerCmd startTimerCmd -> process(startTimerCmd);
            case StopTimerCmd stopTimerCmd -> process(stopTimerCmd);
            case ElapsedTimeCmd elapsedTimeCmd -> process(elapsedTimeCmd);
        }
    }

    static STPCommandProcessor createSTPCommandProcessor(){
        return new STPCommandProcessor() {

            private final Map<Integer,Long> timers = new HashMap<>();

            @Override
            public void process(HelloCmd cmd) {
                System.out.println("Hello the current date is "+ LocalDateTime.now());
            }

            @Override
            public void process(StartTimerCmd start) {
                var timerId = start.timerId();
                if (timers.get(timerId)!=null){
                    System.out.println("Timer "+timerId+" was already started");
                    return;
                }
                var currentTime =  System.currentTimeMillis();
                timers.put(timerId,currentTime);
                System.out.println("Timer "+timerId+" started");
            }

            @Override
            public void process(StopTimerCmd stop) {
                var timerId = stop.timerId();
                var startTime = timers.get(timerId);
                if (startTime==null){
                    System.out.println("Timer "+timerId+" was never started");
                    return;
                }
                var currentTime =  System.currentTimeMillis();
                System.out.println("Timer "+timerId+" was stopped after running for "+(currentTime - startTime)+"ms");
                timers.put(timerId,null);
            }

            @Override
            public void process(ElapsedTimeCmd elapsed) {
                var currentTime =  System.currentTimeMillis();
                for(var timerId : elapsed.timers()){
                    var startTime = timers.get(timerId);
                    if (startTime==null){
                        System.out.println("Unknown timer "+timerId);
                        return;
                    }
                    System.out.println("Elapsed time on timerId "+timerId+" : "+(currentTime-startTime)+"ms");
                }
            }
        };
    }
}