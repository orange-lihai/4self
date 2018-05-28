package biz.churen.self.pl.simple.token;

import java.util.Map;

public interface Struct {
    public String toStr();
    public boolean reducible();
    // public Struct reduce();
    public Struct reduce(Map<String, Struct> env);

    // big-step semantic
    public Struct evaluate(Map<String, Struct> env);
}
