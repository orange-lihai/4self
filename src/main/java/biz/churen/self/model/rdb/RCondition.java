package biz.churen.self.model.rdb;

import java.io.Serializable;

public class RCondition implements Serializable {
  public String left;
  public LogicType logicType;
  public Object right;
  public Object right2;

  public Logic wrapperLogic;

  public RCondition(String left, LogicType logicType, Object right) {
    this.wrapperLogic = Logic.AND;

    this.left = left;
    this.logicType = logicType;
    this.right = right;
  }
  public RCondition(String left, LogicType logicType, Object right, Object right2) {
    this.wrapperLogic = Logic.AND;

    this.left = left;
    this.logicType = logicType;
    this.right = right;
    this.right2 = right2;
  }

  public RCondition(String left, LogicType logicType, Object right, Logic wrapperLogic) {
    this.wrapperLogic = wrapperLogic;

    this.left = left;
    this.logicType = logicType;
    this.right = right;
  }
  public RCondition(String left, LogicType logicType, Object right, Object right2,
      Logic wrapperLogic) {
    this.wrapperLogic = wrapperLogic;

    this.left = left;
    this.logicType = logicType;
    this.right = right;
    this.right2 = right2;
  }


  public String condString() {
    return condString("");
  }

  public String condString(String alias) {
    // TODO convert right and right2
    String _str;
    if (null == right && null == right2) {
      _str = wrapperLogic.code + " " + logicType.join(alias + "." + left);
    } else if (null != right && null == right2) {
      _str = wrapperLogic.code + " " + logicType.join(alias + "." + left, right);
    } else {
      _str = wrapperLogic.code + " " + logicType.join(alias + "." + left, right, right2);
    }
    return " " + _str;
  }
}
