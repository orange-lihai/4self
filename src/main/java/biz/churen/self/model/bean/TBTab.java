package biz.churen.self.model.bean;

import biz.churen.self.model.rdb.ITableBean;
import biz.churen.self.model.strategy.SelfDB;
import biz.churen.self.model.strategy.SelfTable;

import java.util.Date;

@SelfDB(name = "self")
@SelfTable(name = "tb_tab")
public class TBTab implements ITableBean {
  public String id;
  public String systemId;
  public String ename;
  public String memo;
  public String showName;
  public Date createdTime;
  public Integer version;
}
