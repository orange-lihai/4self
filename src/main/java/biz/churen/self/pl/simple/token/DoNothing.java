package biz.churen.self.pl.simple.token;

import java.util.Map;

public class DoNothing extends Assign {
    public String toStr() {
        return "do-nothing";
    }

    public boolean reducible() {
        return false;
    }

    @Override
    public Struct evaluate(Map<String, Struct> env) {
        // Do Nothing
        return this;
    }
}
