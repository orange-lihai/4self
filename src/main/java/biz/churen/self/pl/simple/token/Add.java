package biz.churen.self.pl.simple.token;

import java.util.Map;

public class Add implements Struct, Reducible {
    public Struct left;
    public Struct right;

    public Add(Struct left, Struct right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public String toStr() {
        return left.toStr() + " + " + right.toStr();
    }

    @Override
    public boolean reducible() {
        return true;
    }

    @Override
    public Struct reduce(Map<String, Struct> env) {
        if (left.reducible()) {
            return new Add(left.reduce(env), right);
        } else if (right.reducible()) {
            return new Add(left, right.reduce(env));
        } else {
            return new Num(((Num) left).value + ((Num) right).value);
        }
    }

    @Override
    public Struct evaluate(Map<String, Struct> env) {
        return new Num(((Num)(left.evaluate(env))).value + ((Num)(right.evaluate(env))).value);
    }
}
