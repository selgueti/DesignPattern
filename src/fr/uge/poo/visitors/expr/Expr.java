package fr.uge.poo.visitors.expr;

import java.util.Iterator;

public interface Expr {

    int accept(EvalExprVisitor visitor);
    String accept(ToStringVisitor visitor);

    static Expr parseExpr(Iterator<String> it) {
        if (!it.hasNext()) {
            throw new IllegalArgumentException("no more tokens");
        }
        String token = it.next();
        switch(token) {
            case "+":
                return new BinOp(parseExpr(it), parseExpr(it), token, Integer::sum);
            case "-":
                return new BinOp(parseExpr(it), parseExpr(it), token, (a, b) -> a - b);
            case "*":
                return new BinOp(parseExpr(it), parseExpr(it), token, (a, b) -> a * b);
            case "/":
                return new BinOp(parseExpr(it), parseExpr(it), token, (a, b) -> a / b);
            default:
                return new Value(Integer.parseInt(token));
        }
    }

}
