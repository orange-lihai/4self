package biz.churen.self.model.rdb;

import jodd.methref.Methref;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TableQuery<E extends IBean> extends IQuery {
  private List<RCondition> conditions;
  private LinkedHashMap<String, OrderType> orders;

  public TableQuery(Class<E> z) throws Exception {
    this.conditions = new ArrayList<>();
    this.orders = new LinkedHashMap<>();
    this.z = z;
    this.mf = Methref.on(z);
    // MethodDelegation.to(Interceptor.class).andThen(FieldAccessor.ofBeanProperty());

    this.table = RDBUtils.getRDBTable(z);
  }

  @Override protected void appendCondition(RCondition c) {
    if (null == conditions) { conditions = new ArrayList<>(); }
    conditions.add(c);
  }

  @Override public List<RCondition> conditions() {
    return conditions;
  }

  @Override public void appendCondition(Object getMethodName, LogicType logicType, Object... args) {
    String _getMethodName = this.mf.ref();
    String _columnName = _getMethodName.substring(3);

    int _len = null == args ? 0 : args.length;
    switch (_len) {
      case 0: appendCondition(new RCondition(_columnName, logicType, null)); break;
      case 1: appendCondition(new RCondition(_columnName, logicType, args[0])); break;
      case 2: appendCondition(new RCondition(_columnName, logicType, args[0], args[1])); break;
      default: appendCondition(new RCondition(_columnName, logicType, null)); break;
    }
  }

  @Override public LinkedHashMap<String, OrderType> orders() { return orders; }

  @Override public void appendOrder(String fieldName, OrderType orderType) {
    if (null == orders) { orders = new LinkedHashMap<>(); }
    orders.put(fieldName, orderType);
  }

  @Override public void appendOrder(Object getMethodName, OrderType orderType) {
    String _getMethodName = this.mf.ref();
    String _columnName = _getMethodName.substring(3);
    appendOrder(_columnName, orderType);
  }
}
