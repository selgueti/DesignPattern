package com.evilcorp.stp;

public interface STPCommandObserver {
    void onHello();
    void onStart();
    void onStop(long start, long end);
    void onElapsed();
}
