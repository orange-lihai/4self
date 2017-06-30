package biz.churen.self.model.rdb;

public enum LogicType {
  EQ("=") {
  },
  NOT_EQ("!=") {
  },
  LT("<") {
  },
  LE("<=") {
  },
  GT(">") {
  },
  GE(">=") {
  },
  IN("IN") {
    @Override public String join(String left, Object right) {
      return left + " " + this.code + " (" + right.toString() + ")";
    }
  },
  NOT_IN("NOT IN") {
    @Override public String join(String left, Object right) {
      return left + " " + this.code + " (" + right.toString() + ")";
    }
  },
  LIKE("LIKE") {
    @Override public String join(String left, Object right) {
      return left + " " + this.code + " '%" + right.toString() + "%'";
    }
  },
  NOT_LIKE("NOT LIKE") {
    @Override public String join(String left, Object right) {
      return left + " " + this.code + " '%" + right.toString() + "%'";
    }
  },
  LEFT_LIKE("LEFT_LIKE") {
    @Override public String join(String left, Object right) {
      return left + " LIKE '" + right.toString() + "%'";
    }
  },
  RIGHT_LIKE("RIGHT_LIKE") {
    @Override public String join(String left, Object right) {
      return left + " LIKE '%" + right.toString() + "'";
    }
  },
  BETWEEN_AND("BETWEEN_AND") {
    public String join(String left, Object right, Object right2) {
      return left + " BETWEEN " + right.toString() + " AND " + right2.toString();
    }
  },
  IS_NULL("IS NULL") {
    public String join(String left) {
      return left + " " + this.code;
    }
  },
  IS_NOT_NULL("IS NOT NULL") {
    public String join(String left) {
      return left + " " + this.code;
    }
  };

  String code;
  LogicType(String code) {
    this.code = code;
  }

  public String join(String left) {
    return left + " " + this.code;
  }

  public String join(String left, Object right) {
    return left + " " + this.code + " " + right.toString();
  }

  public String join(String left, Object right, Object right2) {
    return left + " " + this.code + " " + right.toString();
  }
}
