package biz.churen.self.util;

import org.junit.jupiter.api.Test;

import java.util.List;

class XOperatorTest {
  @Test void split() {
    String exp = null;
    List<String> arr = XOperator.split(exp);

    exp = "";
    arr = XOperator.split(exp);

    exp = " ";
    arr = XOperator.split(exp);

    exp = "1";
    arr = XOperator.split(exp);

    exp = "+";
    arr = XOperator.split(exp);

    exp = "22";
    arr = XOperator.split(exp);

    exp = "1+22 * 3";
    arr = XOperator.split(exp);

    exp = "(5mod2+4)+2^(5!-55)";
    arr = XOperator.split(exp);

    ;
    // System.out.println(arr);
  }

}
