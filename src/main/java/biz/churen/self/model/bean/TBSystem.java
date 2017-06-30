package biz.churen.self.model.bean;

import biz.churen.self.model.rdb.ITableBean;
import biz.churen.self.model.strategy.SelfDB;
import biz.churen.self.model.strategy.SelfDBType;
import biz.churen.self.model.strategy.SelfTable;

import java.util.Date;

@SelfDBType(name = "mysql")
@SelfDB(name = "self")
@SelfTable(name = "tb_system")
public class TBSystem implements ITableBean {
  public String id;
  public String ename;
  public String showName;
  public String companyName;
  public String url;
  public String memo;
  public Date d;

  // getters and setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEname() {
    return ename;
  }

  public void setEname(String ename) {
    this.ename = ename;
  }

  public String getShowName() {
    return showName;
  }

  public void setShowName(String showName) {
    this.showName = showName;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public Date getD() {
    return d;
  }

  public void setD(Date d) {
    this.d = d;
  }
}
