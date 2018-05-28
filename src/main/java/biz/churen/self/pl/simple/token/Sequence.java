package biz.churen.self.pl.simple.token;

import java.util.Map;

public class Sequence implements Struct {
    public Struct first;
    public Struct second;

    public Sequence(Struct first, Struct second) {
        this.first = first;
        this.second = second;
    }

    public Sequence(Struct second) {
        this.first = new DoNothing();
        this.second = second;
    }

    @Override
    public String toStr() {
        return first.toStr() + "; " + second.toStr() + "; ";
    }

    @Override
    public boolean reducible() {
        return (first.reducible() || second.reducible());
    }

    @Override
    public Struct reduce(Map<String, Struct> env) {
        if (!(first instanceof DoNothing)) {
            return new Sequence(first.reduce(env), second);
        } else {
            if (second.reducible()) {
                return new Sequence(second.reduce(env));
            } else {
                return new DoNothing();
            }
        }
    }

    @Override
    public Struct evaluate(Map<String, Struct> env) {
        first.evaluate(env);
        return second.evaluate(env);
    }
}
