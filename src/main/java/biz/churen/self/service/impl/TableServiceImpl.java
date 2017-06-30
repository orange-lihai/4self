package biz.churen.self.service.impl;

import biz.churen.self.model.Column;
import biz.churen.self.model.Table;
import biz.churen.self.model.TableQuery;
import biz.churen.self.service.TableService;
import com.sun.rowset.internal.Row;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class TableServiceImpl implements TableService {
  @Autowired private JdbcTemplate jdbcTemplate;

  @Override public String createTable(Table tab) {
    return null;
  }

  @Override public String alterTable(Table tab) {
    return null;
  }

  @Override public String dropTable(Table tab) {
    return null;
  }

  @Override public String truncateTable(Table tab) {
    return null;
  }

  @Override public String addColumn(Column tab) {
    return null;
  }

  @Override public String deleteColumn(Column tab) {
    return null;
  }

  @Override public String alterColumn(Column tab) {
    return null;
  }

  @Override public String copyTable(Table src, Table dest) {
    return null;
  }

  @Override public String copyColumn(Column src, Column dest) {
    return null;
  }

  @Override public String copyColumnValue(Column src, Column dest) {
    return null;
  }

  @Override public String resetColumnValue(Column src) {
    return null;
  }

  @Override public <T> T insertRecord(Row row) {
    return null;
  }

  @Override public <T> T deleteRecord(String tabId, Object src, Class<T> clazz) {
    return null;
  }

  @Override public <T> T updateRecord(String tabId, Object src, Class<T> clazz) {
    return null;
  }

  @Override public <T> List<T> selectRecordList(TableQuery query, Class<T> clazz, int num) {
    return null;
  }

  @Override public <T> T selectRecordOne(TableQuery query, Class<T> clazz) {
    return null;
  }

  @Override
  // @Transactional("txManager")
  public Object execute(List<String> sql, Map<String, Object> params) {
    for (String s : sql) {
      if (StringUtils.isEmpty(s)) { continue; }
      jdbcTemplate.execute(s);
    }
    return sql.size();
  }
}
