package biz.churen.self.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

}
