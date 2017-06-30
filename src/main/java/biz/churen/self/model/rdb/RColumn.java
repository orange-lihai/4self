package biz.churen.self.model.rdb;

public class RColumn implements ITableBean {
  public String column_name;
  public String column_type;


  public RColumn(String column_name) {
    this.column_name = column_name;
  }
}
