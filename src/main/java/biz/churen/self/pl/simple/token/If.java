package biz.churen.self.pl.simple.token;

import java.util.Map;

public class If implements Struct {
    public Struct condition;
    public Struct consequence;
    public Struct alternative;

    public If(Struct condition, Struct consequence, Struct alternative) {
        this.condition = condition;
        this.consequence = consequence;
        this.alternative = alternative;
    }

    @Override
    public String toStr() {
        return ("if ( " + condition.toStr() + " ) { " + consequence.toStr() + " } else { " + alternative.toStr() + " } ");
    }

    @Override
    public boolean reducible() {
        return true;
    }

    @Override
    public Struct reduce(Map<String, Struct> env) {
        if (condition.reducible()) {
            return new If(condition.reduce(env), consequence, alternative);
        } else {
            if (((Bool)condition).value) {
                return new Sequence(consequence);
            } else {
                return new Sequence(alternative);
            }
        }
    }

    @Override
    public Struct evaluate(Map<String, Struct> env) {
        Struct cond = condition.evaluate(env);
        if (cond instanceof Bool && ((Bool) cond).value) {
            return consequence.evaluate(env);
        } else if (cond instanceof Bool && !(((Bool) cond).value)) {
            return alternative.evaluate(env);
        } else {
            return new DoNothing();
        }
    }
}
