package biz.churen.self.server.strategy;

import biz.churen.self.util.SUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * Created by lihai5 on 2017/6/2.
 */
public class SelfMethod {
  public Method method;
  public Object object;

  // constructors
  public SelfMethod(Object object, Method method, String url) {
    this.object = object;
    this.method = method;
  }

  public Object[] matchSelfParams(Object[] o, Map<String, String> params) {
    Parameter[] ps = method.getParameters();
    if (null == o) { o = new Object[ps.length]; }
    for (int i = 0; i < ps.length; i++) {
      Parameter p = ps[i];
      if (!p.isAnnotationPresent(SelfParam.class)) { continue; }
      SelfParam selfParam = p.getAnnotation(SelfParam.class);
      if (StringUtils.isNotEmpty(selfParam.name())) {
        o[i] = params.get(selfParam.name());
      } else {
        // TODO find right parameter name
        o[i] = params.get(p.getName());
      }
    }
    return o;
  }

  public Object[] matchSelfBody(Object[] o, String jsonString) {
    Parameter[] ps = method.getParameters();
    if (null == o) { o = new Object[ps.length]; }
    for (int i = 0; i < ps.length; i++) {
      Parameter p = ps[i];
      if (!p.isAnnotationPresent(SelfBody.class)) { continue; }
      o[i] = SUtil.toObject(jsonString, p.getType());
    }
    return o;
  }

  public Object[] matchSelfBody(Object[] o, Map<String, String> params) {
    Parameter[] ps = method.getParameters();
    if (null == o) { o = new Object[ps.length]; }
    for (int i = 0; i < ps.length; i++) {
      Parameter p = ps[i];
      if (!p.isAnnotationPresent(SelfBody.class)) { continue; }
      o[i] = SUtil.mapToObject(params, p.getType());
    }
    return o;
  }

  // invoke
  public Object invoke(Object... args) {
    Object o = null;
    try {
      o = this.method.invoke(object, args);
    } catch (InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return o;
  }
}
