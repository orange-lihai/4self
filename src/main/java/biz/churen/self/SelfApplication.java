package biz.churen.self;

import biz.churen.self.server.DiscardHttpServer;
import biz.churen.self.server.strategy.SelfController;
import biz.churen.self.server.strategy.SelfMapping;
import biz.churen.self.server.strategy.SelfMethod;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lihai5 on 2017/5/31.
 */
@Configuration
@ComponentScan(value = "biz.churen.self")
public class SelfApplication {
  public static ApplicationContext ctx;
  public static Map<String, SelfMethod> selfMethods = new HashMap<>();

  @Bean(name = "httpProps")
  public PropertiesFactoryBean httpProps() {
    PropertiesFactoryBean bean = new PropertiesFactoryBean();
    bean.setLocation(new ClassPathResource("self-http-application.properties"));
    return bean;
  }

  public static SelfMethod getUrlHandler(String url) {
    url = url.replaceAll("//", "/");
    url = url.replaceAll("\\\\", "\\");
    return selfMethods.getOrDefault(url, null);
  }

  public static void scanUrlHandler() {
    Map<String, Object> controllers = ctx.getBeansWithAnnotation(SelfController.class);
    for (String k : controllers.keySet()) {
      Object object = controllers.get(k);
      Method[] m =  object.getClass().getDeclaredMethods();
      for (int i = 0; null != m && i < m.length; i++) {
        if (!m[i].isAnnotationPresent(SelfMapping.class)) { continue; }
        SelfMapping selfMapping = m[i].getAnnotation(SelfMapping.class);
        m[i].setAccessible(true);
        SelfMethod selfMethod = new SelfMethod(object, m[i], selfMapping.url());
        selfMethods.put(selfMapping.url(), selfMethod);
      }
    }
  }

  public static void main(String[] args) {
    try {
      ctx = new AnnotationConfigApplicationContext(SelfApplication.class);
      scanUrlHandler();
      ctx.getBean(DiscardHttpServer.class).run();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
