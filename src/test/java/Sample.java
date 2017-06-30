import jodd.methref.Methref;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Sample {
  public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, NoSuchMethodException {

    DynamicType.Builder<?> builder = new ByteBuddy()
        .subclass(Dx.class)
        .defineField("id", int.class, Visibility.PRIVATE)
        .defineMethod("getId", int.class, Visibility.PUBLIC).intercept(FieldAccessor.ofBeanProperty())
    ;

    Class<?> type = builder
        .make()
        .load(Sample.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
        .getLoaded();

    Object o = type.newInstance();
    Dx dx = (Dx) o;
    Field f = o.getClass().getDeclaredField("id");
    f.setAccessible(true);
    System.out.println(o.toString());
    Method m = o.getClass().getDeclaredMethod("getId");
    System.out.println(m.getName());

    Methref<Dx> mx = Methref.on(Dx.class);
    mx.to().xx();
    mx.ref();
  }
}
