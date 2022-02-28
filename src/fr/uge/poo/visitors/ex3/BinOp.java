package fr.uge.poo.visitors.ex3;

import java.util.Objects;
import java.util.function.IntBinaryOperator;

public record BinOp(Expr left, Expr right, String opName, IntBinaryOperator operator) implements Expr {
    public BinOp {
        Objects.requireNonNull(left);
        Objects.requireNonNull(right);
        Objects.requireNonNull(opName);
        Objects.requireNonNull(operator);
    }
}
