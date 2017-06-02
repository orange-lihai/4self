package biz.churen.self.web;

import biz.churen.self.server.strategy.SelfBody;
import biz.churen.self.server.strategy.SelfController;
import biz.churen.self.server.strategy.SelfMapping;
import biz.churen.self.server.strategy.SelfParam;

import java.util.HashMap;
import java.util.Map;

@SelfController
public class HelloController {

  @SelfMapping(url = "/hello")
  public Object sayHello(@SelfBody Map<String, Object> params
      , @SelfParam(name = "id") String id
  ) {
    Map<String, Object> r = new HashMap<>();
    return r;
  }

}
