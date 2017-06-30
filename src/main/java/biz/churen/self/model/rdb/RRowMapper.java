package biz.churen.self.model.rdb;

import biz.churen.self.util.ZUtil;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class RRowMapper<T> implements RowMapper<T> {
  public Class<T> clazz;

  public RRowMapper(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override public T mapRow(ResultSet rs, int rowNum) throws SQLException {
    T _r = ZUtil.newClazz(clazz);
    ITableBean _b = (ITableBean) _r;
    Map<String, Object> _map = new HashMap<>();
    ResultSetMetaData meta = rs.getMetaData();
    int columnCount = meta.getColumnCount();
    for (int i = 0; i < columnCount; i++) {
      String _name = JdbcUtils.lookupColumnName(meta, i + 1);
      String _camelName = JdbcUtils.convertUnderscoreNameToPropertyName(_name);
      Object _object = rs.getObject(i + 1);
      _map.put(_name, _object);
      _map.put(_camelName, _object);
    }
    _b.fillProp(_map);

    return _r;
  }
}
