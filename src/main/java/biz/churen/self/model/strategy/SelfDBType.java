package biz.churen.self.model.strategy;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface SelfDBType {
  /** mysql, oracle, sqlServer, etc   */
  String name();
}
