package biz.churen.self.model.rdb;

public enum Logic {
  AND("AND") {
  },
  OR("OR") {
  };

  String code;
  Logic(String code) {
    this.code = code;
  }
  public String value() { return this.code; }
}
