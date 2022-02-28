package com.evilcorp.stp;

public sealed interface STPCommand permits StartTimerCmd, StopTimerCmd, HelloCmd, ElapsedTimeCmd {
    void accept(STPVisitor v);
}
