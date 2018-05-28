package biz.churen.self.pl.simple.token;

import java.util.Map;

public class Assign implements Struct {

    public String name;
    public Struct expression;

    public Assign(String name, Struct expression) {
        this.name = name;
        this.expression = expression;
    }

    public Assign() {
    }

    @Override
    public String toStr() {
        return name + " = " + expression.toStr();
    }

    @Override
    public boolean reducible() {
        return true;
    }

    @Override
    public Struct reduce(Map<String, Struct> env) {
        if (expression.reducible()) {
            return new Assign(name, expression.reduce(env));
        } else {
            env.put(name, expression);
            return new DoNothing();
        }
    }

    @Override
    public Struct evaluate(Map<String, Struct> env) {
        return env.put(name, expression.evaluate(env));
    }
}
