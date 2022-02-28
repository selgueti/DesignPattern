package fr.uge.poo.visitors.expr;

public interface ExprVisitor <E> {
    E visitValue(Value value);
    E visitBinOp(BinOp binOp);
}