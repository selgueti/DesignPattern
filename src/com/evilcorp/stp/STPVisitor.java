package com.evilcorp.stp;

public interface STPVisitor {

    void visit(HelloCmd hello);
    void visit(StartTimerCmd hello);
    void visit(StopTimerCmd hello);
    void visit(ElapsedTimeCmd hello);

}
