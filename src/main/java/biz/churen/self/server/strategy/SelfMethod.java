package biz.churen.self.server.strategy;

import biz.churen.self.SelfApplication;
import biz.churen.self.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class SelfMethod {
  public Method method;
  public String[] parameterNames;
  public String mappingUrl;
  public Object object;

  // constructors
  public SelfMethod(Object object, Method method, String url) {
    this.object = object;
    this.method = method;
    this.parameterNames = SelfApplication.ctx
        .getBean(DefaultParameterNameDiscoverer.class).getParameterNames(method);
    this.mappingUrl = url;
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
        o[i] = params.get(parameterNames[i]);
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
      o[i] = JsonUtil.toObject(jsonString, p.getType());
    }
    return o;
  }

  public Object[] matchSelfBody(Object[] o, Map<String, String> params) {
    Parameter[] ps = method.getParameters();
    if (null == o) { o = new Object[ps.length]; }
    for (int i = 0; i < ps.length; i++) {
      Parameter p = ps[i];
      if (!p.isAnnotationPresent(SelfBody.class)) { continue; }
      o[i] = JsonUtil.mapToObject(params, p.getType());
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
