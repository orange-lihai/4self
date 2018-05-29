package biz.churen.self.pl.simple.token;

import java.util.Map;

public class Variable implements Struct, Reducible {
    public String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toStr() {
        return name;
    }

    @Override
    public boolean reducible() {
        return true;
    }

    @Override
    public Struct reduce(Map<String, Struct> env) {
        return env.get(name);
    }

    @Override
    public Struct evaluate(Map<String, Struct> env) {
        return env.get(name);
    }
}
