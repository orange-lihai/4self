package biz.churen.self.model.rdb;

import biz.churen.self.model.strategy.*;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SelfDBType(name = "")
@SelfDB(name = "")
@SelfTable(name = "")
@SelfVirtualTable(name = "")
public interface IBean {

  default void fillProp(Map<String, Object> values) {
    if (CollectionUtils.isEmpty(values)) { return; }
    List<SelfBeanProp> props = props();
    for (String k : values.keySet()) {
      try {
        for (SelfBeanProp p : props) {
          if (p.fuzzyMatching(k)) {
            p.f.set(this, values.get(k));
            break;
          }
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  default List<SelfBeanProp> props() {
    // TODO 性能问题
    List<SelfBeanProp> ref = new ArrayList<>();
    Field[] fields = this.getClass().getDeclaredFields();
    for (Field f : fields) {
      SelfBeanProp p = new SelfBeanProp(f);
      if (f.isAnnotationPresent(SelfColumn.class)) {
        SelfColumn _col = f.getDeclaredAnnotation(SelfColumn.class);
        p.appendName(_col.name());
      }
      p.appendName(f.getName());
      ref.add(p);
    }
    return ref;
  }

  default String dbTypeName() {
    SelfDBType db = this.getClass().getDeclaredAnnotation(SelfDBType.class);
    return (null != db) ? db.name() : "";
  }

  default String dbName() {
    SelfDB db = this.getClass().getDeclaredAnnotation(SelfDB.class);
    return (null != db) ? db.name() : "";
  }

  default String tableName() {
    SelfTable tab = this.getClass().getDeclaredAnnotation(SelfTable.class);
    return (null != tab) ? tab.name() : "";
  }

  default String virtualTableName() {
    SelfVirtualTable tab = this.getClass().getDeclaredAnnotation(SelfVirtualTable.class);
    return (null != tab) ? tab.name() : "";
  }
}
