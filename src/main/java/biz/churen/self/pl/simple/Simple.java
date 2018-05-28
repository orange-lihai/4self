package biz.churen.self.pl.simple;

import biz.churen.self.pl.simple.token.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Understanding Computation: From Simple Machines to Impossible Programs
 */
public class Simple {
    public Struct expression;
    public Map<String, Struct> env;
    public Simple(Struct exp) {
        this.expression = exp;
    }

    public Simple(Struct exp, Map<String, Struct> env) {
        this.expression = exp;
        this.env = env;
    }

    public void run() {
        while (expression.reducible()) {
            System.out.println();
            System.out.print(    expression.toStr()    );
            System.out.print(    " ------ "    );
            System.out.print(    envToStr()    );
            expression = expression.reduce(env);
        }
        System.out.println();
        System.out.print(    expression.toStr()    );
        System.out.print(    " ------ "    );
        System.out.print(    envToStr()    );
    }

    public String envToStr() {
        if (null == env) { return "{}"; }
        StringBuffer sb = new StringBuffer("{");
        for (String k : env.keySet()) {
            if (1 != sb.length()) {
                sb.append(", ");
            }
            sb.append(k);
            sb.append("=>");
            Struct o = env.get(k);
            if (o instanceof Num) {
                sb.append(((Num) (env.get(k))).value);
            } else if (o instanceof Bool) {
                sb.append(((Bool) (env.get(k))).value);
            } else {
                sb.append(String.valueOf(o));
            }

        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        // System.out.println( new Num(5).toStr() );
        // System.out.println( new Num(8).reducible() );
        // System.out.println( new Add(new Num(6), new Num(7)).reducible() );

        Struct exp = new Add(
                new Multiply(new Num(1), new Num(2)),
                new Multiply(new Num(3), new Num(4))
        );
        Simple simple = new Simple(exp);
        simple.run();

        exp = new LessThan(
                new Multiply(new Num(1), new Num(2)),
                new Multiply(new Num(3), new Num(4))
        );
        simple = new Simple(exp);
        simple.run();

        Map<String, Struct> env = new HashMap<>();
        env.put("b", new Bool(true));
        env.put("x", new Num(5));
        env.put("y", new Num(6));
        Sequence seq = new Sequence(
            new Assign("z", new Add(new Variable("x"), new Variable("y"))),
            new Assign("x", new Add(new Variable("z"), new Variable("y")))
        );
        If _if = new If(new Variable("b"),
                new Assign("rs", new Add(new Num(1), new Num(1))),
                new Sequence(seq, new Assign("rs", new Add(new Num(2), new Variable("x"))))
        );
        simple = new Simple(_if, env);
        simple.run();


        env.clear();
        env.put("x", new Num(1));
        simple = new Simple(
            new While(
                    new LessThan(new Variable("x"), new Num(7)),
                    new Assign("x", new Multiply(new Variable("x"), new Num(2)))
            ), env
        );
        simple.run();

    }

}
