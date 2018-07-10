package biz.churen.self.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Stack;

/**
 * Created by lihai5 on 2018/6/7.
 */
class XStackUtilTest {

  @Test void parenthesesMatch() {
    String a = null;
    Assertions.assertEquals(XStackUtil.parenthesesMatch(a), 0);
    String b = "";
    Assertions.assertEquals(XStackUtil.parenthesesMatch(b), 0);
    String c = " ";
    Assertions.assertEquals(XStackUtil.parenthesesMatch(c), 0);
    String d = " no parentheses ";
    Assertions.assertEquals(XStackUtil.parenthesesMatch(d), 0);
    String e = "hello (";
    Assertions.assertEquals(XStackUtil.parenthesesMatch(e), -1);
    String f = "hello ), world";
    Assertions.assertEquals(XStackUtil.parenthesesMatch(f), -1);
    String g = "hello, (world)!";
    Assertions.assertEquals(XStackUtil.parenthesesMatch(g), 1);
    String h = "[hello, (world)!]";
    Assertions.assertEquals(XStackUtil.parenthesesMatch(h), 2);
    String i = "[hello(, (world)!]";
    Assertions.assertEquals(XStackUtil.parenthesesMatch(i), -1);
    String j = "[[{4, 6, 7]";
    Assertions.assertEquals(XStackUtil.parenthesesMatch(j), -1);
  }

  @Test public void expressionEval() {
    String e = " 1 + 2 * 3";
    Double r = XStackUtil.expressionEval(e);
    Assertions.assertEquals(7, r.intValue());

    Stack<String> rpn = XStackUtil.RPN(e);
    Stack<Character> man = new Stack<>();

    String e2 = "(0!+1)*2^(3!+4)-(5!-67-(8+9))";
    Double r2 = XStackUtil.expressionEval(e2);
    Assertions.assertEquals(2012, r2.intValue());

    Stack<String> rpn2 = XStackUtil.RPN(e2);

    e2 = "5mod3";
    r2 = XStackUtil.expressionEval(e2);
    Assertions.assertEquals(2, r2.intValue());
  }

  @Test public void placeQueens() {
    XStackUtil stackUtil = new XStackUtil();
    List<Stack<XStackUtil.Queen>> rs = stackUtil.placeQueens(4);
    Assertions.assertEquals(2, rs.size());
  }

}
