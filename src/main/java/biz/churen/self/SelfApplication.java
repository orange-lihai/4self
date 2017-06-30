package biz.churen.self;

import biz.churen.self.server.DiscardHttpServer;
import biz.churen.self.server.strategy.SelfController;
import biz.churen.self.server.strategy.SelfMapping;
import biz.churen.self.server.strategy.SelfMethod;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lihai5 on 2017/5/31.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(value = "biz.churen.self")
@PropertySource({
    "classpath:self-db-application.properties",
    "self-http-application.properties"
})
public class SelfApplication {
  public static AnnotationConfigApplicationContext ctx;
  public static Map<String, SelfMethod> selfMethods = new HashMap<>();

  @Autowired private Environment env;

  /** ########################################################################### **/
  @Bean(name = "httpProps")
  public PropertiesFactoryBean httpProps() {
    PropertiesFactoryBean bean = new PropertiesFactoryBean();
    bean.setLocation(new ClassPathResource("self-http-application.properties"));
    return bean;
  }
  /** ########################################################################### **/
  @Bean(name="dataSourceRead")
  public DataSource dataSourceRead() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("slave.mysql.driver"));
    dataSource.setUsername(env.getProperty("slave.mysql.username"));
    dataSource.setPassword(env.getProperty("slave.mysql.password"));
    dataSource.setUrl(env.getProperty("slave.mysql.url"));
    return dataSource;
  }
  @Bean
  public JdbcTemplate jdbcTemplateRead(DataSource dataSourceRead) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceRead);
    jdbcTemplate.setResultsMapCaseInsensitive(true);
    return jdbcTemplate;
  }

  @Bean(name="dataSource")
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("master.mysql.driver"));
    dataSource.setUsername(env.getProperty("master.mysql.username"));
    dataSource.setPassword(env.getProperty("master.mysql.password"));
    dataSource.setUrl(env.getProperty("master.mysql.url"));
    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.setResultsMapCaseInsensitive(true);
    return jdbcTemplate;
  }

  @Bean(name="txManager")
  public PlatformTransactionManager txManager() {
    DataSourceTransactionManager txManager = new DataSourceTransactionManager();
    txManager.setDataSource(dataSource());
    return txManager;
  }
  /** ########################################################################### **/
  @Bean("writeSelf")
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    Reader reader = Resources.getResourceAsReader("self-data-source.xml");
    SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(reader, "writeSelf");
    return ssf;
  }
  @Bean("sqlSession")
  public SqlSessionTemplate sqlSessionTemplate() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory());
  }

  @Bean("readSelf")
  public SqlSessionFactory readSqlSessionFactory() throws Exception {
    Reader reader = Resources.getResourceAsReader("self-data-source.xml");
    SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(reader, "readSelf");
    return ssf;
  }
  @Bean("readSqlSession")
  public SqlSessionTemplate readSqlSessionTemplate() throws Exception {
    return new SqlSessionTemplate(readSqlSessionFactory());
  }

  /** ########################################################################### **/
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
  /** ########################################################################### **/
  @Bean("nameDiscover")
  public DefaultParameterNameDiscoverer parameterNameDiscoverer() {
    return new DefaultParameterNameDiscoverer();
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
