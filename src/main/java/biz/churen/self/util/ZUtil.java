package biz.churen.self.util;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ZUtil {
  public static <T> T newClazz(Class<T> clazz) {
    T a = null;
    try {
      a = clazz.newInstance();
    } catch (Exception ex) { ex.printStackTrace(); }
    return a;
  }

  public static <T> T one(List<T> list) {
    return CollectionUtils.isEmpty(list) ? null : list.get(0);
  }

  public static String lowerF(String src) {
    char c = src.charAt(0);
    if (c >= 'a' && c <= 'z') {
      src = String.valueOf(c).toLowerCase() + src.substring(1);
    }
    return src;
  }

  public static <E extends Serializable> List<E> deepClone(List<E> src) {
    if (null == src) { return src; }
    List<E> rs = new ArrayList<>();
    for (E e : src) {
      rs.add(SerializationUtils.clone(e));
    }
    return rs;
  }

  public static String annotationValue(Class clazz, Class anClazz) {
    Annotation an = clazz.getDeclaredAnnotation(anClazz);
    return null == an ? "" : an.toString();
  }
}
