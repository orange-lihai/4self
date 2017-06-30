package biz.churen.self.model;

import biz.churen.self.model.strategy.SelfDB;
import biz.churen.self.model.strategy.SelfTable;

import java.util.Date;
import java.util.List;

@SelfTable(name = "tb_tab")
public class Table {
  public String id;
  public String systemId;
  public String ename;
  public String memo;
  public String showName;
  public Date createdTime;
  public String version;

  public List<Column> columns;
}
