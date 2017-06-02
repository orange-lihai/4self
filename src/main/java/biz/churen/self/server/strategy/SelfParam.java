package biz.churen.self.server.strategy;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SelfParam {
  String name();
}
