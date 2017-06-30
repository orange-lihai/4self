package biz.churen.self.model.rdb;

import biz.churen.self.SelfApplication;
import biz.churen.self.model.strategy.SelfColumn;
import biz.churen.self.model.strategy.SelfDB;
import biz.churen.self.model.strategy.SelfTable;
import biz.churen.self.util.JsonUtil;
import biz.churen.self.util.ZUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class RDBUtils {
  public static boolean isRDBTableType(Class<?> clazz) {
    SelfDB db = clazz.getDeclaredAnnotation(SelfDB.class);
    SelfTable table = clazz.getDeclaredAnnotation(SelfTable.class);
    return !(null == db || null == table || StringUtils.isAnyEmpty(db.name(), table.name()));
  }

  public static String dbTypeName(Class<? extends IBean> clazz) {
    IBean bean = ZUtil.newClazz(clazz);
    return bean.dbTypeName();
  }

  public static String dbName(Class<? extends IBean> clazz) {
    IBean bean = ZUtil.newClazz(clazz);
    return bean.dbName();
  }

  public static String tableName(Class<? extends IBean> clazz) {
    IBean bean = ZUtil.newClazz(clazz);
    return bean.tableName();
  }

  public static String dbColumnName(Class<? extends IBean> clazz, String fieldName) {
    SelfColumn an = null;
    fieldName = ZUtil.lowerF(fieldName);
    for (Field f : clazz.getFields()) {
      if (f.getName().equals(fieldName)) {
        an = f.getDeclaredAnnotation(SelfColumn.class);
      }
    }
    return null == an ? fieldName : an.name();
  }

  public static String matchColumnName(Set<String> columnsNames, String fieldName) {
    String r = null;
    if (columnsNames.contains(fieldName)) {
      r = fieldName;
    } else {
      for (String c : columnsNames) {
        String _c = c.replaceAll("_", "");
        String _f = fieldName.replaceAll("_", "");
        if (_c.equalsIgnoreCase(_f)) { r = c; break; }
      }
    }
    return r;
  }

  public static String RDBTableDeleteSql(Class<? extends IBean> clazz, String alias) {
    Assert.isTrue(RDBUtils.isRDBTableType(clazz),
                  "not found `SelfDB` or `SelfTable` Annotation in clazz.");
    return "delete "+ alias +" from " + dbName(clazz) + "." + tableName(clazz) + " " + alias + " "
           + " where 1 = 1 ";
  }

  public static String RDBTableQuerySql(Class<? extends IBean> clazz, String alias) {
    Assert.isTrue(RDBUtils.isRDBTableType(clazz),
                  "not found `SelfDB` or `SelfTable` Annotation in clazz.");
    return "select "+ alias +".* from " + dbName(clazz) + "." + tableName(clazz) + " " + alias + " "
           + " where 1 = 1 ";
  }

  public static String RDBTableQueryCountSql(Class<? extends IBean> clazz, String alias) {
    Assert.isTrue(RDBUtils.isRDBTableType(clazz),
                  "not found `SelfDB` or `SelfTable` Annotation in clazz.");
    return "select count(*) from " + dbName(clazz) + "." + tableName(clazz) + " " + alias + " "
           + " where 1 = 1 ";
  }

  public static String RDBTableWhereSql(Set<String> columnsName, IQuery query, String alias) {
    StringBuilder sb = new StringBuilder();
    List<RCondition> condSave = query.conditions();
    if (!Objects.isNull(condSave)) {
      List<RCondition> cond = ZUtil.deepClone(condSave);
      for (RCondition c : cond) {
        String _left = dbColumnName(query.z, c.left);
        _left = matchColumnName(columnsName, _left);
        if (StringUtils.isNotEmpty(_left)) {
          c.left = _left;
          c.right = MysqlUtils.formatType(c.right);
          c.right2 = MysqlUtils.formatType(c.right2);
          sb.append(c.condString(alias));
        }
      }
    }
    return sb.toString();
  }

  public static String RDBTableOrderSql(Set<String> columnsName, IQuery query, String alias) {
    StringBuilder sbOrder = new StringBuilder();
    List<String> _order = new ArrayList<>();
    LinkedHashMap<String, OrderType> ordersSave = query.orders();
    if (!Objects.isNull(ordersSave)) {
      LinkedHashMap<String, OrderType> orders = ObjectUtils.clone(ordersSave);
      for (String k : orders.keySet()) {
        OrderType ot = orders.get(k);
        String _left = dbColumnName(query.z, k);
        _left = matchColumnName(columnsName, _left);
        String _right = (null == ot) ? OrderType.ASC.name() : ot.name();
        if (StringUtils.isNotEmpty(_left)) {
          _order.add(" " + alias + "." + _left + " " + _right);
        }
      }
    }
    if (_order.size() > 0) {
      sbOrder.insert(0, " ORDER BY ");
      sbOrder.append(StringUtils.join(_order, " , "));
      sbOrder.insert(0, " ");
    }

    return sbOrder.toString();
  }

  public static String RDBTableWhereSql(Set<String> columnsName, IQuery query, String alias, int maxSize) {
    StringBuilder sb = new StringBuilder();
    sb.append( RDBTableWhereSql(columnsName, query, alias) );
    sb.append(" limit "); sb.append(maxSize); sb.append(" ");
    return sb.toString();
  }

  public static String RDBTableWhereSql(Set<String> columnsName, IQuery query, String alias, int start, int maxSize) {
    StringBuilder sb = new StringBuilder();
    sb.append( RDBTableWhereSql(columnsName, query, alias) );
    sb.append(" limit "); sb.append(start); sb.append(", "); sb.append(maxSize);
    return sb.toString();
  }

  public static <E> String RDBTableInsertSql(Class clazz, E e, List<RColumn> columns) {
    Set<String> columnSet = columns.stream().map(m->m.column_name).collect(Collectors.toSet());

    List<String> cols = new ArrayList<>();
    List<String> values = new ArrayList<>();
    Map<String, Object> m = JsonUtil.objectToMap(e);
    for (String k : m.keySet()) {
      Object o = m.get(k);
      if (null == o) {
        continue;
      } else {
        String _col = matchColumnName(columnSet, dbColumnName(clazz, k));
        if (null != _col) {
          cols.add(_col);
          values.add(o.toString());
        }
      }
    }

    StringBuilder sb = new StringBuilder();
    sb.append(" INSERT INTO " + dbName(clazz) + "." + tableName(clazz) + " ( ");
    sb.append(StringUtils.join(cols));
    sb.append(" ) ");
    sb.append(" VALUES ( ");
    sb.append(StringUtils.join(values));
    sb.append(" ) ");
    return sb.toString();
  }

  public static String RDBTableWhereSql(List<String> pks, Object o) {
    Set<String> columnSet = pks.stream().map(m->m).collect(Collectors.toSet());

    return null;
  }

  public static ITable getRDBTable(Class clazz) {
    IBean bean = (IBean) ZUtil.newClazz(clazz);
    return (ITable) SelfApplication.ctx.getBean(bean.dbTypeName());
  }
}
