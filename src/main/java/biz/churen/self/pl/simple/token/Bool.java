package biz.churen.self.pl.simple.token;

import java.util.Map;

public class Bool implements Struct, UnReducible {
    public Boolean value;

    public Bool(Boolean value) {
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
        return this;
    }

    @Override
    public Struct evaluate(Map<String, Struct> env) {
        return this;
    }
}
