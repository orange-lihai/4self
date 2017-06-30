package biz.churen.self.model.rdb;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Time;
import java.util.Date;

public class MysqlUtils {
  public static String formatType(Object o) {
    String r;
    if (null == o) { return null; }
    if (o instanceof Time) {
      Date d = (Date) o;
      r =
          " TIME_FORMAT("
          + DateFormatUtils.format(d, "HH:mm:ss")
          + ", '%H:%i:%s') ";
    } else if (o instanceof Date) {
      Date d = (Date) o;
      r =
          " DATE_FORMAT("
          + DateFormatUtils.format(d, "yyyy-MM-dd HH:mm:ss")
          + ", '%Y-%m-%d %H:%i:%s') ";
    } else if (o instanceof Boolean) {
      Boolean b = (Boolean) o;
      r = b ? " 1 " :  " 0 ";
    } else {
      r = o.toString();
    }
    return r;
  }
}
