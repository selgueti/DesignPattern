package fr.uge.poo.visitors.ex3;

import java.util.HashMap;

import java.util.Objects;
import java.util.function.BiFunction;


public class ExprVisitor <U, V> {
    private final HashMap<Class<? extends Expr>, BiFunction<Expr, V, U>> visitor = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T extends Expr> ExprVisitor<U, V>  when(Class<T> expr, BiFunction<T, V, U> action){
        Objects.requireNonNull(expr);
        Objects.requireNonNull(action);
        visitor.put(expr, (BiFunction<Expr, V, U>)action);
        return this;
    }

    public U visit(Expr expr, V acc){
        Objects.requireNonNull(expr);
        var action = visitor.get(expr.getClass());
        if(action == null){
            throw new IllegalArgumentException("No visitor action for " + expr.getClass());
        }
        return action.apply(expr, acc);

    }
}