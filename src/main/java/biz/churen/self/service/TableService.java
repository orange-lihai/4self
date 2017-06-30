package biz.churen.self.service;

import biz.churen.self.model.Column;
import biz.churen.self.model.Table;
import biz.churen.self.model.TableQuery;
import com.sun.rowset.internal.Row;

import java.util.List;
import java.util.Map;

public interface TableService {
  /** DDL operators, like DDL operators in relation database. */
  public String createTable(Table tab);
  public String alterTable(Table tab);
  public String dropTable(Table tab);
  public String truncateTable(Table tab);

  public String addColumn(Column tab);
  public String deleteColumn(Column tab);
  public String alterColumn(Column tab);

  public String copyTable(Table src, Table dest);
  public String copyColumn(Column src, Column dest);
  public String copyColumnValue(Column src, Column dest);
  public String resetColumnValue(Column src);

  /** CRUD operators */
  public <T> T insertRecord(Row row);
  public <T> T deleteRecord(String tabId, Object src, Class<T> clazz);
  public <T> T updateRecord(String tabId, Object src, Class<T> clazz);

  public <T> List<T> selectRecordList(TableQuery query, Class<T> clazz, int num);
  public <T> T selectRecordOne(TableQuery query, Class<T> clazz);

  /** executor */
  public Object execute(List<String> sql, Map<String, Object> params);
}
