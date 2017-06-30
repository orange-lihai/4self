package biz.churen.self.model.rdb;

import biz.churen.self.util.ZUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;

import java.sql.ResultSetMetaData;
import java.util.*;
import java.util.stream.Collectors;

@Service("mysql")
public class AbstractMySQLTable<E extends IBean>
    extends AbstractTable<E> implements ITable<E> {
  @Autowired private JdbcTemplate jdbcTemplate;

  @Override public String uniqueKeysMetaSql() {
    return "select k.CONSTRAINT_NAME, k.COLUMN_NAME\n"
           + "from information_schema.KEY_COLUMN_USAGE k\n"
           + "where k.TABLE_SCHEMA = ? and k.TABLE_NAME = ?\n";
  }

  @Override public String columnsMetaSql() {
    return "select c.ORDINAL_POSITION, c.COLUMN_NAME, c.COLUMN_TYPE\n"
           + "  , c.CHARACTER_MAXIMUM_LENGTH, c.CHARACTER_OCTET_LENGTH\n"
           + "  , c.NUMERIC_PRECISION, c.NUMERIC_SCALE\n"
           + "  , c.CHARACTER_SET_NAME, c.COLLATION_NAME\n"
           + "  , c.COLUMN_KEY, c.COLUMN_COMMENT\n"
           + "from information_schema.COLUMNS c\n"
           + "where c.TABLE_SCHEMA = ? and c.TABLE_NAME = ?\n"
           + "order by c.ORDINAL_POSITION asc\n";
  }

  @Override public String primaryKeysMetaSql() {
    return "select c.ORDINAL_POSITION, c.COLUMN_NAME, c.COLUMN_TYPE\n"
           + "  , c.CHARACTER_MAXIMUM_LENGTH, c.CHARACTER_OCTET_LENGTH\n"
           + "  , c.NUMERIC_PRECISION, c.NUMERIC_SCALE\n"
           + "  , c.CHARACTER_SET_NAME, c.COLLATION_NAME\n"
           + "  , c.COLUMN_KEY, c.COLUMN_COMMENT\n"
           + "from information_schema.COLUMNS c\n"
           + "where c.TABLE_SCHEMA = ? and c.TABLE_NAME = ?\n"
           + "and c.COLUMN_KEY= 'PRI'"
           + "order by c.ORDINAL_POSITION asc\n";
  }

  @Override public List<RColumn> columns(Class<E> clazz) {
    String sql = columnsMetaSql();
    Object[] args = new Object[] {RDBUtils.dbName(clazz), RDBUtils.tableName(clazz)};
    return queryListT(sql, args, RColumn.class);
  }

  @Override public Set<String> columnsSet(Class<E> clazz) {
    return columns(clazz).stream().map(m -> m.column_name).collect(Collectors.toSet());
  }

  @Override public Map<String, List<RColumn>> ukMap(Class<E> clazz) {
    Map<String, List<RColumn>> map = new HashMap<>();
    String sql = uniqueKeysMetaSql();
    Object[] args = new Object[] {RDBUtils.dbName(clazz), RDBUtils.tableName(clazz)};
    List<RConstraint> cons = queryListT(sql, args, RConstraint.class);

    for (RConstraint c : cons) {
      if (!map.containsKey(c.constraint_name)) {
        map.put(c.constraint_name, new ArrayList<>());
      }
      map.get(c.constraint_name).add(new RColumn(c.column_name));

    }
    return map;
  }

  @Override public List<RColumn> pks(Class<E> clazz) {
    String sql = primaryKeysMetaSql();
    Object[] args = new Object[] {RDBUtils.dbName(clazz), RDBUtils.tableName(clazz)};
    return queryListT(sql, args, RColumn.class);
  }


  @Override public Integer queryCount(String sql) {
    return jdbcTemplate.queryForObject(sql, Integer.class);
  }

  @Override public Integer queryCount(String sql, Object[] args) {
    return jdbcTemplate.queryForObject(sql, args, Integer.class);
  }

  @Override public Integer queryCount(IQuery<E> query) {
    Class clazz = query.z;
    String sql = RDBUtils.RDBTableQueryCountSql(clazz, "x")
                 + RDBUtils.RDBTableWhereSql(columnsSet(clazz), query, "x");
    return queryCount(sql);
  }

  @Override public Page<E> queryPage(IQuery<E> query, Page<E> page) {
    Class<E> clazz = query.z;
    String sql = RDBUtils.RDBTableQuerySql(clazz, "x") +
                 RDBUtils.RDBTableWhereSql(columnsSet(clazz), query, "x"
                     , page.getOffset(), page.pageSize) +
                 RDBUtils.RDBTableOrderSql(columnsSet(clazz), query, "x");

    List<E> list = jdbcTemplate.query(sql, new RRowMapper<>(clazz));
    page.setData(list, queryCount(query));
    return page;
  }

  @Override public List<E> queryList(IQuery<E> query, Integer maxNum) {
    Class<E> clazz = query.z;
    String
        sql =
        RDBUtils.RDBTableQuerySql(clazz, "x") + RDBUtils.RDBTableWhereSql(columnsSet(clazz),
                                                                          query,
                                                                          "x",
                                                                          maxNum) + RDBUtils.RDBTableOrderSql(columnsSet(clazz), query, "x");
    return jdbcTemplate.query(sql, new RRowMapper<>(clazz));
  }

  @Override public List<E> queryList(IQuery<E> query) {
    return queryList(query, 65535 * 10);
  }

  @Override public List<E> queryList(String sql, Class<E> clazz) {
    return null;
  }

  @Override public List<E> queryList(String sql, Object[] args, Class<E> clazz) {
    List<E> list = new ArrayList<>();
    // query data from db, and extract it to rs
    list.addAll(jdbcTemplate.query(sql, args, (rs, rowNum) -> {
      E _r = ZUtil.newClazz(clazz);
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
      _r.fillProp(_map);

      return _r;
    }));
    return list;
  }

  @Override public <T> List<T> queryListT(String sql, Object[] args, Class<T> clazz) {
    List<T> list = new ArrayList<>();
    // query data from db, and extract it to rs
    list.addAll(jdbcTemplate.query(sql, args, (rs, rowNum) -> {
      T _r = ZUtil.newClazz(clazz);
      IBean _b = (IBean) _r;
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
    }));
    return list;
  }

  @Override public E queryOneByPK(E clazz, Object... params) {
    return null;
  }

  @Override public List<E> queryOne(E e) {
    return null;
  }

  @Override public List<E> queryOne(IQuery<E> query) {
    return null;
  }

  @Override public List<E> queryOne(String sql, Class<E> clazz) {
    return null;
  }

  @Override public List<E> queryOne(String sql, Object[] args, Class<E> clazz) {
    return null;
  }

  @Override public int insert(List<E> list) {
    return 0;
  }

  @Override public int insert(E e) {
    return 0;
  }

  @Override public int delete(E e) {
    return 0;
  }

  @Override public int delete(List<E> list) {
    return 0;
  }

  @Override public int update(E e, boolean allReplace) {
    return 0;
  }

  @Override public int update(E e) {
    return 0;
  }

  @Override public int update(List<E> list) {
    return 0;
  }

  @Override public int update(List<E> list, boolean allReplace) {
    return 0;
  }

  @Override public void execute(List<String> sql) {
    for (String s : sql) { execute(s); }
  }

  @Override public void execute(String sql) {
    jdbcTemplate.execute(sql);
  }

}
