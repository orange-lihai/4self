package biz.churen.self.web;

import biz.churen.self.model.bean.TBSystem;
import biz.churen.self.model.rdb.*;
import biz.churen.self.server.strategy.SelfBody;
import biz.churen.self.server.strategy.SelfController;
import biz.churen.self.server.strategy.SelfMapping;
import biz.churen.self.server.strategy.SelfParam;
import biz.churen.self.util.JsonUtil;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@SelfController
public class HelloController {

  @SelfMapping(url = "/hello")
  public Object sayHello(@SelfBody Map<String, Object> params
      , @SelfParam(name = "id") String id
  ) throws Exception {

    IQuery<TBSystem> q = new TableQuery<>(TBSystem.class);

    q.appendCondition(q.mf.to().getUrl(), LogicType.LIKE, "ABC");
    q.appendCondition(q.mf.to().getShowName(), LogicType.LIKE, "ABC");
    q.appendCondition(q.mf.to().getD(), LogicType.LIKE, "ABC");

    int a = q.table.queryCount(q);

    IQuery<TBSystem> q2 = new TableQuery<>(TBSystem.class);
    q2.appendOrder(q2.mf.to().getShowName(), OrderType.ASC);
    q2.appendOrder(q2.mf.to().getShowName(), OrderType.DESC);
    q2.appendOrder(q2.mf.to().getUrl(), OrderType.DESC);
    List<TBSystem> rs2 = q2.table.queryList(q2);

    IQuery<TBSystem> q3 = new TableQuery<>(TBSystem.class);
    Page<TBSystem> page = new Page<>(2, 13);
    q3.table.queryPage(q3, page);

    IQuery<TBSystem> q4 = new TableQuery<>(TBSystem.class);
    q4.table.insert(Arrays.asList(new TBSystem()));

    return a + " " + JsonUtil.toJSON(page);
  }

}
