package biz.churen.self.model.rdb;

import jodd.methref.Methref;

import java.util.LinkedHashMap;
import java.util.List;

public abstract class IQuery<E extends IBean> {
  public Class<E> z;
  public E e;
  public Methref<? extends E> mf;
  public ITable<E> table;

  public abstract List<RCondition> conditions();
  protected abstract void appendCondition(RCondition c);
  public abstract void appendCondition(Object getMethodName, LogicType logicType, Object... args);

  public abstract LinkedHashMap<String, OrderType> orders();
  protected abstract void appendOrder(String filedName, OrderType orderType);
  public abstract void appendOrder(Object getMethodName, OrderType orderType);

}
