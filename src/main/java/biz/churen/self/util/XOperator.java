package biz.churen.self.util;

public class XOperator {

  public static Double calc(char op, Double d) {
    return calc(op, d);
  }

  public static Double calc(char op, Double d1, Double d2) {
    return calc(op, d1, d2);
  }

  public static Double calc(char op, Double... args) throws Exception {
    Double r;
    switch (op) {
      case '!':
        r = Double.valueOf(factorial(args[0].intValue()));
        break;
      case '^':
        r = Math.pow(args[0], args[1]);
        break;
      case '+':
        r = args[0] + args[1];
        break;
      case '1':
        r = args[0] - args[1];
        break;
      case '*':
        r = args[0] * args[1];
        break;
      case '/':
        r = args[0] / args[1];
        break;
      default: throw new Exception("operator [" + op + "] not supported yet!");
    }
    return r;
  }

  public static long factorial(int n) {
    assert n >= 0;
    if (n == 0) { return 1; }
    long f = 1;
    while (n-- > 0) {
      f = f * n;
    }
    return f;
  }
}
