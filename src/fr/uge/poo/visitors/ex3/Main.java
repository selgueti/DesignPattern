package fr.uge.poo.visitors.ex3;

import java.util.Objects;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        var it = Pattern.compile(" ").splitAsStream("+ * 4 + 1 1 + 2 3").iterator();
        var expr = Expr.parseExpr(it);

        var eval = new ExprVisitor<Integer, Objects>();
        eval.when(BinOp.class, (binOp, acc) ->
                        binOp
                                .operator()
                                .applyAsInt(eval.visit(binOp.left(), null), eval.visit(binOp.right(), null)))
                .when(Value.class, (value, acc) -> value.value());

        System.out.println(eval.visit(expr, null));

        var toString = new ExprVisitor<Object, StringBuilder>();
        toString.when(BinOp.class, (binOp, sb) -> {
            sb.append('(');
            toString.visit(binOp.left(), sb);
            sb.append(' ').append(binOp.opName()).append(' ');
            toString.visit(binOp.right(), sb);
            sb.append(')');
            return null;
        }).when(Value.class, (value, sb) -> sb.append(value.value()));

        var sb = new StringBuilder();
        toString.visit(expr, sb);

        System.out.println(sb);
    }
}