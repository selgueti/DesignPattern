package fr.uge.poo.visitors.expr;

public class Value implements Expr {
    private final int value;

    public Value(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public int accept(EvalExprVisitor visitor) {
        return visitor.visitValue(this);
    }

    @Override
    public String accept(ToStringVisitor visitor) {
        return visitor.visitValue(this);
    }
}
