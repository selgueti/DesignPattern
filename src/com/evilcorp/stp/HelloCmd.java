package com.evilcorp.stp;

public final class HelloCmd implements STPCommand {


    @Override
    public void accept(STPVisitor v) {
        v.visit(this);
    }
}
