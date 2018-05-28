package biz.churen.self.pl.simple.token;

import java.util.Map;

public class Multiply implements Struct, Reducible {
    Struct left;
    Struct right;

    public Multiply(Struct left, Struct right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toStr() {
        return left.toStr() + " * " + right.toStr();
    }

    public boolean reducible() {
        return true;
    }

    @Override
    public Struct reduce(Map<String, Struct> env) {
        if (left.reducible()) {
            return new Multiply(left.reduce(env), right);
        } else if (right.reducible()) {
            return new Multiply(left, right.reduce(env));
        } else {
            return new Num(((Num) left).value * ((Num) right  ).value);
        }
    }

    @Override
    public Struct evaluate(Map<String, Struct> env) {
        return new Num(
                ((Num)(left.evaluate(env))).value * ((Num)(right.evaluate(env))).value
        );
    }
}
