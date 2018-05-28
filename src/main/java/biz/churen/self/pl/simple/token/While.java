package biz.churen.self.pl.simple.token;

import java.util.Map;

public class While implements Struct {
    public Struct condition;
    public Struct body;

    public While(Struct condition, Struct body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toStr() {
        return "while ( " + condition.toStr() + " ) { "+ body.toStr() +" }";
    }

    @Override
    public boolean reducible() {
        return true;
    }

    @Override
    public Struct reduce(Map<String, Struct> env) {
        return new If(condition, new Sequence(body, this), new DoNothing());
    }

    @Override
    public Struct evaluate(Map<String, Struct> env) {
        Struct cond = condition.evaluate(env);
        if (cond instanceof Bool && ((Bool)cond).value) {
            body.evaluate(env);
            return this.evaluate(env);
        } else {
            return new DoNothing();
        }
    }
}
