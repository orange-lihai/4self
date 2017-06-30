package biz.churen.self.util;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;

import java.lang.reflect.Field;

public class PUtil {

  public static <T> T newClazz4PropertyRef(Class<T> type) {
    try {
      DynamicType.Builder<?> builder = new ByteBuddy()
          .subclass(type.isInterface() ? Object.class : type);

      if ( type.isInterface() ) {
        builder = builder.implement( type );
      }

      Field[] fields = type.getDeclaredFields();
      for (int i = 0; null != fields && i < fields.length; i++) {
        builder = builder.defineMethod(fields[i].getName(), fields[i].getType(), Visibility.PUBLIC)
                         .intercept(FieldAccessor.of(fields[i]));
      }

      Class<?> proxyType = builder
          .make()
          .load(PUtil.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
          .getLoaded();

      @SuppressWarnings("unchecked")
      Class<T> typed = (Class<T>) proxyType;
      return typed.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      return ZUtil.newClazz(type);
    }
  }
}
