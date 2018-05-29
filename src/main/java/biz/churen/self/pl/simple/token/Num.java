package biz.churen.self.pl.simple.token;

import java.util.Map;

public class Num implements Struct, UnReducible {
    public Integer value;

    public Num(Integer value) {
        this.value = value;
    }


    @Override
    public String toStr() {
        return "`" + String.valueOf(value) + "`";
    }

    @Override
    public boolean reducible() {
        return false;
    }

    @Override
    public Struct reduce(Map<String, Struct> env) {
        return null;
    }

    @Override
    public Struct evaluate(Map<String, Struct> env) {
        return this;
    }
}
