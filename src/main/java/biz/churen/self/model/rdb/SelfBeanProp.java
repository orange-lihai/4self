package biz.churen.self.model.rdb;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

public class SelfBeanProp {
  protected Field f;
  private Set<String> names;

  public SelfBeanProp(Field f) {
    this.f = f;
    this.names = new LinkedHashSet<>();
  }

  public void appendName(String key) {
    this.names.add(key);
  }
  public boolean fuzzyMatching(String key) {
    for (String a : names) {
      if (a.equalsIgnoreCase(key)) { return true; }
    }
    return false;
  }
}
